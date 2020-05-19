package com.sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Utilisateur;

public class ConnexionForm {
	public static final String CHAMP_EMAIL_CONNEXION = "email";
	public static final String CHAMP_PASSWORD_CONNEXION = "motDePasse";
	public static final String REGEX_EMAIL_VALID = "^[^.+-][A-Za-z0-9_.+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat() {
		return resultat;
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public Utilisateur seConnecterUtilisateur(HttpServletRequest request) {
		// Récupération des champs du client
		String email = getValeurChamp(request, CHAMP_EMAIL_CONNEXION);
		String motDePasse = getValeurChamp(request, CHAMP_PASSWORD_CONNEXION);
		
		Utilisateur utilisateur = new Utilisateur();
		
		try {
			validationEmail(email);
		} catch (Exception e) {
			erreurs.put(CHAMP_EMAIL_CONNEXION, e.getMessage());
		}
		utilisateur.setEmail(email);
		
		try {
			validationMotDePasse(motDePasse);
		} catch (Exception e) {
			erreurs.put(CHAMP_PASSWORD_CONNEXION, e.getMessage());
		}
		utilisateur.setMotDePasse(motDePasse);
		
		if (erreurs.isEmpty()) {
			resultat = "Succès de la connexion";
		} else {
			resultat = "Échec de la connexion";
		}
		
		return utilisateur;
	}
	
	private void validationMotDePasse(String motDePasse) throws Exception {
		if ( motDePasse == null || motDePasse.length() < 2 )
			throw new Exception("Mot de passe contient au moins 2 charactères");
	}
	private void validationEmail(String email) throws Exception {
		if ( email == null || !email.matches(REGEX_EMAIL_VALID) )
			throw new Exception("Adresse email invalide");
	}
	private String getValeurChamp(HttpServletRequest request, String champ) {
		String valeur = ((String) request.getParameter(champ)).trim();
		return valeur.isBlank() ? null : valeur ;
	}
}
