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
	    <form method="post" action="creationCommande" enctype="multipart/form-data">
	    	<fieldset>
	    		<legend>Informations client</legend>
	    		<label for="isNewClient">Nouveau client ? <span class="requis">*</span></label>
	    		<input type="radio" id="newClient" name="isNewClient" value="isNewClient" checked
	    		onclick="check(this.value)" />
	    		<label style="float:none">Oui</label>
	    		<input type="radio" id="notNewClient" name="isNewClient" value="notNewClient"
	    		onclick="check(this.value)" />
	    		<label style="float:none">Non</label>
	      	<br/>
	      	<br/>
	      	<!-- Display the fields to fill or the dropdown list of clients created -->
	      	<div id="clientFields">
	      		<c:import url="inc/creerClientFieldSet.jsp"></c:import>
	      	</div>
	      	<!-- not display this list as default -->
	      	<select id="dropdownClientList" name="dropdownClientList" style="display:none">
					  <option value="undefined">Choisir un client...</option>
					  <c:if test="${ !empty clientMap }">
					  	<c:forEach var="client" items="${ clientMap }">
					  		<option value="${ client.key }">${ client.value.nom } ${ client.value.prenom }</option>
					  	</c:forEach>
					  </c:if>					  
					</select>
	      </fieldset>
	      
	      <script>
		      function check(value) {
		    	  if ( value == "isNewClient" ) {
		    		  document.getElementById("clientFields").style.display = "";
		    		  document.getElementById("dropdownClientList").style.display = "none";
		    	  }
		    	  else {
		    		  document.getElementById("clientFields").style.display = "none";
		    		  document.getElementById("dropdownClientList").style.display = "";
		    	  }
		    	}
			  </script>
	      
	      <fieldset>
	        <legend>Informations commande</legend>
	        <c:import url="inc/creerCommandeFieldSet.jsp"></c:import>
	        
	        
	        <p class="info">${ form.resultat }</p>
	      </fieldset>
	      <input type="submit" value="Valider"  />
	      <input type="reset" value="Remettre à zéro" /> <br />
	    </form>
	  </div>
	</body>
</html>