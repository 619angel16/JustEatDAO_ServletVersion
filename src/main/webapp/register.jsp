<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesM.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <c:choose>
        <c:when test="${user != null}">
            <title>Editar cuenta | Just Eat</title>
        </c:when>
        <c:otherwise>
            <title>Crear cuenta | Just Eat</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<div class="barra_navegacion">
    <div class="barra_nav_logo">
        <form method="GET" action="${pageContext.request.contextPath}/InicioServlet">
            <input type="image" src="${pageContext.request.contextPath}/img/logo_justeat.svg" alt="Logo-justeat">
        </form>
    </div>
</div>
<div id="div_box_registro">
    <c:choose>
        <c:when test="${user != null}">
            <div style="text-align: center" id="div_register_title">
                <h1 id="register_title">Editar | Eliminar: ${user.name} ${user.surname}</h1>
            </div>
        </c:when>
        <c:otherwise>
            <div style="text-align: center" id="div_register_title">
                <h1 id="register_title">Registro </h1>
            </div>
        </c:otherwise>
    </c:choose>
    <form method="POST" id="form-register"
          <c:if test="${user != null}">action="${pageContext.request.contextPath}/rest/EditUserServlet"</c:if>
            <c:if test="${user == null}">action="${pageContext.request.contextPath}/RegisterServlet"</c:if>>
        <div>
            <label for="name_register" class="nombres_campos_registro">Nombre</label><br>
            <input type="text" class="rellenar_registro" id="name_register" name="name_register" required
                   <c:if test="${user != null}">value="${user.name}"</c:if>>
        </div>
        <div>
            <label for="surname_register" class="nombres_campos_registro">Apellido</label><br>
            <input type="text" class="rellenar_registro" id="surname_register" name="surname_register" required
                   <c:if test="${user != null}">value="${user.surname}"</c:if>>
        </div>
        <div id="div_email_register">
            <label for="email_register" class="nombres_campos_registro">Correo electrónico</label><br>
            <input type="email" class="rellenar_registro" id="email_register" name="email_register" required
                   <c:if test="${user != null}">value="${user.email}"</c:if>>
        </div>
        <div>
            <label for="pass_register" class="nombres_campos_registro">Contraseña</label><br>
            <input type="password" class="rellenar_registro" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                   id="pass_register" name="pass_register"
                   title="La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número"
                   required
                   <c:if test="${user != null}">value="${user.password}"</c:if>>
        </div>
        <c:if test="${user != null}">
            <div>
                <input type="hidden" name="userid" value="${user.id}">
                <input type="submit" class="rellenar_registro" id="boton_registro"
                       name="edit_button" value="Editar cuenta">
            </div>
        </c:if>
        <c:if test="${user == null}">
            <div>
                <input type="hidden" name="userid" value="${user.id}">
                <input type="submit" class="rellenar_registro" id="boton_registro"
                       name="register_button" value="Crear cuenta">
            </div>
        </c:if>
        <c:if test="${user != null}"></form>
    <form method="POST" id="form-register" action="${pageContext.request.contextPath}/rest/DeleteUserServlet">
        <div>
            <input type="hidden" name="deleteid" value="true">
            <input type="submit" value="Eliminar cuenta" style="margin-left: 15px; cursor: pointer;"
                   class="rellenar_registro">
        </div>
    </form>
    </c:if>
    <c:choose>
        <c:when test="${user == null}">
            <div id="terms_conditions_div">
                <p style="text-align: center">Al crear la cuenta, aceptas nuestros<br><a
                        href="https://www.just-eat.es/info/terminos-y-condiciones">términos y condiciones</a>. Por
                    favor,
                    lee<br>nuestra
                    <a href="https://www.just-eat.es/info/politica-de-privacidad">política
                        de
                        privacidad</a> y nuestra<br><a href="https://www.just-eat.es/info/politica-de-cookies">política
                        de
                        cookies</a>.
                </p>
            </div>
        </c:when>
    </c:choose>
</div>
</body>
</html>
