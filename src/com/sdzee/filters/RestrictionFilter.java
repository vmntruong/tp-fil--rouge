package com.sdzee.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RestrictionFilter implements Filter {
	private static final String ATT_SESSION_USER = "sessionUtilisateur";
	private static final String ACCES_CONNEXION = "/WEB-INF/connexion.jsp";

	public void init( FilterConfig config ) throws ServletException {
   }

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		/* Cast des objets request et response */
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) resp;
      
      /* Non-filtrage des ressources statiques et de la page de connexion*/
      String chemin = request.getRequestURI().substring( request.getContextPath().length() );
      if ( chemin.startsWith( "/inc" ) || chemin.startsWith( "/connexion" )) {
          chain.doFilter( request, response );
          return;
      }
      
      /* Récupération de la session depuis la requête */
      HttpSession session = request.getSession();

      /**
       * Si l'objet utilisateur n'existe pas dans la session en cours, alors
       * l'utilisateur n'est pas connecté.
       */
      if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
          /* Redirection vers la page de connexion */
          request.getRequestDispatcher( ACCES_CONNEXION ).forward(request, response);
      } else {
          /* Affichage de la page restreinte */
          chain.doFilter( request, response );
      }
		
	}

	public void destroy() {
   }
}
