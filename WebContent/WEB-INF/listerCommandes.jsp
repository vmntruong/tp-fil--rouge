<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

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
		<c:when test="${ !empty commandeMap }">
			<table>
				<tr>
					<th>Client</th>
					<th>Date</th>
					<th>Montant</th>
					<th>Mode de paiement</th>
					<th>Statut de paiement</th>
					<th>Mode de livraison</th>
					<th>Statut de livraison</th>
					<th class="action">Action</th>
				</tr>
				<c:forEach var="commande" items="${ commandeMap }">
					<tr id="${ commande.key }">
						<td>${ commande.value.client.nom } ${ commande.value.client.prenom }</td>
						<td><joda:format value="${ commande.value.date }" pattern="dd/MM/yyyy HH:mm:ss"></joda:format></td>
						<td>${ commande.value.montant }</td>
						<td>${ commande.value.modePaiement }</td>
						<td>${ commande.value.statutPaiement }</td>
						<td>${ commande.value.modeLivraison }</td>
						<td>${ commande.value.statutLivraison }</td>
						<td><a href="<c:url value="/suppressionCommande?id=${ commande.key }"/>">
							<img alt="supprimer" src="<c:url value="/inc/imgs/remove_icon.png" />" />
						</a></td>
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