<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />
	  <title>Affichage du client</title>
	  <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
	</head>
<body>
	<!-- Add the menu -->
	<c:import url="inc/menu.jsp"/>
	
	<p class="info">${ clientForm.resultat }</p>
	<c:if test="${ empty clientForm.erreurs }">
		<p>Info du client:</p>
		<p>
			Nom : ${ client.nom } <br />
			Prénom: ${ client.prenom } <br />
			Adresse: ${ client.adresse } <br />
			Numéro de téléhone: ${ client.telephone } <br />
			Email: ${ client.email }
		</p>
	</c:if>
	

</body>
</html>