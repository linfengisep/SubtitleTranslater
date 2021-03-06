<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Editer les sous-titres</title>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
</style>

</head>
<body>
	<h3>Uploading file to translate below:</h3>
	<c:if test="${! empty desc}">
		<p><c:out value="${desc} a été téléchargé."/></p>
	</c:if>
	<form method="POST" action="edit" enctype="multipart/form-data">	 
		 	 <p>
				<label for="desc">Description:</label>
			 	<input type="text" id="desc" name ="desc"/>
			 </p>
			 <p>
				<label for=myfile>File:</label>
			 	<input type="file" id="myfile" name ="myfile"/>
			 </p>
			 <input type="submit" value="envoyer" name ="uploading"/>
	</form>
	
	<h3>Export the translation in below section:</h3>	
	<form action="edit" method="POST">
		<input type="submit" name="export" value="export"/>
	</form>
	
	<h3>Translation section below:</h3>
    <form method="POST" action="edit"> 
      <input type="submit" value="envoyer"/>   
	    <table style="position:relative;top: 50px;">
	            <tr>
	        		<td style="text-align:right;">Version Originale</td>
	        		<td>Traduction</td>
	        	</tr>
	        <c:forEach items="${ subtitles }" var="line" varStatus="status">
	        	<tr>
	        		<td style="text-align:right;"><c:out value="${ line.subtitleVO }" /></td>
	        		<td><input type="text" name="${ line.id }" id="${ line.id }" value="${line.subtitleVT }" size="35" /></td>
	        	</tr>
	    	</c:forEach>
	    </table>
    </form>
    
</body>
</html>