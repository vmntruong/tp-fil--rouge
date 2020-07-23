package com.sdzee.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.CommandeDao;
import com.sdzee.dao.DAOFactory;
import com.sdzee.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_COMMANDE = "commande";
	public static final String ATT_FORM     = "form";
	public static final String ATT_SESSION_COMMANDE_MAP = "sessionCommandeMap";
	public static final String ATT_SESSION_CLIENT_MAP = "sessionClientMap";
	public static final String ATT_CLIENT_MAP = "clientMap";
	
	public static final String CHEMIN     = "chemin";
	
	public static final String VUE_SUCCES   = "/WEB-INF/afficherCommande.jsp";
	public static final String VUE_FORM     = "/WEB-INF/creerCommande.jsp";
	
	private CommandeDao commandeDao;
	private ClientDao clientDao;
	
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY );
		this.clientDao = daoFactory.getClientDao();
		this.commandeDao = daoFactory.getCommandeDao();
	}
	
   public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
   	/* Récupération de la session de la requête */
   	HttpSession session = request.getSession();
   	/* Récupération de la liste des clients de la session */
   	Map<Long, Client> clientMap = (Map<Long, Client>) session.getAttribute( ATT_SESSION_CLIENT_MAP );
   	
   	if ( clientMap != null && !clientMap.isEmpty() && clientMap.size() > 0 ) {
   		request.setAttribute(ATT_CLIENT_MAP, clientMap);
   	} else {
   		request.setAttribute(ATT_CLIENT_MAP, null);
   	}
   	
	   /* À la réception d'une requête GET, affichage du formulaire */
	   this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
   }

   public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
   	/*
       * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
       * dans le web.xml
       */
      String chemin = this.getServletConfig().getInitParameter( CHEMIN );
   	/* Préparation de l'objet formulaire */
   	CreationCommandeForm form = new CreationCommandeForm( commandeDao, clientDao );
   	/* Traitement de la requête et récupération du bean en résultant */
   	Commande commande = form.creerCommande( request, chemin );
   	/* Récupération de la session de la requête */
   	HttpSession session = request.getSession();
        
   	/**
   	 * Si aucune erreur de validation n'a eu lieu, alors ajout de la liste de
   	 * Commande à la session, sinon on fait rien
   	 */
   	if ( form.getErreurs().isEmpty() ) {
   		Map<Long, Commande> commandeMap = (Map<Long, Commande>) session.getAttribute( ATT_SESSION_COMMANDE_MAP );
   		if (commandeMap != null) {
   			commandeMap.put( commande.getId(), commande );
   			session.setAttribute( ATT_SESSION_COMMANDE_MAP, commandeMap );
   		}
   		else {
   			Map<Long, Commande> map = new HashMap<Long, Commande>();
   			map.put( commande.getId(), commande );
   			session.setAttribute( ATT_SESSION_COMMANDE_MAP, map );
   		}
   	}

   	/* Ajout du bean et de l'objet métier à l'objet requête */
   	request.setAttribute( ATT_COMMANDE, commande );
   	request.setAttribute( ATT_FORM, form );

		if ( form.getErreurs().isEmpty() ) {
			/* Si aucune erreur, alors affichage de la fiche récapitulative */
			this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
		} else {
			/* Sinon, ré-affichage du formulaire de création avec les erreurs */
			this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
		}
	}
}