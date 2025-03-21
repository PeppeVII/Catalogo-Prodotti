<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Risultati Ricerca</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; display: flex; }
        .menu { width: 20%; padding: 10px; background-color: #f0f0f0; }
        .container { width: 80%; padding: 10px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 8px; border: 1px solid #ccc; text-align: left; }
        th { background-color: #999; color: white; }
        input[type="text"], input[type="number"] { width: 100%; padding: 5px; box-sizing: border-box; }
        button { background-color: #666; color: white; padding: 5px; border: none; cursor: pointer; margin: 2px; }
        button:hover { background-color: #888; }
    </style>
</head>
<body>
    <div class="menu">
        <%@ include file="menu.jsp"%>
    </div>
    <div class="container">
        <h1>Risultati Ricerca</h1>
        <c:if test="${not empty risultati}">
            <table>
                <tr>
                    <th>Nome</th>
                    <th>Prezzo</th>
                    <c:if test="${utenteLoggato != null && utenteLoggato.ruolo.nome == 'admin'}">
                        <th>Azioni</th>
                    </c:if>
                </tr>
                <c:forEach var="prodotto" items="${risultati}">
                    <tr>
                        <td>${prodotto.nome}</td>
                        <td>${prodotto.prezzo}</td>
                        <c:if test="${utenteLoggato != null && utenteLoggato.ruolo.nome == 'admin'}">
                            <td>
                                <!-- Modifica Completa -->
                                <form action="/totalUpdate" method="post">
                                    <input type="hidden" name="id" value="${prodotto.id}">
                                    <input type="text" name="nome" value="${prodotto.nome}" required>
                                    <input type="number" name="prezzo" value="${prodotto.prezzo}" step="0.01" required>
                                    <button type="submit">Modifica Completa</button>
                                </form>
                                <!-- Modifica Parziale -->
                                <form action="/partialUpdate" method="post">
                                    <input type="hidden" name="id" value="${prodotto.id}">
                                    <input type="text" name="nome" placeholder="${prodotto.nome}" value="${prodotto.nome}">
                                    <input type="number" name="prezzo" placeholder="${prodotto.prezzo}" value="${prodotto.prezzo}" step="0.01">
                                    <button type="submit">Modifica Parziale</button>
                                </form>
                                <!-- Cancella -->
                                <form action="/delete" method="post">
                                    <input type="hidden" name="id" value="${prodotto.id}">
                                    <button type="submit" onclick="return confirm('Sei sicuro di voler cancellare questo prodotto?')">Cancella</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty risultati}">
            <p>Nessun prodotto trovato.</p>
        </c:if>
    </div>
</body>
</html>