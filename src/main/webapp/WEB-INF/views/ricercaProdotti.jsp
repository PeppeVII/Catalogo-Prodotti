<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ricerca Prodotti</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; display: flex; }
        .menu { width: 20%; padding: 10px; background-color: #f0f0f0; }
        .container { width: 80%; text-align: center; padding: 10px; }
        form { display: inline-block; text-align: left; }
        label { display: block; margin: 10px 0 5px; }
        input[type="text"] { width: 100%; padding: 5px; }
        button { background-color: #666; color: white; padding: 10px; border: none; cursor: pointer; margin-top: 10px; }
        button:hover { background-color: #888; }
    </style>
</head>
<body>
    <div class="menu">
        <%@ include file="menu.jsp"%>
    </div>
    <div class="container">
        <h1>Ricerca Prodotti</h1>
        <form:form action="/ricercaProdotti" method="post" modelAttribute="prodottoRicerca">
            <label>Nome prodotto:</label>
            <form:input path="nome" />
            <form:button>Ricerca</form:button>
        </form:form>
    </div>
</body>
</html>