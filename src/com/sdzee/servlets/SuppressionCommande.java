package com.sdzee.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Commande;

public class SuppressionCommande extends HttpServlet {
	public static final String VUE = "listerCommandes";
	public static final String PARAM_INDEX = "id";
	public static final String ATT_SESSION_COMMANDE_LIST = "sessionCommandeList";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the index parsed in the request param
		int index = Integer.parseInt( req.getParameter( PARAM_INDEX ) );
		
		// Get the session from the request
		HttpSession session = req.getSession();
		
		// Get the list of commandes from session
		List<Commande> commandeList = (List<Commande>) session.getAttribute( ATT_SESSION_COMMANDE_LIST );
		
		// Supprimer l'élément de la liste des clients
		if ( session != null ) {
			if ( index >= 0 && index < commandeList.size() ) {
				commandeList.remove( index );
				session.setAttribute( ATT_SESSION_COMMANDE_LIST, commandeList );
			}
		}
		
		// Display the list of Commandes again
		// this.getServletContext().getRequestDispatcher( VUE ).forward(req, resp);
		resp.sendRedirect(VUE);
	}
}
