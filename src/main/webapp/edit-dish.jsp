<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <c:choose>
        <c:when test="${dish != null}">
            <title>Editar Plato - Just Eat</title>
        </c:when>
        <c:otherwise>
            <title>Añadir Plato - Just Eat</title>
        </c:otherwise>
    </c:choose>
    <title>Añadir Plato - Just Eat</title>
</head>
<body>
<div class="barra_navegacion">
    <div class="barra_nav_logo">
        <form method="GET" action="${pageContext.request.contextPath}/InicioServlet">
            <input type="image" src="${pageContext.request.contextPath}/img/logo_justeat.svg" alt="Logo-justeat">
        </form>
    </div>
    <div class="barra_nav_usuario">
        Has iniciado sesion como: ${user.name}
    </div>
    <div class="barra_nav_botones">

        <form method="GET" action="${pageContext.request.contextPath}/rest/UserRestaurantListServlet">
            <input type="submit" value="Restaurantes" id="boton-nav">
        </form>
        <form method="GET" action="${pageContext.request.contextPath}/rest/EditUserServlet">
            <input type="submit" value="Perfil" id="boton-nav">
        </form>
        <form method="GET" action="${pageContext.request.contextPath}/rest/LogoutServlet">
            <input type="submit" value="Cerrar Sesión" style="color: red" id="boton-nav">
        </form>
    </div>
</div>
<div class="container">
    <c:choose>
        <c:when test="${dish != null}">
            <h2 id="dish_option">- Editar plato -</h2>
        </c:when>
        <c:otherwise>
            <h2 id="dish_option">- Añadir plato -</h2>
        </c:otherwise>
    </c:choose>
    <form method="POST" id="form_update_dish" action="${pageContext.request.contextPath}/rest/DishEditServlet">
        <div class="form-group">
            <label class="form-label" for="nombre_plato">Nombre plato</label>
            <input type="text" class="form-control" id="nombre_plato" name="nombre_plato"
                   placeholder="Introduzca el nombre del plato" value="${dish.name}">
        </div>
        <div class="form-group">
            <label class="form-label" for="descripcion_plato">Descripción plato</label>
            <textarea class="form-control" id="descripcion_plato" name="descripcion_plato"
                      placeholder="Introduzca la descripcion del plato">${dish.description}</textarea>
        </div>
        <div class="form-group">
            <label class="form-label" for="precio_plato">Precio plato</label>
            <input type="number" class="form-control" id="precio_plato" name="precio_plato"
                   placeholder="Introduzca el precio del plato" value="${dish.price}">
        </div>
        <c:choose>
            <c:when test="${dish != null}">
                <input type="hidden" name="dishid" value="${dish.id}">
                <input type="hidden" name="restaurantid" value="${restaurant.id}">
                <input type="submit" id="boton-post-edit_dish" class="btn button-save" value="Actualizar plato"/>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="restaurantid" value="${restaurant.id}">
                <input type="submit" id="boton-post-edit_dish" class="btn button-save" value="Añadir plato"/>
            </c:otherwise>
        </c:choose>
    </form>
</div>

</body>
</html>
