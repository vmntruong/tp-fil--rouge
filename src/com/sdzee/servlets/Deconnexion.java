package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Deconnexion extends HttpServlet {
	public static final String VUE_DECONNEXION = "/WEB-INF/deconnexion.jsp";
	public static final String LINK_REDIRECT = "/connexion";
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.invalidate();
		//this.getServletContext().getRequestDispatcher( VUE_DECONNEXION ).forward(request, response);
		response.sendRedirect(request.getContextPath() + LINK_REDIRECT);
	}
}
