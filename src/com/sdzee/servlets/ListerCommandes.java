package com.sdzee.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Commande;

public class ListerCommandes extends HttpServlet {
	public static final String VUE = "/WEB-INF/listerCommandes.jsp";
	public static final String ATT_SESSION_COMMANDE_MAP = "sessionCommandeMap";
	public static final String ATT_COMMANDE_MAP = "commandeMap";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Map<Long, Commande> commandeMap = (Map<Long, Commande>) session.getAttribute(ATT_SESSION_COMMANDE_MAP);
		
		if ( commandeMap != null && !commandeMap.isEmpty() ) {
			req.setAttribute( ATT_COMMANDE_MAP, commandeMap );
		}
		else {
			req.setAttribute( ATT_COMMANDE_MAP, null );
		}
		
		req.getServletContext().getRequestDispatcher(VUE).forward(req, resp);
	}
}
