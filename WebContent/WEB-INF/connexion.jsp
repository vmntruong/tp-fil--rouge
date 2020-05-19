<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Connexion</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<!-- Include the menu -->
	<c:import url="inc/menu.jsp"></c:import>
	<form method="post" action="connexion">
		<fieldset>
			<legend>Info de connexion</legend>
			
			<label for="email">Email:<span class="requis">*</span></label>
			<input type="text" id="email" name="email" size="20" maxlength="20" 
			value="${ param.email }" />
			<span class="erreur">${ form.erreurs['email'] }</span>
			<br/>
			
			<label for="motDePasse">Mot de passe:<span class="requis">*</span></label>
			<input type="password" id="motDePasse" name="motDePasse" size="20" maxlength="20" 
			value="" />
			<span class="erreur">${ form.erreurs['motDePasse'] }</span>
			<br/>
		</fieldset>
		
		<p class="${ empty form.erreurs? 'succes' : 'info' }">${ form.resultat }</p>
		<c:if test="${ !empty sessionScope.sessionUtilisateur }">
			<p class="succes">Vous êtes connecté(e) sous <c:out value="${ sessionScope.sessionUtilisateur.email }" /></p>
		</c:if>
		<input type="submit" value="Valider"  />
	  <input type="reset" value="Remettre à zéro" /> <br />
	</form>
</body>
</html>