package com.sdzee.dao;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdzee.beans.Client;
import com.sdzee.beans.Utilisateur;

public class ClientDaoImpl implements ClientDao {
	
	private static final String SQL_SELECT = 
			"SELECT id, nom, prenom, adresse, telephone, email, image FROM Client ORDER BY id";
	private static final String SQL_SELECT_PAR_ID = 
			"SELECT id, nom, prenom, adresse, telephone, email, image FROM Client WHERE id = ?";
	private static final String SQL_INSERT = 
			"INSERT INTO Client (nom, prenom, adresse, telephone, email, image) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID = 
			"DELETE FROM Client WHERE id = ?";
	
	private DAOFactory daoFactory;
	
	public ClientDaoImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}
	
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des clients (un
	 * ResultSet) et un bean Client.
	 */
	private static Client map( ResultSet resultSet ) throws SQLException {
		Client client = new Client();
		client.setId( resultSet.getLong( "id" ) );
		client.setNom( resultSet.getString( "nom" ) );
		client.setPrenom( resultSet.getString( "prenom" ) );
		client.setAdresse( resultSet.getString( "adresse" ) );
		client.setTelephone( resultSet.getString( "telephone" ) );
		client.setEmail( resultSet.getString( "email" ) );
		client.setImage( resultSet.getString( "image" ) );
		
		return client;
		
	}

	@Override
	public void creer( Client client ) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
					client.getNom(),
					client.getPrenom(),
					client.getAdresse(),
					client.getTelephone(),
					client.getEmail(),
					client.getImage());
			
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException("Échec de la création du client, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				client.setId( valeursAutoGenerees.getLong( 1 ) );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}

	}

	@Override
	public Client trouver( Long id ) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Client client = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, 
					false, id );
			resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() ) {
				client = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
				
		return client;

	}

	@Override
	public List<Client> lister() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Client> listClients = new ArrayList<Client>();
		Client client = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT, false );
			resultSet = preparedStatement.executeQuery();
			while ( resultSet.next() ) {
				client = map( resultSet );
				listClients.add( client );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return listClients;

	}

	@Override
	public void supprimer( Long id ) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, 
					false, id );
			int statut = preparedStatement.executeUpdate();
			if ( statut == 0 ) {
				throw new DAOException("Échec de la suppression du client, aucune ligne supprimée de la table.");
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	}

}
