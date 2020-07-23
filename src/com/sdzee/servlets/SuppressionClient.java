package com.sdzee.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;

public class SuppressionClient extends HttpServlet {
	public static final String VUE = "listerClients";
	public static final String PARAM_INDEX = "id";
	public static final String ATT_SESSION_CLIENT_MAP = "sessionClientMap";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the index parsed in the request param
		long index = Long.parseLong( req.getParameter( PARAM_INDEX ) );
		
		// Get the session from the request
		HttpSession session = req.getSession();
		
		// Get the list of clients from session
		Map<Long, Client> clientMap = (Map<Long, Client>) session.getAttribute( ATT_SESSION_CLIENT_MAP );
		
		// Supprimer l'élément de la liste des clients
		if ( session != null ) {
			if ( index >= 0 ) {
				clientMap.remove( index );
				session.setAttribute( ATT_SESSION_CLIENT_MAP, clientMap );
			}
		}
		
		// Display the list of Clients again
		// this.getServletContext().getRequestDispatcher( VUE ).forward(req, resp);
		resp.sendRedirect(VUE);
	}
}
