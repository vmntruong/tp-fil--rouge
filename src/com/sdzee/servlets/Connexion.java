package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Utilisateur;
import com.sdzee.forms.ConnexionForm;

public class Connexion extends HttpServlet {
	public static final String VUE = "/WEB-INF/connexion.jsp";
	public static final String ATT_FORM = "form";
	public static final String ATT_USER = "user";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnexionForm connexionForm = new ConnexionForm();
		Utilisateur utilisateur = connexionForm.seConnecterUtilisateur(request);
		
		if (connexionForm.getErreurs().isEmpty()) {
			
		}
		
		request.setAttribute(ATT_FORM, connexionForm);
		request.setAttribute(ATT_USER, utilisateur);
		
		this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
		
	}
}
