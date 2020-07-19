package com.sdzee.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.sdzee.beans.Client;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.DAOException;

import eu.medsea.mimeutil.MimeUtil;

public class CreationClientForm {
	private static final String CHAMP_NOM_CLIENT = "nomClient";
	private static final String CHAMP_PRENOM_CLIENT = "prenomClient";
	private static final String CHAMP_ADRESSE_CLIENT = "adresseClient";
	private static final String CHAMP_TELEPHONE_CLIENT = "telephoneClient";
	private static final String CHAMP_EMAIL_CLIENT = "emailClient";
	private static final String CHAMP_IMAGE     = "imageClient";
	
	private static final int    TAILLE_TAMPON     = 8192; // 8K
	
	public static final String REGEX_TELEPHONE_VALID = "^[0-9]{4}[0-9]*$";
	public static final String REGEX_EMAIL_VALID = "^[^.+-][A-Za-z0-9_.+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	private ClientDao clientDao;
	
	/**
	 * Constructeur
	 * 
	 * @param clientDao
	 */
	public CreationClientForm( ClientDao clientDao ) {
		super();
		this.clientDao = clientDao;
	}
	
	public String getResultat() {
		return resultat;
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public Client creerClient(HttpServletRequest request, String chemin) {
		// Récupération des champs du client
		String nomClient = getValeurChamp(request, CHAMP_NOM_CLIENT);
		String prenomClient = getValeurChamp(request, CHAMP_PRENOM_CLIENT);
		String adresseClient = getValeurChamp(request, CHAMP_ADRESSE_CLIENT);
		String telephoneClient = getValeurChamp(request, CHAMP_TELEPHONE_CLIENT);
		String emailClient = getValeurChamp(request, CHAMP_EMAIL_CLIENT);
		
		Client client = new Client();
		
		try {
			traiterNomClient( nomClient, client );
			traiterPrenomClient( prenomClient, client );
			traiterAdresseClient( adresseClient, client );
			traiterTelephoneClient( telephoneClient, client );
			traiterEmailClient( emailClient, client );
			recupererEtTraiterImage( request, CHAMP_IMAGE, client, chemin);
			
			if ( erreurs.isEmpty() ) {
				clientDao.creer( client );
				resultat = "Succès de création du client.";
			} else {
				resultat = "Echec de création du client.";
			}
		} catch( DAOException e ) {
			resultat = "Échec de la création : une erreur imprévue est survenue, "
					+ "merci de réessayer dans quelques instants.";
	      e.printStackTrace();
		}
		
		return client;
	}
	
	/**
	 * Récupération du nom de l'image
	 * 
	 * @param request
	 * @param champImage
	 * @return
	 */
	private void recupererEtTraiterImage( HttpServletRequest request, String champImage, Client client, String chemin ) {
		/*
       * Récupération du contenu du champ image du formulaire. 
       * Il faut ici utiliser la méthode getPart().
       */
      String nomImage = null;
      InputStream contenuImage = null;
      
      try {
          Part part = request.getPart( CHAMP_IMAGE );
          /*
           * Il faut déterminer s'il s'agit bien d'un champ de type fichier :
           * on délègue cette opération à la méthode utilitaire
           * getNomFichier().
           */
          nomImage = getNomFichier( part );

          /*
           * Si la méthode a renvoyé quelque chose, il s'agit donc d'un
           * champ de type fichier (input type="file").
           */
          if ( nomImage != null && !nomImage.isEmpty() ) {
              /*
               * Antibug pour Internet Explorer, qui transmet pour une
               * raison mystique le chemin du fichier local à la machine
               * du client...
               * 
               * Ex : C:/dossier/sous-dossier/fichier.ext
               * 
               * On doit donc faire en sorte de ne sélectionner que le nom
               * et l'extension du fichier, et de se débarrasser du
               * superflu.
               */
         	 nomImage = nomImage.substring( nomImage.lastIndexOf( '/' ) + 1 )
                      .substring( nomImage.lastIndexOf( '\\' ) + 1 );
         	 contenuImage = part.getInputStream();
          }
      } catch ( IllegalStateException e ) {
          /*
           * Exception retournée si la taille des données dépasse les limites
           * définies dans la section <multipart-config> de la déclaration de
           * notre servlet d'upload dans le fichier web.xml
           */
          e.printStackTrace();
          erreurs.put( CHAMP_IMAGE, "La taille de l'image ne peut pas dépasser 1M." );
      } catch ( IOException e ) {
          /*
           * Exception retournée si une erreur au niveau des répertoires de
           * stockage survient (répertoire inexistant, droits d'accès
           * insuffisants, etc.)
           */
          e.printStackTrace();
          erreurs.put( CHAMP_IMAGE, "Erreur de configuration du serveur." );
      } catch ( ServletException e ) {
          /*
           * Exception retournée si la requête n'est pas de type
           * multipart/form-data. Cela ne peut arriver que si l'utilisateur
           * essaie de contacter la servlet d'upload par un formulaire
           * différent de celui qu'on lui propose... pirate ! :|
           */
          e.printStackTrace();
          erreurs.put( CHAMP_IMAGE,
                  "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
      }
      
      /* Si aucune erreur n'est survenue jusqu'à présent */
      if ( erreurs.isEmpty() ) {
          /* Validation du champ image. */
          try {
         	 validationAndWriteImage( nomImage, contenuImage, chemin );
          } catch ( Exception e ) {
         	 erreurs.put( CHAMP_IMAGE, e.getMessage() );
          }
          client.setImage( nomImage );
      }
	}

	private void traiterEmailClient(String emailClient, Client client) {
		try {
			validationEmailClient(emailClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_EMAIL_CLIENT, e.getMessage());
		}
		client.setEmail(emailClient);		
	}

	private void traiterTelephoneClient(String telephoneClient, Client client) {
		try {
			validationTelephoneClient(telephoneClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_TELEPHONE_CLIENT, e.getMessage());
		}
		client.setTelephone(telephoneClient);		
	}

	private void traiterAdresseClient(String adresseClient, Client client) {
		try {
			validationAdresseClient(adresseClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_ADRESSE_CLIENT, e.getMessage());
		}
		client.setAdresse(adresseClient);
		
	}

	private void traiterPrenomClient(String prenomClient, Client client) {
		try {
			validationPrenomClient(prenomClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_PRENOM_CLIENT, e.getMessage());
		}
		client.setPrenom(prenomClient);
		
	}

	private void traiterNomClient( String nomClient, Client client ) {
		try {
			validationNomClient( nomClient );
		} catch ( Exception e ) {
			erreurs.put( CHAMP_NOM_CLIENT, e.getMessage() );
		}
		client.setNom( nomClient );
		
	}

	/* Pour valider le prenom */
	private void validationPrenomClient(String prenomClient) throws Exception {
		if ( prenomClient != null ) {
			if ( prenomClient.length() < 2 ) {
				throw new Exception("Le prenom doit contenir au moins 2 caractères");
			}
		}
	}
	
	/* Pour valider le nom */
	private void validationNomClient(String nomClient) throws Exception {
		if ( nomClient == null || nomClient.length() < 2 ) {
			throw new Exception("Le nom doit contenir au moins 2 caractères");
		}
	}
	
	/* Pour valider l'adresse */
	private void validationAdresseClient(String adresseClient) throws Exception {
		if ( adresseClient == null ) {
			throw new Exception("Adresse est vide");
		}
	}
	
	/* Pour valider l'adresse */
	private void validationTelephoneClient(String telephoneClient) throws Exception {
		if ( telephoneClient == null || !telephoneClient.matches( REGEX_TELEPHONE_VALID ) ) {
			throw new Exception("Numéro de téléphone doit contenir au moins 4 numéros");
		}
	}
	
	/* Pour valider l'adresse */
	private void validationEmailClient(String emailClient) throws Exception {
		if ( emailClient != null ) {
			if ( !emailClient.matches(REGEX_EMAIL_VALID) ) {
				throw new Exception("Adresse email est invalide");
			}
		}
	}
	
	/**
    * Valide l'image envoyée.
    */
   private void validationAndWriteImage( String nomFichier, InputStream contenuFichier, String chemin ) throws Exception {
   	/**
       * Problem 4: Champ image n'est pas obligatoire
       */
   	if ( nomFichier != null && contenuFichier != null ) {
   		/**
          * Vérifier si c'est bien une image
          */
         /* Extraction du type MIME du fichier depuis l'InputStream nommé "contenu" */
         MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
         Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

         /*
          * Si le fichier est bien une image, alors son en-tête MIME
          * commence par la chaîne "image"
          */
         if ( !mimeTypes.toString().startsWith( "image" ) ) {
         	throw new Exception( "Le fichier selectioné n'est pas une image" );
         }
         
         /* Écriture du fichier sur le disque */
         try {
             ecrireFichier( contenuFichier, nomFichier, chemin );
         } catch ( Exception e ) {
        	 erreurs.put( CHAMP_IMAGE, "Erreur lors de l'écriture du fichier sur le disque." );
         }
   	}
   }
	
	private String getValeurChamp(HttpServletRequest request, String champ) {
		String valeur = ((String) request.getParameter(champ));
		return ( valeur == null || valeur.isBlank() ) ? null : valeur.trim() ;
	}
	
	/*
    * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
    * "content-disposition", et de vérifier si le paramètre "filename" y est
    * présent. Si oui, alors le champ traité est de type File et la méthode
    * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
    * la méthode retourne null.
    */
   private static String getNomFichier( Part part ) {
       /* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
       for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
           /* Recherche de l'éventuelle présence du paramètre "filename". */
           if ( contentDisposition.trim().startsWith( "filename" ) ) {
               /*
                * Si "filename" est présent, alors renvoi de sa valeur,
                * c'est-à-dire du nom de fichier sans guillemets.
                */
               return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
           }
       }
       /* Et pour terminer, si rien n'a été trouvé... */
       return null;
   }
   
   /*
    * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
    * sur le disque, dans le répertoire donné et avec le nom donné.
    */
   private void ecrireFichier( InputStream contenu, String nomFichier, String chemin ) throws Exception {
       /* Prépare les flux. */
       BufferedInputStream entree = null;
       BufferedOutputStream sortie = null;
       try {
           /* Ouvre les flux. */
           entree = new BufferedInputStream( contenu, TAILLE_TAMPON );
           sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                   TAILLE_TAMPON );

           /*
            * Lit le fichier reçu et écrit son contenu dans un fichier sur le
            * disque.
            */
           byte[] tampon = new byte[TAILLE_TAMPON];
           int longueur = 0;
           longueur = entree.read( tampon );
           while ( longueur > 0 ) {
               sortie.write( tampon, 0, longueur );
               longueur = entree.read( tampon );
           }
       } finally {
           try {
               sortie.close();
           } catch ( IOException ignore ) {
           }
           try {
               entree.close();
           } catch ( IOException ignore ) {
           }
       }
   }
}
