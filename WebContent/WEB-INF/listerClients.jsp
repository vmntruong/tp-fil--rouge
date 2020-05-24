<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Liste of clients</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<!-- Import the menu -->
	<c:import url="inc/menu.jsp"></c:import>
	<c:choose>
		<c:when test="${ !empty clientList }">
			<table>
				<tr>
					<th>Nom</th>
					<th>Prénom</th>
					<th>Adresse</th>
					<th>Téléphone</th>
					<th>Email</th>
					<th class="action">Action</th>
				</tr>
				<c:set var="index" value="0"/>
				<c:forEach var="client" items="${ clientList }">
					<tr id="${ index }">
						<td>${ client.nom }</td>
						<td>${ client.prenom }</td>
						<td>${ client.adresse }</td>
						<td>${ client.telephone }</td>
						<td>${ client.email }</td>
						<td><a href="<c:url value="/suppressionClient?id=${ index }"/>">
							<img alt="supprimer" src="<c:url value="/inc/imgs/remove_icon.png" />" />
						</a></td>
						<c:set var="index" value="${ index+1 }" />
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p class="info">Aucun client créé</p>
		</c:otherwise>
	</c:choose>
	
</body>
</html>