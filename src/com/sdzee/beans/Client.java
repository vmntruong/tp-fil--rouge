package com.sdzee.beans;

/**
 * @author Nhon
 * Client Bean
 *
 */
public class Client {
	/* Nom du client */
	private String nom;
	
	/* Prénom du client */
	private String prenom;
	
	/* Adresse de livraison du client */
	private String adresse;
	
	/* Téléphone du client */
	private String telephone;
	
	/* Email du client */
	private String email;
	
	/* Image du client */
	private String image;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
