<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:choose>
        <c:when test="${restaurant != null}">
            <title>Editar Restaurante - Just Eat</title>
        </c:when>
        <c:otherwise>
            <title>Añadir Restaurante - Just Eat</title>
        </c:otherwise>
    </c:choose>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
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
        <form method="GET" action="${pageContext.request.contextPath}/register.jsp">
            <input type="submit" value="Perfil" id="boton-nav">
        </form>
        <form method="GET" action="${pageContext.request.contextPath}/rest/LogoutServlet">
            <input type="submit" value="Cerrar Sesión" style="color: red" id="boton-nav">
        </form>
    </div>
</div>
<div class="container">
    <c:choose>
        <c:when test="${restaurant != null}">
            <h2 id="option_title">- Editar Restaurante -</h2>
        </c:when>
        <c:otherwise>
            <h2 id="option_title">- Añadir Restaurante -</h2>
        </c:otherwise>
    </c:choose>
    <form method="POST" id="form_update_restaurant" action="${pageContext.request.contextPath}/rest/EditRestaurantList">
        <div class="form-group">
            <label class="form-label" for="nombre">Nombre</label>
            <input type="text" class="form-control" name="name" id="nombre" placeholder="Introduzca el nombre del restaurante"
                   value="${restaurant.name}">
        </div>
        <div class="form-group">
            <label class="form-label" for="localidad">Localidad</label>
            <input type="text" class="form-control" name="city" id="localidad" placeholder="Introduzca la localidad del restaurante"
                   value="${restaurant.city}">
        </div>
        <div class="form-group">
            <label class="form-label" for="direccion_restaurante">Dirección</label>
            <textarea class="form-control" name="address" id="direccion_restaurante"
                      placeholder="Introduzca la dirección del restaurante"
            >${restaurant.address}</textarea>
        </div>
        <div class="form-group">
            <label class="form-label" for="telefono">Teléfono</label>
            <input type="tel" class="form-control" name="telephone" id="telefono"
                   placeholder="Introduzca el teléfono del restaurante"
                   value="${restaurant.telephone}">
        </div>
        <div class="form-group">
            <label class="form-label" for="email">Correo de contacto</label>
            <input type="email" class="form-control" name="email" id="email"
                   placeholder="Introduzca el correo de contacto del restaurante"
                   value="${restaurant.contactEmail}">
        </div>
        <div class="form-group">
            <label class="form-label" for="precioMin">Rango inferior de precio</label>
            <input type="number" class="form-control" name="minPrice" id="precioMin"
                   placeholder="Introduzca el rango inferior de precios del restaurante"
                   value="${restaurant.minPrice}">
        </div>
        <div class="form-group">
            <label class="form-label" for="precioMax">Rango superior de precio</label>
            <input type="number" class="form-control" name="maxPrice" id="precioMax"
                   placeholder="Introduzca el rango superior de precios del restaurante"
                   value="${restaurant.maxPrice}">
        </div>
        <div class="form-group">
            <label class="form-label" id="category_list">Categorías</label>
            <c:forEach var="category" items="${categories}">
                <div>
                    <label class="checkbox-label">
                        <input type="checkbox" class="checkbox-input" name="categorias"
                               value="${category.name}"
                               id="${category.name}"
                        <c:forEach var="restaurantCategory" items="${restaurantCategories}">
                        <c:if test="${restaurantCategory.idct == category.id}">
                               checked
                        </c:if>
                        </c:forEach>
                        >${category.name}
                    </label>
                </div>
            </c:forEach>
        </div>
        <div class="form-group">
            <label class="form-label">Bike Friendly</label>
            <div>
                <label class="radio-label">
                    <input type="radio" class="radio-input" name="bike-friendly" value="si"
                    <c:if test="${restaurant.bikeFriendly == 1}"> checked </c:if>
                    > Sí
                </label>
            </div>
            <div>
                <label class="radio-label">
                    <input type="radio" class="radio-input" name="bike-friendly" value="no"
                    <c:if test="${restaurant.bikeFriendly == 0}"> checked </c:if>
                    > No
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="form-label">Estado</label>
            <div>
                <label class="radio-label">
                    <input type="radio" class="radio-input" name="status" value="si"
                    <c:if test="${restaurant.available == 1}"> checked </c:if>
                    > Acepta pedidos
                </label>
            </div>
            <div>
                <label class="radio-label">
                    <input type="radio" class="radio-input" name="status" value="no"
                    <c:if test="${restaurant.available == 0}"> checked </c:if>
                    > No acepta pedidos
                </label>
            </div>
        </div>
        <c:choose>
            <c:when test="${restaurant != null}">
                <input type="hidden" name="restaurantid" value="${restaurant.id}">
                <input type="submit" id="boton-post-edit_restaurant" class="btn button-save"
                       value="Actualizar restaurante"/>
            </c:when>
            <c:otherwise>
                <input type="submit" id="boton-post-edit_restaurant" class="btn button-save"
                       value="Añadir restaurante"/>
            </c:otherwise>
        </c:choose>
    </form>
</div>
</body>
</html>
