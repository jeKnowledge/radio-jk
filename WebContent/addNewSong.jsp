<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="QualeoMambo" method="POST">
		Music Title: <input type="text" name="newTitle">
	<br />
		Music Link from Youtube (Ex: https://www.youtube.com/watch?v=WWR0TBNFRRQ -> WWR0TBNFRRQ): <input type="text" name="newLink" />
	<br />
		Initial Time (Minute): <input type="text" name="newInitMin" />
	<br />
		Initial Time (Seconds): <input type="text" name="newInitSec" />		
	<br />
		Final Time (Minute): <input type="text" name="newFinalMin" />
	<br />
		Final Time (Seconds): <input type="text" name="newFinalSec" />		
	<input type="submit" value="Submit" />
	</form>
</body>
</html>