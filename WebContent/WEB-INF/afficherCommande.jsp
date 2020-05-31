<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />
	  <title>Affichage de la commande</title>
	  <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
	</head>
<body>
	<!-- Adding the menu -->
	<c:import url="inc/menu.jsp"/>
	
	<p class="info">${ commandeForm.resultat }</p>
	<p>
		Info du client: 
	</p>
	<p>
		Nom : ${ commande.client.nom } <br />
		Prénom: ${ commande.client.prenom } <br />
		Adresse: ${ commande.client.adresse } <br />
		Numéro de téléhone: ${ commande.client.telephone } <br />
		Email: ${ commande.client.email }
		Image: ${ commande.client.image }
	</p>
	
	<p>
		Info de la commande:
	</p>
	<p>
		Date : ${ commande.date } <br />
		Montant: ${ commande.montant } <br />
		Mode de paiement: ${ commande.modePaiement } <br />
		Statut de paiement: ${ commande.statutPaiement } <br />
		Mode de livraison: ${ commande.modeLivraison } <br />
		Statut de livraison: ${ commande.statutLivraison }
	</p>
</body>
</html>