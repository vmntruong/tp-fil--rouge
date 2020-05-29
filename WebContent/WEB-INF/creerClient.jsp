<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />
	  <title>Création d'un client</title>
	  <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
	</head>
	<body>
		<c:import url="inc/menu.jsp"></c:import>
	  <div>
	    <form method="post" action="creationClient" enctype="multipart/form-data">
	    	<fieldset>
	    	<legend>Info du client</legend>
		    	<c:import url="inc/creerClientFieldSet.jsp"></c:import>
	      </fieldset>
	      <input type="submit" value="Valider"  />
	      <input type="reset" value="Remettre à zéro" /> <br />
	    </form>	
	  </div>	
	</body>
</html>