package com.sdzee.dao;

import java.util.List;

import com.sdzee.beans.Client;

public interface ClientDao {
	// Créer un client
	void creer( Client client ) throws DAOException;
	
	// Trouver un client
	Client trouver( Long id ) throws DAOException;
	
	// Lister des clients
	List<Client> lister() throws DAOException;
	
	// Supprimer un client
	void supprimer( Long id ) throws DAOException;
}
