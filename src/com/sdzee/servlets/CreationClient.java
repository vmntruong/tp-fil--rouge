package com.sdzee.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.forms.CreationClientForm;

public class CreationClient extends HttpServlet {
	public static final String ATT_CLIENT = "client";
	public static final String ATT_CLIENT_FORM = "clientForm";
	public static final String VUE_CREATION = "/WEB-INF/creerClient.jsp";
	public static final String VUE_AFFICHAGE = "/WEB-INF/afficherClient.jsp";
	
	public static final String MESSAGE_OK = "OK. Tous les champs sont remplis correctement.";
	public static final String MESSAGE_ERROR = "Erreur - Vous n'avez pas rempli tous les champs"
			+ " obligatoires.<br/>"
			+ "<a href='creationClient'>Cliquez ici</a> pour accéder au formulaire de création d'un client.";
	public static final String ATT_SESSION_CLIENT_LIST = "sessionClientList";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) 
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) 
			throws ServletException, IOException {
		
		Client monClient = new Client();
		CreationClientForm clientForm = new CreationClientForm();
		/* Récupération de la session depuis la requête */
  	 	HttpSession session = request.getSession();
		
  	 	monClient = clientForm.creerClient(request);
		
		/**
  	  	* Si aucune erreur de validation n'a eu lieu, alors ajout de la liste de
  	  	* Client à la session, sinon on fait rien
  	  	*/
  	 	if ( clientForm.getErreurs().isEmpty() ) {
  	 		List<Client> clientList = (List<Client>) session.getAttribute( ATT_SESSION_CLIENT_LIST );
  	 		if (clientList != null) {
  	 			clientList.add(monClient);
  	 			session.setAttribute( ATT_SESSION_CLIENT_LIST, clientList );
  	 		}
  	 		else {
  	 			List<Client> li = new ArrayList<Client>();
  	 			li.add(monClient);
  	 			session.setAttribute( ATT_SESSION_CLIENT_LIST, li );
  	 		}
  	 	}
		
		request.setAttribute( ATT_CLIENT, monClient );
		request.setAttribute( ATT_CLIENT_FORM, clientForm );
		
		if (clientForm.getErreurs().isEmpty()) {
			this.getServletContext().getRequestDispatcher( VUE_AFFICHAGE ).forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher( VUE_CREATION ).forward(request, response);
		}
		
	}
}
