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
				</tr>
				<c:forEach var="client" items="${ clientList }">
					<tr>
						<td>${ client.nom }</td>
						<td>${ client.prenom }</td>
						<td>${ client.adresse }</td>
						<td>${ client.telephone }</td>
						<td>${ client.email }</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	
</body>
</html>