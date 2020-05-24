package com.sdzee.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
import com.sdzee.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {
	public static final String ATT_COMMANDE = "commande";
	public static final String ATT_FORM     = "form";
	public static final String ATT_SESSION_COMMANDE_LIST = "sessionCommandeList";
	public static final String ATT_SESSION_CLIENT_LIST = "sessionClientList";
	public static final String ATT_CLIENT_LIST = "listClient";
	
	public static final String VUE_SUCCES   = "/WEB-INF/afficherCommande.jsp";
	public static final String VUE_FORM     = "/WEB-INF/creerCommande.jsp";
	
   public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
   	/* Récupération de la session de la requête */
   	HttpSession session = request.getSession();
   	/* Récupération de la liste des clients de la session */
   	List<Client> clientList = (List<Client>) session.getAttribute( ATT_SESSION_CLIENT_LIST );
   	
   	if ( clientList != null && !clientList.isEmpty() && clientList.size() > 0 ) {
   		request.setAttribute(ATT_CLIENT_LIST, clientList);
   	} else {
   		request.setAttribute(ATT_CLIENT_LIST, null);
   	}
   	
	   /* À la réception d'une requête GET, affichage du formulaire */
	   this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
   }

   public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
   	/* Préparation de l'objet formulaire */
   	CreationCommandeForm form = new CreationCommandeForm();
   	/* Traitement de la requête et récupération du bean en résultant */
   	Commande commande = form.creerCommande( request );
   	/* Récupération de la session de la requête */
   	HttpSession session = request.getSession();
        
   	/**
   	 * Si aucune erreur de validation n'a eu lieu, alors ajout de la liste de
   	 * Commande à la session, sinon on fait rien
   	 */
   	if ( form.getErreurs().isEmpty() ) {
   		List<Commande> commandeList = (List<Commande>) session.getAttribute( ATT_SESSION_COMMANDE_LIST );
   		if (commandeList != null) {
   			commandeList.add(commande);
   			session.setAttribute( ATT_SESSION_COMMANDE_LIST, commandeList );
   		}
   		else {
   			List<Commande> li = new ArrayList<Commande>();
   			li.add(commande);
   			session.setAttribute( ATT_SESSION_COMMANDE_LIST, li );
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