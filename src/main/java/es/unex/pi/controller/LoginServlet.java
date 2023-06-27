package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo GET del LoginServlet");
		HttpSession session = request.getSession();

		RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo POST del LoginServlet");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		String email = request.getParameter("email_login");
		String password = request.getParameter("pass_login");
		logger.info("Printing email and pass: " + email + " " + password);

		User user = userDao.get(email);

		if ((user != null) && (user.getPassword().equals(password))) {
			logger.info("El login ha sido correcto.");
			HttpSession session = request.getSession();
			session.setAttribute("user",user);
			RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
			view.forward(request,response);

		} else {
			logger.info("El login no se ha podido realizar.");
			request.setAttribute("messages","Correo o contraseña mal introducidas.");
			RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
			view.forward(request,response);

		}
	}

}
