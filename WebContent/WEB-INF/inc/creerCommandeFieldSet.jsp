<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<label for="dateCommande">Date <span class="requis">*</span></label>
<input type="text" id="dateCommande" name="dateCommande" size="20" maxlength="20" disabled
value="<c:out value="${ commande.date }" />" />
<span class="erreur"><c:out value="${ form.erreurs['dateCommande'] }"></c:out></span>
<br />

<label for="montantCommande">Montant <span class="requis">*</span></label>
<input type="text" id="montantCommande" name="montantCommande" size="20" maxlength="20"
value="<c:out value="${ param.montantCommande }" />" />
<span class="erreur"><c:out value="${ form.erreurs['montantCommande'] }"></c:out></span>
<br />

<label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
<input type="text" id="modePaiementCommande" name="modePaiementCommande" size="20" maxlength="20"
value="<c:out value="${ param.modePaiementCommande }" />" />
<span class="erreur"><c:out value="${ form.erreurs['modePaiementCommande'] }"></c:out></span>
<br />

<label for="statutPaiementCommande">Statut du paiement</label>
<input type="text" id="statutPaiementCommande" name="statutPaiementCommande" size="20" maxlength="20"
value="<c:out value="${ param.statutPaiementCommande }" />" />
<span class="erreur"><c:out value="${ form.erreurs['statutPaiementCommande'] }"></c:out></span>
<br />

<label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
<input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" size="20" maxlength="20"
value="<c:out value="${ param.modeLivraisonCommande }" />" />
<span class="erreur"><c:out value="${ form.erreurs['modeLivraisonCommande'] }"></c:out></span>
<br />

<label for="statutLivraisonCommande">Statut de la livraison</label>
<input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" size="20" maxlength="20"
value="<c:out value="${ param.statutLivraisonCommande }" />" />
<span class="erreur"><c:out value="${ form.erreurs['statutLivraisonCommande'] }"></c:out></span>
<br />