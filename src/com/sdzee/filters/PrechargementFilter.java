package com.sdzee.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.CommandeDao;
import com.sdzee.dao.DAOFactory;

public class PrechargementFilter implements Filter {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_SESSION_CLIENT_MAP = "sessionClientMap";
	public static final String ATT_SESSION_COMMANDE_MAP = "sessionCommandeMap";
	
	private ClientDao clientDao;
	private CommandeDao commandeDao;
	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		DAOFactory daoFactory = (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY );
		this.clientDao = daoFactory.getClientDao();
		this.commandeDao = daoFactory.getCommandeDao();
	}

	@Override
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
			throws IOException, ServletException {
		/* Cast de l'objet request */
		HttpServletRequest request = (HttpServletRequest) req;
		
		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();
		
		/* Récupération de la liste des clients existants et enregistrement en session */
		if ( session.getAttribute( ATT_SESSION_CLIENT_MAP ) == null ) {
			List<Client> listeClients = clientDao.lister();
			Map<Long, Client> mapClients = new HashMap<Long, Client>();
			for ( Client client : listeClients ) {
				mapClients.put( client.getId(), client );
			}
			session.setAttribute( ATT_SESSION_CLIENT_MAP, mapClients );
		}
		
		/* Récupération de la liste des commandes existants et enregistrement en session */
		if ( session.getAttribute( ATT_SESSION_COMMANDE_MAP ) == null ) {
			List<Commande> listeCommandes = commandeDao.lister();
			Map<Long, Commande> mapCommandes = new HashMap<Long, Commande>();
			for ( Commande commande : listeCommandes ) {
				mapCommandes.put( commande.getId(), commande );
			}
			session.setAttribute( ATT_SESSION_COMMANDE_MAP, mapCommandes );
		}
		
		/* Pour terminer, continue la requête en cours */
		chain.doFilter( req, res );
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
	
	
}
