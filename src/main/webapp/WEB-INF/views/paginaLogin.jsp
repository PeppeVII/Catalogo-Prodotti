<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .container { width: 50%; margin: 0 auto; text-align: center; }
        form { display: inline-block; text-align: left; }
        label { display: block; margin: 10px 0 5px; }
        input[type="text"], input[type="password"] { width: 100%; padding: 5px; }
        button { background-color: #666; color: white; padding: 10px; border: none; cursor: pointer; margin-top: 10px; }
        button:hover { background-color: #888; }
        .messaggio { margin: 10px 0; color: #d00; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Login</h1>
        <form:form action="/login" method="post" modelAttribute="utente">
            <label>Username:</label>
            <form:input path="username" />
            <label>Password:</label>
            <form:password path="password" />
            <form:button>Login</form:button>
        </form:form>
        <c:if test="${not empty messaggio}">
            <p class="messaggio">${messaggio}</p>
        </c:if>
        <p><a href="/registrazione">Registrati</a></p>
    </div>
</body>
</html>