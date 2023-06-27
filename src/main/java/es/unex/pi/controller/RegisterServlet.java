package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo GET del RegisterServlet");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo POST del RegisterServlet");
		
		//Establecimiento de conexion
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		

		
		String nombre = request.getParameter("name_register");
		String surname = request.getParameter("surname_register");
		String email = request.getParameter("email_register");
		String password = request.getParameter("pass_register");
			
		//Crea el perfil de un nuevo usuario
		if(request.getParameter("register_button") != null) {			
			User user = new User(nombre, surname, email, password);
			//contraseña robusta
			if(password.length() >= 8) {
				//no email existente
				if(userDAO.get(user.getEmail()) == null) {
					logger.info("Creando el perfil de "+nombre+" "+surname);
					userDAO.add(user);
					RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
					view.forward(request,response);
				}else {
					request.setAttribute("email_exi",  "Ya existe un usuario con este correo");
					RequestDispatcher view = request.getRequestDispatcher("/register.jsp");
					view.forward(request,response);
				}
			}else {
				request.setAttribute("mal_pass", "¡La contraseña no es robusta, al menos contendrá 8 dígitos entre los que se incluye un número, una mayúscula, una\r\n"
						+ "minúscula y un carácter alfanumérico");
				RequestDispatcher view = request.getRequestDispatcher("/register.jsp");
				view.forward(request,response);
			}
		}
	}

}
