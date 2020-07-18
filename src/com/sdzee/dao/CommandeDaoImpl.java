package com.sdzee.dao;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;

public class CommandeDaoImpl implements CommandeDao {
	
	private static final String SQL_SELECT        = 
			"SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande ORDER BY id";
	private static final String SQL_SELECT_PAR_ID = 
			"SELECT id, id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison FROM Commande WHERE id = ?";
	private static final String SQL_INSERT        = 
			"INSERT INTO Commande (id_client, date, montant, mode_paiement, statut_paiement, mode_livraison, statut_livraison) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID = 
			"DELETE FROM Commande WHERE id = ?";
	
	private DAOFactory daoFactory;
	private ClientDao clientDao;
	
	/*
	 * Constructeur avec daoFactory
	 */
	public CommandeDaoImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
		clientDao = new ClientDaoImpl( daoFactory );
	}
	
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des clients (un
	 * ResultSet) et un bean Client.
	 */
	private Commande map( ResultSet resultSet ) throws SQLException {
		
		Commande commande = new Commande();
		commande.setId( resultSet.getLong( "id" ) );
		
		Client client = clientDao.trouver( resultSet.getLong( "id_client" ) );
		commande.setClient( client );
		
		commande.setDate( new DateTime( resultSet.getTimestamp( "date" ) ) );
		commande.setMontant( resultSet.getDouble( "montant" ) );
		commande.setModePaiement( resultSet.getString( "mode_paiement" ) );
		commande.setStatutPaiement( resultSet.getString( "statut_paiement" ) );
		commande.setModeLivraison( resultSet.getString( "mode_livraison" ) );
		commande.setStatutLivraison( resultSet.getString( "statut_livraison" ) );
		
		return commande;
		
	}

	@Override
	public void creer( Commande commande ) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,
					commande.getClient().getId(),
					commande.getDate(),
					commande.getMontant(),
					commande.getModePaiement(),
					commande.getStatutPaiement(),
					commande.getModeLivraison(),
					commande.getStatutLivraison());
			
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException("Échec de la création de la commande, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				commande.setId( valeursAutoGenerees.getLong( 1 ) );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}

	}

	@Override
	public Commande trouver( Long id ) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Commande commande = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, 
					false, id );
			resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() ) {
				commande = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
				
		return commande;
	}

	@Override
	public List<Commande> lister() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Commande> listCommandes = new ArrayList<Commande>();
		Commande commande = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT, false );
			resultSet = preparedStatement.executeQuery();
			while ( resultSet.next() ) {
				commande = map( resultSet );
				listCommandes.add( commande );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return listCommandes;
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
				throw new DAOException("Échec de la suppression de la commande, aucune ligne supprimée de la table.");
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}

	}

}
