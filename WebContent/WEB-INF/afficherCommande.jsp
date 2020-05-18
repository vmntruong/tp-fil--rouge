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
		Nom : ${ client.nom } <br />
		Prénom: ${ client.prenom } <br />
		Adresse: ${ client.adresse } <br />
		Numéro de téléhone: ${ client.telephone } <br />
		Email: ${ client.email }
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