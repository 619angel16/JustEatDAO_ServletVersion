<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de pedidos - Just Eat</title>
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

<div class="columna_details" id="order-div">
    <h2 id="user-order-history" style="font-family: JETSansDigitalExtraBold; margin-left: 2%">Historial de pedidos</h2>
    <c:forEach var="order" items="${userOrders}">
        <div class="columna_details_item" style="padding-top: 0px;">
            <h3 style="font-size: 18px;">Identificador: ${order.id}</h3>
            <c:forEach var="dish" items="${dishesByOrder.get(order.id)}">
                <span>${dish.name}</span>
                <br>
                <span>Precio plato: ${dish.price} €</span>
                <br></c:forEach>
            <span>Precio total: ${Float.parseFloat(order.totalPrice)} €</span>
        </div>
    </c:forEach>
</div>
</body>
</html>