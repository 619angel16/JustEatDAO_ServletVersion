<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <title>Inicio</title>
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
                <form method="GET" action="${pageContext.request.contextPath}/rest/UserRestaurantListServlet">
                    <input type="submit" value="Restaurantes" id="boton-nav">
                </form>
                <form method="GET" action="${pageContext.request.contextPath}/rest/OrdersServlet">
                    <input type="submit" value="Pedidos" id="boton-nav">
                </form>
                <form method="GET" action="${pageContext.request.contextPath}/rest/EditUserServlet">
                    <input type="submit" value="Perfil" id="boton-nav">
                </form>
                <form method="GET" action="${pageContext.request.contextPath}/rest/LogoutServlet">
                    <input type="submit" value="Cerrar Sesión" style="color: red" id="boton-nav">
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <input type="button" value="Inicio Sesión" id="boton-nav"
                   onclick="location.href = '${pageContext.request.contextPath}/login.jsp'">
        </c:otherwise>
    </c:choose>
</div>

<div class="fondo">
    <img style="width: 100%" alt="Plato de comida"
         src="${pageContext.request.contextPath}/img/fondo_busquedajpg.jpg">
</div>
<div id="div_box_busqueda">
    <h1 id="titulo_inicio_busqueda">Pide lo que te pida el cuerpo</h1>
    <h2 id="subtitulo_inicio_busqueda">Comida a domicilio online cerca de ti</h2>
    <form method="GET" action="${pageContext.request.contextPath}/rest/RestaurantesBusquedaServlet">
        <div id="rellenar-boton_direccion">
            <input type="text" id="rellenar_direccion" name="search" placeholder="Introduce una direccion o restaurante"
                   required>
            <input type="submit" id="boton_direccion" value="Buscar restaurantes">
        </div>
    </form>
</div>
<div id="contenido_pagina_busqueda">
    <div>
        <h1>Tipos de cocina más populares</h1>
        <p>Buscar los restaurantes mas populares de tu zona y haz tu pedido de comida a domicilio online</p>
    </div>
    <div class="flex_container">
        <c:forEach var="category" items="${categoriesList}">
            <div class="columna_container">
                <form method="GET" action="${pageContext.request.contextPath}/rest/RestaurantesBusquedaServlet">
                    <input type="hidden" name="categoryid" value="${category.id}">
                    <input type="image" src="${pageContext.request.contextPath}/img/hamburguesa_fondo_naranja.jpg"
                           alt="img_container" class="img_container">
                </form>
                <div class="texto_container">${category.name}</div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
