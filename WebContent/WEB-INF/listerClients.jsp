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
		<c:when test="${ !empty clientMap }">
			<table>
				<tr>
					<th>Nom</th>
					<th>Prénom</th>
					<th>Adresse</th>
					<th>Téléphone</th>
					<th>Email</th>
					<th>Image</th>
					<th class="action">Action</th>
				</tr>
				<c:forEach var="client" items="${ clientMap }">
					<tr id="${ client.key }">
						<td>${ client.value.nom }</td>
						<td>${ client.value.prenom }</td>
						<td>${ client.value.adresse }</td>
						<td>${ client.value.telephone }</td>
						<td>${ client.value.email }</td>
						<td><c:if test="${ !empty client.value.image }"><a href="<c:url value="fichiers/${ client.value.image }" />">Voir</a></c:if></td>
						<td><a href="<c:url value="/suppressionClient?id=${ client.key }"/>">
							<img alt="supprimer" src="<c:url value="/inc/imgs/remove_icon.png" />" />
						</a></td>
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