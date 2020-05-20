<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Liste de commandes</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<!-- Import the menu -->
	<c:import url="inc/menu.jsp"></c:import>
	<c:choose>
		<c:when test="${ !empty commandeList }">
			<table>
				<tr>
					<th>Client</th>
					<th>Date</th>
					<th>Montant</th>
					<th>Mode de paiement</th>
					<th>Statut de paiement</th>
					<th>Mode de livraison</th>
					<th>Statut de livraison</th>
				</tr>
				<c:forEach var="commande" items="${ commandeList }">
					<tr>
						<td>${ commande.client.nom } ${ commande.client.prenom }</td>
						<td>${ commande.date }</td>
						<td>${ commande.montant }</td>
						<td>${ commande.modePaiement }</td>
						<td>${ commande.statutPaiement }</td>
						<td>${ commande.modeLivraison }</td>
						<td>${ commande.statutLivraison }</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p class="info">Aucune commande créée</p>
		</c:otherwise>
	</c:choose>
	
</body>
</html>