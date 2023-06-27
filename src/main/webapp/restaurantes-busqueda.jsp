<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <title>Restaurantes</title>
</head>
<body>
<div class="barra_navegacion">
    <div class="barra_nav_logo">
        <form method="GET" action="${pageContext.request.contextPath}/InicioServlet">
            <input type="image" src="${pageContext.request.contextPath}/img/logo_justeat.svg" alt="Logo-justeat">
        </form>
    </div>

    <c:choose>
        <c:when test="${user != null}">
            <div class="barra_nav_usuario">
                Has iniciado sesion como: ${user.name}
            </div>
            <div class="barra_nav_botones">
                <form method="GET" action="${pageContext.request.contextPath}/register.jsp">
                    <input type="submit" value="Perfil" id="boton-nav">
                </form>
                <form method="GET" action="${pageContext.request.contextPath}/rest/LogoutServlet">
                    <input type="submit" value="Cerrar Sesión" style="color: red" id="boton-nav">
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="barra_nav_botones">
                <input type="button" value="Inicio Sesión" id="boton-nav"
                       onclick="location.href = '${pageContext.request.contextPath}/login.jsp'">
            </div>
        </c:otherwise>
    </c:choose>

</div>

<div id="flex_otras_filtros_restaurantes">
    <div id="columna_otras_filtros">
        <div id="direccion">
            Calle Mallorca, 10005 Caceres
            <br>
            <a href="" style="color: black;">cambiar direccion</a>
        </div>
        <div class="titulo_otras_filtros">
            <h3 style="width: 80%; text-align: left">Otras A-Z</h3>
            <input type="button" class="boton_reiniciar" value="Reiniciar">
        </div>
        <div class="flex_otras_filtros">
            <div class="check_otra_filtro">
                <input id="admite" type="checkbox" name="availabity" value="1"/>
            </div>
            <div class="check_otra_filtro">Admite pedidos</div>
        </div>
        <div class="flex_otras_filtros">
            <div class="check_otra_filtro">
                <input id="no-admite" type="checkbox" name="availabity" value="0" onclick="window.location.reload()"/>
            </div>
            <div class="check_otra_filtro">No admite</div>
        </div>
    </div>

    <div id="columna_restaurantes">
        <form method="get" action="${pageContext.request.contextPath}/rest/RestaurantesBusquedaServlet">
            <input type="search" name="search" id="buscador_restaurante" placeholder="Buscar restaurante o cocina">
        </form>
        <c:forEach var="restaurant" items="${restaurantList}">
        <form method="GET" action="${pageContext.request.contextPath}/rest/PreviewRestaurantServlet">
            <label for="restaurant_${restaurant.id}" class="flex_restaurante"
                   onclick="this.querySelector('input[type=submit]').click()">
                <input type="hidden" name="restaurantid" value="${restaurant.id}">
                <div class="columna_foto_restaurante" style="height: auto;">
                    <img src="${pageContext.request.contextPath}/img/background_info_rest.webp"
                         class="foto_fondo_restaurante" alt="foto_fondo_restaurante">
                    <img src="${pageContext.request.contextPath}/img/logo_alpunto.png" class="logo_restaurante"
                         alt="logo_restaurante">
                </div>
                <div class="columna_info_restaurante" style="margin-top: 0px; margin-bottom: 0px; margin-right: 0px;">
                    <div class="flex_info_restaurante">
                        <div class="nombre_info_restaurante">${restaurant.name}</div>
                    </div>
                    <div class="flex_info_restaurante">
                        <div class="info_restaurante">Puntuacion: ${restaurant.gradesAverage} ★</div>
                    </div>
                    <div class="flex_info_restaurante">
                        <div class="info_restaurante">${categoriesByRestaurant.get(restaurant.id)}</div>
                    </div>
                    <div class="flex_info_restaurante">
                        <div class="info_restaurante">${(restaurant.minPrice + restaurant.maxPrice)/2}€</div>
                    </div>
                </div>
                <input type="submit" style="display: none">
            </label>
        </form>
        </c:forEach>
</body>
</html>
