package com.sdzee.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.dao.ClientDao;
import com.sdzee.dao.DAOFactory;
import com.sdzee.forms.CreationClientForm;

@MultipartConfig()
public class CreationClient extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String CHEMIN     = "chemin";
	public static final String ATT_CLIENT = "client";
	public static final String ATT_FORM = "form";
	public static final String VUE_CREATION = "/WEB-INF/creerClient.jsp";
	public static final String VUE_AFFICHAGE = "/WEB-INF/afficherClient.jsp";
	
	public static final String MESSAGE_OK = "OK. Tous les champs sont remplis correctement.";
	public static final String MESSAGE_ERROR = "Erreur - Vous n'avez pas rempli tous les champs"
			+ " obligatoires.<br/>"
			+ "<a href='creationClient'>Cliquez ici</a> pour accéder au formulaire de création d'un client.";
	public static final String ATT_SESSION_CLIENT_MAP = "sessionClientMap";
	
	private ClientDao clientDao;
	
	@Override
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.clientDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ))
				.getClientDao();
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) 
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) 
			throws ServletException, IOException {
  	 	/*
       * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
       * dans le web.xml
       */
      String chemin = this.getServletConfig().getInitParameter( CHEMIN );
		/* Préparation de l'objet formulaire */
		CreationClientForm form = new CreationClientForm( clientDao );
		/* Traitement de la requête et récupération du bean en résultant */
  	 	Client client = form.creerClient(request, chemin);
		/* Récupération de la session depuis la requête */
  	 	HttpSession session = request.getSession();

  	 	
		/**
  	  	* Si aucune erreur de validation n'a eu lieu, alors ajout de la liste de
  	  	* Client à la session, sinon on fait rien
  	  	*/
  	 	if ( form.getErreurs().isEmpty() ) {
  	 		Map<Long, Client> clientMap = (Map<Long, Client>) session.getAttribute( ATT_SESSION_CLIENT_MAP );
  	 		if (clientMap != null) {
  	 			clientMap.put( client.getId(), client );
  	 			session.setAttribute( ATT_SESSION_CLIENT_MAP, clientMap );
  	 		}
  	 		else {
  	 			Map<Long, Client> map = new HashMap<Long, Client>();
  	 			map.put( client.getId(), client );
  	 			session.setAttribute( ATT_SESSION_CLIENT_MAP, map );
  	 		}
  	 	}
		
		request.setAttribute( ATT_CLIENT, client );
		request.setAttribute( ATT_FORM, form );
		
		if (form.getErreurs().isEmpty()) {
			this.getServletContext().getRequestDispatcher( VUE_AFFICHAGE ).forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward(request, response);
		}
		
	}
}
