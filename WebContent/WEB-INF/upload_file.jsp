<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Uploading file</title>
</head>
<body>
		<h3>Uploading your subtitle document:</h3>
		<form method="POST" action="upload" enctype="multipart/form-data">	 
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
</body>
</html>