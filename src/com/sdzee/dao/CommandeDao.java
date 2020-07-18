package com.sdzee.dao;

import java.util.List;

import com.sdzee.beans.Commande;

public interface CommandeDao {
	// Créer une commande
	void creer( Commande commande ) throws DAOException;
	
	// Trouver une commande
	Commande trouver( Long id ) throws DAOException;
	
	// Lister des commandes
	List<Commande> lister() throws DAOException;
	
	// Supprimer une commande
	void supprimer( Long id ) throws DAOException;
}
