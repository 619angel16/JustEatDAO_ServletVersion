<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reseñas</title>
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
    <c:choose>
        <c:when test="${user != null}">
            <div class="barra_nav_usuario">
                Has iniciado sesion como: ${user.name}
            </div>
            <div class="barra_nav_botones">
                <form method="GET" action="${pageContext.request.contextPath}/rest/EditUserServlet">
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

<div class="columna_details" id="review-div">
    <h2 id="restaurant-details-reviews" style="font-family: JETSansDigitalExtraBold; margin-left: 2%">Reseñas de otros
        usuarios</h2>
    <c:forEach var="review" items="${reviews}">
        <c:forEach var="userIt" items="${userList}">
            <c:if test="${userIt.id == review.idu}">
                <c:choose>
                    <c:when test="${userIt.id == user.id}">
                        <form method="GET" action="${pageContext.request.contextPath}/rest/ReviewsServlet">
                            <label for="review_${review.idu}" style="cursor: pointer" onclick="this.querySelector('input[type=submit]').click()">
                                <input type="hidden" name="reviewid" value="${review.idr}">
                                <div class="columna_details_item">
                                    <h3 style="font-size: 18px;">${userIt.name} ${userIt.surname}</h3>
                                    <span>${review.review}</span>
                                    <br>
                                    <span>${Float.parseFloat(review.grade)} ★</span>
                                </div>
                                <input type="submit" style="display: none">
                            </label>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="columna_details_item">
                            <h3 style="font-size: 18px;">${userIt.name} ${userIt.surname}</h3>
                            <span>${review.review}</span>
                            <br>
                            <span>${Float.parseFloat(review.grade)} ★</span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
    </c:forEach>
</div>
</body>
</html>
