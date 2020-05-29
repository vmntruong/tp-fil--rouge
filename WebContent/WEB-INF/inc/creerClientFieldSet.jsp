<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<label for="nomClient">Nom <span class="requis">*</span></label>
<input type="text" id="nomClient" name="nomClient" size="20" maxlength="30" 
value="${ param.nomClient }" />
<span class="erreur">${ form.erreurs['nomClient'] }</span>
<br />

<label for="prenomClient">Prénom </label>
<input type="text" id="prenomClient" name="prenomClient" size="20" maxlength="20" 
value="${ param.prenomClient }" />
<span class="erreur">${ form.erreurs['prenomClient'] }</span>
<br />
    
<label for="adresseClient">Adresse de livraison <span class="requis">*</span></label>
<input type="text" id="adresseClient" name="adresseClient" size="20" maxlength="60" 
value="${ param.adresseClient }" />
<span class="erreur">${ form.erreurs['adresseClient'] }</span>
<br />

<label for="telephoneClient">Numéro de téléphone <span class="requis">*</span></label>
<input type="text" id="telephoneClient" name="telephoneClient" size="20" maxlength="20" 
value="${ param.telephoneClient }" />
<span class="erreur">${ form.erreurs['telephoneClient'] }</span>
<br />

<label for="emailClient">Adresse email</label>
<input type="email" id="emailClient" name="emailClient" size="20" maxlength="60" 
value="${ param.emailClient }" />
<span class="erreur">${ form.erreurs['emailClient'] }</span>
<br />

<label for="imageClient">Image</label>
<input type="file" id="imageClient" name="imageClient" />
<span class="erreur">${form.erreurs['imageClient']}</span>
<br />