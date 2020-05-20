package com.sdzee.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Client;

public class ListerCommandes extends HttpServlet {
	public static final String VUE = "/WEB-INF/listerCommandes.jsp";
	public static final String ATT_SESSION_COMMANDE_LIST = "sessionCommandeList";
	public static final String ATT_COMMANDE_LIST = "commandeList";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		List<Client> clientList = (List<Client>) session.getAttribute(ATT_SESSION_COMMANDE_LIST);
		
		if ( clientList != null && !clientList.isEmpty() ) {
			req.setAttribute(ATT_COMMANDE_LIST, clientList);
		}
		else {
			req.setAttribute(ATT_COMMANDE_LIST, null);
		}
		
		req.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
	}
}