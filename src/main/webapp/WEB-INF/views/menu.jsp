<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <h3>Menu</h3>
    <p><a href="/ricercaProdotti">Ricerca Prodotti</a></p>
    <c:if test="${utenteLoggato != null && utenteLoggato.ruolo.nome == 'admin'}">
        <p><a href="/creaProdotto">Crea Prodotto</a></p>
    </c:if>
    <p><a href="/logout">Logout</a></p>
</div>