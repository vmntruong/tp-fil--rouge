package com.sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Client;

public class CreationClientForm {
	private static final String CHAMP_NOM_CLIENT = "nomClient";
	private static final String CHAMP_PRENOM_CLIENT = "prenomClient";
	private static final String CHAMP_ADRESSE_CLIENT = "adresseClient";
	private static final String CHAMP_TELEPHONE_CLIENT = "telephoneClient";
	private static final String CHAMP_EMAIL_CLIENT = "emailClient";
	
	public static final String REGEX_TELEPHONE_VALID = "^[0-9]{4}[0-9]*$";
	public static final String REGEX_EMAIL_VALID = "^[^.+-][A-Za-z0-9_.+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat() {
		return resultat;
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public Client creerClient(HttpServletRequest request) {
		// Récupération des champs du client
		String nomClient = getValeurChamp(request, CHAMP_NOM_CLIENT);
		String prenomClient = getValeurChamp(request, CHAMP_PRENOM_CLIENT);
		String adresseClient = getValeurChamp(request, CHAMP_ADRESSE_CLIENT);
		String telephoneClient = getValeurChamp(request, CHAMP_TELEPHONE_CLIENT);
		String emailClient = getValeurChamp(request, CHAMP_EMAIL_CLIENT);
		
		Client client = new Client();
		
		try {
			validationNomClient(nomClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_NOM_CLIENT, e.getMessage());
		}
		client.setNom(nomClient);
		
		try {
			validationPrenomClient(prenomClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_PRENOM_CLIENT, e.getMessage());
		}
		client.setPrenom(prenomClient);
		
		try {
			validationAdresseClient(adresseClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_ADRESSE_CLIENT, e.getMessage());
		}
		client.setAdresse(adresseClient);
		
		try {
			validationTelephoneClient(telephoneClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_TELEPHONE_CLIENT, e.getMessage());
		}
		client.setTelephone(telephoneClient);
		
		try {
			validationEmailClient(emailClient);
		} catch (Exception e) {
			erreurs.put(CHAMP_EMAIL_CLIENT, e.getMessage());
		}
		client.setEmail(emailClient);
		
		if (erreurs.isEmpty()) {
			resultat = "Succès de la création du client";
		} else {
			resultat = "Échec de la création du client";
		}
		
		return client;
	}
	
	/* Pour valider le prenom */
	private void validationPrenomClient(String prenomClient) throws Exception {
		if ( prenomClient == null || prenomClient.length() < 2 ) {
			throw new Exception("Le prenom doit contenir au moins 2 caractères");
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
		if ( emailClient==null || !emailClient.matches(REGEX_EMAIL_VALID) ) {
			throw new Exception("Adresse email est invalide");
		}
	}
	
	private String getValeurChamp(HttpServletRequest request, String champ) {
		String valeur = ((String) request.getParameter(champ)).trim();
		return valeur.isBlank() ? null : valeur ;
	}
}
