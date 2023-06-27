<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Platos - Just Eat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
</head>
<body>
<div class="barra_navegacion">
    <form method="GET" action="${pageContext.request.contextPath}/InicioServlet">
        <input type="image" src="${pageContext.request.contextPath}/img/logo_justeat.svg" alt="Logo-justeat">
    </form>
    Has iniciado sesion como: ${user.name}
    <form method="GET" action="${pageContext.request.contextPath}/register.jsp">
        <input type="submit" value="Perfil" id="boton-nav">
    </form>
    <form method="GET" action="${pageContext.request.contextPath}/rest/LogoutServlet">
        <input type="submit" value="Cerrar Sesión" style="color: red" id="boton-nav">
    </form>
</div>

<h2 style="text-align: center" id="dish_list">-Lista Platos-</h2>
<div>
    <c:forEach var="dish" items="${dishes}">
        <p>${dish.name}</p>
        <form method="GET" action="${pageContext.request.contextPath}/rest/DishEditServlet">
            <input type="hidden" name="dishid" value="${dish.id}">
            <input type="hidden" name="restaurantid" value="${restaurant.id}">
            <input type="submit" value="Editar plato" class="btn button-save" style="width: 10%; margin: 1px;">
        </form>
        <form method="POST" action="${pageContext.request.contextPath}/rest/DeleteDishServlet">
            <input type="hidden" name="dishid" value="${dish.id}">
            <input type="submit" value="Eliminar plato" class="btn button-save" style="width: 10%; margin: 1px;">
        </form>
    </c:forEach>
    <br>
</div>
<form method="GET" id="add-dish-form" action="${pageContext.request.contextPath}/rest/DishEditServlet">
    <input type="hidden" name="restaurantid" value="${restaurant.id}">
    <input type="submit" class="btn button-save" value="Añadir Plato">
</form>
</body>
</html>
