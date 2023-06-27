<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reviewStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <meta charset="UTF-8">
    <c:if test="${review != null}">
        <title>Editar reseña</title>
    </c:if>
    <c:if test="${review == null}">
        <title>Añadir reseña</title>
    </c:if>
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
<div class="form-container" style="margin-top: 40px">
    <c:if test="${review != null}">
        <h2 class="form-title" id="review-title">Editar Reseña</h2>
    </c:if>
    <c:if test="${review == null}">
        <h2 class="form-title" id="review-title">Crear Reseña</h2>
    </c:if>
    <form method="POST" id="review-form" action="${pageContext.request.contextPath}/rest/ReviewsServlet">
        <div class="form-group">
            <label for="description-textarea">Descripción:</label>
            <textarea id="description-textarea" name="description-textarea"
                      style="width: 99%; height: 100px; max-width: 99%; max-height: 100px"
                      placeholder="Ingresa la descripción de la reseña">${review.review}</textarea>
        </div>
        <div class="form-group">
            <div class="rating-container">
                <label for="ratingScore" class="rating-label">Selecciona una nota:</label>
                <input type="number" name="ratingScore" id="ratingScore" max="5" min="1" value="${review.grade}">
            </div>
        </div>
        <c:if test="${review != null}">
            <div class="form-submit">
                <input type="hidden" name="restaurantid" value="${review.idr}">
                <input type="hidden" name="userid" value="${review.idu}">
                <input type="submit" value="Editar Reseña"
                       style="padding: 10px 20px; background-color: #fb6100; border: none; border-radius: 4px; color: #fff; font-size: 16px; cursor: pointer;">
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/rest/ReviewsServlet">
                <div class="form-submit">
                    <input type="hidden" name="restaurantid" value="${review.idr}">
                    <input type="hidden" name="delete" value="true">
                    <input type="submit" value="Eliminar Reseña" class="rellenar_registro" style="cursor: pointer; width: fit-content">
                </div>
            </form>
        </c:if>
        <c:if test="${review == null}">
            <div class="form-submit">
                <input type="hidden" name="restaurantid" value="${restaurant.id}">
                <input type="submit" value="Añadir Reseña" style="padding: 10px 20px; background-color: #fb6100; border: none; border-radius: 4px; color: #fff; font-size: 16px; cursor: pointer;">
            </div>
        </c:if>
    </form>
</div>
</body>
</html>
