<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Esito</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; display: flex; }
        .menu { width: 20%; padding: 10px; background-color: #f0f0f0; }
        .container { width: 80%; text-align: center; padding: 10px; }
        .messaggio { margin: 10px 0; }
        .successo { color: #006600; }
        .errore { color: #d00; }
    </style>
</head>
<body>
    <div class="menu">
        <%@ include file="menu.jsp"%>
    </div>
    <div class="container">
        <h1>Esito Operazione</h1>
        <p class="messaggio ${messaggio.contains('successo') ? 'successo' : 'errore'}">${messaggio}</p>
    </div>
</body>
</html>