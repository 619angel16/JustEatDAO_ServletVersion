<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Restaurantes - Just Eat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
</head>
<body>
<div class="barra_navegacion">
    <form method="GET" action="${pageContext.request.contextPath}/InicioServlet" style="margin-top: 10px">
        <input type="image" src="${pageContext.request.contextPath}/img/logo_justeat.svg" alt="Logo-justeat">
    </form>
    <p style="margin-top: 10px">Has iniciado sesion como: ${user.name}</p>
    <form method="GET" action="${pageContext.request.contextPath}/register.jsp" style="margin-top: 10px">
        <input type="submit" value="Perfil" id="boton-nav">
    </form>
    <form method="GET" action="${pageContext.request.contextPath}/rest/LogoutServlet" style="margin-top: 10px">
        <input type="submit" value="Cerrar Sesión" style="color: red" id="boton-nav">
    </form>
</div>

<h2 style="text-align: center" id="restaurant_list">-Lista Restaurantes-</h2>
<div>
    <c:forEach var="restaurant" items="${restaurantList}">
        <p>${restaurant.name}</p>
        <form method="GET" action="${pageContext.request.contextPath}/rest/PreviewRestaurantServlet">
            <input type="hidden" name="restaurantid" value="${restaurant.id}">
            <input type="submit" value="Ver" class="btn button-save" style="width: 10%; margin: 1px;">
        </form>
        <form method="GET" action="${pageContext.request.contextPath}/rest/EditRestaurantList">
            <input type="hidden" name="restaurantid" value="${restaurant.id}">
            <input type="submit" value="Editar" class="btn button-save" style="width: 10%; margin: 1px;">
        </form>
        <form method="GET" action="${pageContext.request.contextPath}/rest/DishListServlet">
            <input type="hidden" name="restaurantid" value="${restaurant.id}">
            <input type="submit" value="Menú de platos" class="btn button-save" style="width: 10%; margin: 1px;">
        </form>
        <form method="POST" action="${pageContext.request.contextPath}/rest/DeleteRestaurantServlet">
            <input type="hidden" name="restaurantid" value="${restaurant.id}">
            <input type="submit" value="Eliminar" class="btn button-save" style="width: 10%; margin: 1px;">
        </form>
    </c:forEach>
    <br>
</div>
<form method="GET" action="${pageContext.request.contextPath}/rest/EditRestaurantList">
    <input type="submit" class="btn button-save" value="Añadir Restaurante">
</form>
</body>
</html>