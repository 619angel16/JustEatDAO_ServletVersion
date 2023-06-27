<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicia sesión en Just Eat</title>
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
	<div class="barra_nav_botones">
		<input type="button" value="Regístrate" id="boton-nav" onclick="location.href = 'register.jsp'">
	</div>
</div>
<div id="div_box_login">
    <h2>Inicia sesión</h2>

    <form method="POST" action="LoginServlet">
        <div>
            <input type="email" placeholder="Introduce tu email" required class="input_login" name="email_login">
        </div>
        <div>
        	<p>${messages}</p>
            <input type="password"
                   placeholder="Introduce contraseña" required class="input_login" name="pass_login">
        </div>
        <div>
            <a href="https://www.just-eat.es/account/reset-password/?returnUrl=">¿Has olvidado tu contraseña?</a>
        </div>
        <div>
            <input type="checkbox" name="save_session" id="save_session" checked>
            <label for="save_session" id="label_modify" style="font-family: JetSansDigital; font-size: 14px">Guardar
                sesión<br>&ensp;No lo marques si compartes ordenador</label>
        </div>
        <div>
            <input type="submit"  id="boton_inicio" value="Inicia Sesión" name="login_button">
        </div>
        <p style="display: inline-block;">¿Nuevo en Just Eat? <a href="register.jsp">Crear
            cuenta</a>
        </p>
        <p>Al crear la cuenta, aceptas nuestros<br><a href="https://www.just-eat.es/info/terminos-y-condiciones">términos
            y condiciones</a>.
            Por favor, lee<br>nuestra <a href="https://www.just-eat.es/info/politica-de-privacidad">política
                de
                privacidad</a> y nuestra<br><a href="https://www.just-eat.es/info/politica-de-cookies">politica de
                cookies</a>.</p>
    </form>
</div>

</body>
</html>
