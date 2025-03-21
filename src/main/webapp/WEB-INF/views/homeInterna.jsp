<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Interna</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; display: flex; }
        .menu { width: 20%; padding: 10px; background-color: #f0f0f0; }
        .container { width: 80%; text-align: center; padding: 10px; }
    </style>
</head>
<body>
    <div class="menu">
        <%@ include file="menu.jsp"%>
    </div>
    <div class="container">
        <h1>Benvenuto nell'area privata!</h1>
    </div>
</body>
</html>