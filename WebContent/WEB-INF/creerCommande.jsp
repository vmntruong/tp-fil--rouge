<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
	  <meta charset="utf-8" />
	  <title>Création d'une commande</title>
	  <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
	</head>
	<body>
		<c:import url="inc/menu.jsp"></c:import>
	  <div>
	    <form method="post" action="creationCommande">
	      <c:import url="inc/creerClientFieldSet.jsp"></c:import>
	      <fieldset>
	        <legend>Informations commande</legend>
	        
	        <label for="dateCommande">Date <span class="requis">*</span></label>
	        <input type="text" id="dateCommande" name="dateCommande" size="20" maxlength="20" disabled
	        value="<c:out value="${ commande.date }" />" />
	        <span class="erreur"><c:out value="${ commandeForm.erreurs['dateCommande'] }"></c:out></span>
	        <br />
	        
	        <label for="montantCommande">Montant <span class="requis">*</span></label>
	        <input type="text" id="montantCommande" name="montantCommande" size="20" maxlength="20"
	        value="<c:out value="${ param.montantCommande }" />" />
	        <span class="erreur"><c:out value="${ commandeForm.erreurs['montantCommande'] }"></c:out></span>
	        <br />
	        
	        <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
	        <input type="text" id="modePaiementCommande" name="modePaiementCommande" size="20" maxlength="20"
	        value="<c:out value="${ param.modePaiementCommande }" />" />
	        <span class="erreur"><c:out value="${ commandeForm.erreurs['modePaiementCommande'] }"></c:out></span>
	        <br />
	        
	        <label for="statutPaiementCommande">Statut du paiement</label>
	        <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" size="20" maxlength="20"
	        value="<c:out value="${ param.statutPaiementCommande }" />" />
	        <span class="erreur"><c:out value="${ commandeForm.erreurs['statutPaiementCommande'] }"></c:out></span>
	        <br />
	        
	        <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
	        <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" size="20" maxlength="20"
	        value="<c:out value="${ param.modeLivraisonCommande }" />" />
	        <span class="erreur"><c:out value="${ commandeForm.erreurs['modeLivraisonCommande'] }"></c:out></span>
	        <br />
	        
	        <label for="statutLivraisonCommande">Statut de la livraison</label>
	        <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" size="20" maxlength="20"
	        value="<c:out value="${ param.statutLivraisonCommande }" />" />
	        <span class="erreur"><c:out value="${ commandeForm.erreurs['statutLivraisonCommande'] }"></c:out></span>
	        <br />
	        
	        <p class="info">${ commandeForm.resultat }</p>
	      </fieldset>
	      <input type="submit" value="Valider"  />
	      <input type="reset" value="Remettre à zéro" /> <br />
	    </form>
	  </div>
	</body>
</html>