<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <meta charset="UTF-8">
    <title>Menú</title>
</head>
<body
        style="background-color: white; background-image: url('${pageContext.request.contextPath}/img/background_info_rest.webp'); background-repeat: no-repeat; background-size: 100%;">

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
<div class="div_box_details_list">
    <ul class="menu_list">
        <li class="menu_list_item"><a href="#ensaladas" class="link_modification_details">Ensaladas</a></li>
        <li class="menu_list_item"><a href="#raciones" class="link_modification_details">Raciones</a></li>
        <li class="menu_list_item"><a href="#sandwich" class="link_modification_details">Sandwich</a></li>
        <li class="menu_list_item"><a href="#ensaladas" class="link_modification_details">Bocadillos</a></li>
        <li class="menu_list_item"><a href="#raciones" class="link_modification_details">Hamburguesas</a></li>
        <li class="menu_list_item"><a href="#sandwich" class="link_modification_details">Postres Caseros</a></li>
    </ul>
</div>
<div id="div_box_main_details">
    <div class="div_box_details">
        <div>
            <img src="${pageContext.request.contextPath}/img/logo_alpunto.png" alt="a" class="profile_rest">
        </div>
        <h2 style="font-size: 32px; line-height: 1.25" id="restaurant_name">${restaurant.name}</h2>
        <p style="margin-top: 5px" id="restaurant_categories">
            <c:forEach var="category" items="${catNameList}">
                ${category.name}
            </c:forEach>
        </p>
        <p id="restaurant_grades"><b>${restaurant.gradesAverage}★</b></p>
        <a href="ReviewsServlet?restaurantid=${restaurant.id}"
           style="color: #354447; text-align: center; line-height: 1.43;"
           id="restaurant_reviews"><b>
            <c:choose>
                <c:when test="${reviews.size() < 1}">
                    Sin opiniones
                </c:when>
                <c:when test="${reviews.size() > 1}">
                    ${reviews.size()} opiniones
                </c:when>
                <c:otherwise>
                    1 opinión
                </c:otherwise>
            </c:choose>
        </b></a>
        <p id="restaurant_address">${restaurant.address}</p>
        <p id="restaurant_telephone">Tlf.: ${restaurant.telephone}</p>
        <br>
        <div class="div_box_details_inside">
            <table style="width: 100%; height: 100%; margin: 1% 1% 1% 1%">
                <tr>
                    <th style="text-align: left">
          <span style="color: #242e30">
            Reparto
          </span>
                    </th>
                    <th style="text-align: right; padding-left: 40px">
                        <a href="" style="color: #242e30;"><b>Quiero recogida</b></a>
                    </th>
                </tr>
                <tr>
                    <td style="text-align: left ">
          <span style="color: #242e30"><b>1,74-3,14 €</b><br>
            gastos de envío</span>
                    </td>
                    <td style="text-align: right;">
                        <span>Sin pedido mínimo</span>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="columna_details" id="dish-div">
        <h2 id="restaurant-details-dishes" style="font-family: JETSansDigitalExtraBold">Platos</h2>
        <c:forEach var="dish" items="${dishList}">
            <c:if test="${user != null}">
                <form method="POST" action="${pageContext.request.contextPath}/rest/NewOrderServlet">
                    <label for="dish_${dish.id}" id="${dish.id}"
                           onclick="this.querySelector('input[type=submit]').click()">
                        <input type="hidden" name="adddishid" value="${dish.id}">
                        <input type="hidden" name="restaurantid" value="${restaurant.id}">
                        <div class="columna_details_item" id="1" style="cursor: pointer;">
                            <h3 style="font-size: 18px;">${dish.name}</h3>
                            <span>${dish.description}</span>
                            <br>
                            <span>${Float.parseFloat(dish.price)} €</span>
                        </div>
                        <input type="submit" style="display: none">
                    </label>
                </form>
            </c:if>
            <c:if test="${user == null}">
                <form method="POST" action="${pageContext.request.contextPath}/login.jsp">
                    <label for="dish_${dish.id}" id="${dish.id}"
                           onclick="this.querySelector('input[type=submit]').click()">
                        <div class="columna_details_item" style="cursor: pointer;">
                            <h3 style="font-size: 18px;">${dish.name}</h3>
                            <span>${dish.description}</span>
                            <br>
                            <span>${Float.parseFloat(dish.price)} €</span>
                        </div>
                        <input type="submit" style="display: none">
                    </label>
                </form>
            </c:if>
        </c:forEach>
    </div>
</div>
<div class="div_box_details_pay" id="div_box_details_pay_id">
    <h2 style="font-size: 32px; line-height: 1.25; text-align: left; margin-bottom: 0">Tu pedido</h2>
    <div id="printOrder_div">
        <div id="printOrderData" align="left">
            <ol>
                <c:forEach var="orderDish" items="${dishes}">
                    <li>${orderDish.name} ${orderDish.price} €</li>
                </c:forEach>
                <ul>
                    <c:if test="${totalPrice > 0}">
                        <b>Total: ${Float.parseFloat(totalPrice)} €</b>
                    </c:if>
                </ul>
            </ol>
        </div>
    </div>
    <hr>
    <a href="" class="link_modification_details"
       style="text-decoration: underline; font-weight: normal; text-align: left">Info. alérgenos</a>
    <div>
        <c:if test="${user != null}">
            <form method="POST" action="${pageContext.request.contextPath}/rest/NewOrderServlet">
                <input type="hidden" name="restaurantid" value="${restaurant.id}">
                <button type="submit" id="button_pay">Pagar</button>
            </form>
        </c:if>
        <c:if test="${user == null}">
            <button type="submit" id="button_pay"
                    style="background-color: #b7b4b2; border: #b7b4b2; cursor: not-allowed">Pagar
            </button>
        </c:if>
        <%--darle un ojo a esto--%> <%--TODO añadir funcionalidad pedido--%>
    </div>
    <div>
        <form>
            <label for="domicilio" class="button_pay_delivery">A domicilio</label>
            <input type="radio" id="domicilio" name="radio_election" value="domicilio" style="appearance: none;">
            <label for="recoger" class="button_pay_delivery">Para recoger</label>
            <input type="radio" id="recoger" name="radio_election" value="recoger" style="appearance: none;">
        </form>
    </div>
    <c:if test="${user != null}">
        <div align="left">
            <form method="GET" action="${pageContext.request.contextPath}/rest/ReviewsServlet" id="add-review-form">
                <input type="hidden" name="reviewid" value="${restaurant.id}">
                <button type="submit" id="button_review">Añadir reseña</button>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>