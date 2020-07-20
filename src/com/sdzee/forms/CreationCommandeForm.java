package com.sdzee.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.CommandeDao;
import com.sdzee.dao.DAOException;

public final class CreationCommandeForm {
	private static final String CHAMP_CHOIX_NEW_CLIENT = "isNewClient";
	private static final String CHAMP_DROPDOWN_LIST_CLIENT = "dropdownClientList";
	private static final String CHAMP_MONTANT          = "montantCommande";
	private static final String CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
	private static final String CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
	private static final String CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
	private static final String CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";
	private static final String FORMAT_DATE            = "dd/MM/yyyy HH:mm:ss";
	private static final String ATT_SESSION_CLIENT_LIST = "sessionClientList";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private CommandeDao commandeDao;
	private ClientDao clientDao;
	
	/**
	 * Constructeur
	 * 
	 * @param commandeDao
	 */
	public CreationCommandeForm( CommandeDao commandeDao, ClientDao clientDao ) {
		this.commandeDao = commandeDao;
		this.clientDao = clientDao;
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Commande creerCommande( HttpServletRequest request, String chemin ) {
		
		/*
		* L'objet métier pour valider la création d'un client existe déjà, il
		* est donc déconseillé de dupliquer ici son contenu ! À la place, il
		* suffit de passer la requête courante à l'objet métier existant et de
		* récupérer l'objet Client créé.
		*/
		CreationClientForm clientForm = new CreationClientForm( clientDao );
		Client client = new Client();
		HttpSession session = request.getSession();
		List<Client> listClient = (List<Client>) session.getAttribute( ATT_SESSION_CLIENT_LIST );
		
		/*
		* Si c'est un nouveau client
		*/
		if ( isNewClient(request) ) {
			client = clientForm.creerClient( request, chemin );
			/*
			 * Et très important, il ne faut pas oublier de récupérer le contenu de
			 * la map d'erreurs créée par l'objet métier CreationClientForm dans la
			 * map d'erreurs courante, actuellement vide.
			 */
			erreurs = clientForm.getErreurs();
		}
		else {
			/* Sinon récupérer de la session */
			int index = Integer.parseInt(getValeurChamp(request, CHAMP_DROPDOWN_LIST_CLIENT));
			client = listClient.get(index);
		}

		/*
		 * Ensuite, il suffit de procéder normalement avec le reste des champs
		 * spécifiques à une commande.
		 */
		/*
		 * Récupération et conversion de la date en String selon le format
		 * choisi.
		 */
		DateTime dt = new DateTime();
		// DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
		// String date = dt.toString( formatter );
		String montant = getValeurChamp( request, CHAMP_MONTANT );
		String modePaiement = getValeurChamp( request, CHAMP_MODE_PAIEMENT );
		String statutPaiement = getValeurChamp( request, CHAMP_STATUT_PAIEMENT );
		String modeLivraison = getValeurChamp( request, CHAMP_MODE_LIVRAISON );
		String statutLivraison = getValeurChamp( request, CHAMP_STATUT_LIVRAISON );
		Commande commande = new Commande();
		commande.setClient( client );
		commande.setDate( dt );
		
		try {
			traiterMontant( montant, commande );
			traiterModePaiment( modePaiement, commande );
			traiterStatutPaiment( statutPaiement, commande );
			traiterModeLivraison( modeLivraison, commande );
			traiterStatutLivraison( statutLivraison, commande );
			
			if ( erreurs.isEmpty() ) {
				resultat = "Succès de la création de la commande.";
			} else {
				resultat = "Échec de la création de la commande.";
			}
		} catch( DAOException e ) {
			resultat = "Échec de la création de commande: une erreur imprévue est survenue, "
					+ "merci de réessayer dans quelques instants.";
	      e.printStackTrace();
		}
		
		return commande;
	}

	private void traiterStatutLivraison(String statutLivraison, Commande commande) {
		try {
			validationStatutLivraison( statutLivraison );
		} catch ( Exception e ) {
			setErreur( CHAMP_STATUT_LIVRAISON, e.getMessage() );
		}
		commande.setStatutLivraison( statutLivraison );		
	}

	private void traiterModeLivraison(String modeLivraison, Commande commande) {
		try {
			validationModeLivraison( modeLivraison );
		} catch ( Exception e ) {
			setErreur( CHAMP_MODE_LIVRAISON, e.getMessage() );
		}
		commande.setModeLivraison( modeLivraison );		
	}

	private void traiterStatutPaiment(String statutPaiement, Commande commande) {
		try {
			validationStatutPaiement( statutPaiement );
		} catch ( Exception e ) {
			setErreur( CHAMP_STATUT_PAIEMENT, e.getMessage() );
		}
		commande.setStatutPaiement( statutPaiement );		
	}

	private void traiterModePaiment(String modePaiement, Commande commande) {
		try {
			validationModePaiement( modePaiement );
		} catch ( Exception e ) {
			setErreur( CHAMP_MODE_PAIEMENT, e.getMessage() );
		}
		commande.setModePaiement( modePaiement );		
	}

	private void traiterMontant(String montant, Commande commande) {
		double valeurMontant = -1;
		try {
			valeurMontant = validationMontant( montant );
		} catch ( Exception e ) {
			setErreur( CHAMP_MONTANT, e.getMessage() );
		}
		commande.setMontant( valeurMontant );
		
	}

	private boolean isNewClient(HttpServletRequest request) {
		String val = getValeurChamp(request, CHAMP_CHOIX_NEW_CLIENT);
		if ( val != null ) {
			return (val.equals("isNewClient"));
		}
		return false;
	}

	private double validationMontant( String montant ) throws Exception {
		double temp;
		if ( montant != null ) {
			try {
				temp = Double.parseDouble( montant );
				if ( temp < 0 ) {
					throw new Exception( "Le montant doit être un nombre positif." );
				}
			} catch ( NumberFormatException e ) {
				temp = -1;
				throw new Exception( "Le montant doit être un nombre." );
			}
		} else {
			temp = -1;
			throw new Exception( "Merci d'entrer un montant." );
		}
		return temp;
	}

	private void validationModePaiement( String modePaiement ) throws Exception {
		if ( modePaiement != null ) {
			if ( modePaiement.length() < 2 ) {
				throw new Exception( "Le mode de paiement doit contenir au moins 2 caractères." );
			}
		} else {
			throw new Exception( "Merci d'entrer un mode de paiement." );
		}
	}

	private void validationStatutPaiement( String statutPaiement ) throws Exception {
		if ( statutPaiement != null && statutPaiement.length() < 2 ) {
			throw new Exception( "Le statut de paiement doit contenir au moins 2 caractères." );
		}
	}

	private void validationModeLivraison( String modeLivraison ) throws Exception {
		if ( modeLivraison != null ) {
			if ( modeLivraison.length() < 2 ) {
				throw new Exception( "Le mode de livraison doit contenir au moins 2 caractères." );
			}
		} else {
			throw new Exception( "Merci d'entrer un mode de livraison." );
		}
	}

	private void validationStatutLivraison( String statutLivraison ) throws Exception {
		if ( statutLivraison != null && statutLivraison.length() < 2 ) {
			throw new Exception( "Le statut de livraison doit contenir au moins 2 caractères." );
		}
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur( String champ, String message ) {
		erreurs.put( champ, message );
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp( HttpServletRequest request, String champ ) {
		String valeur = ((String) request.getParameter(champ)).trim();
		return valeur.isBlank() ? null : valeur ;
	}
}