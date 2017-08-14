<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Page1</title>
</head>
<body>

	<form name="amountRequest" action="/csrf.manager.redis.example/rest/account/v1/amount" method="post">
	    <input type="hidden" name="requestId" value="${requestId}"/>
	    Amount: <input type = "text" name = "amount" />
	    <input type = "submit" value = "Submit" />
	</form>

</body>
</html>