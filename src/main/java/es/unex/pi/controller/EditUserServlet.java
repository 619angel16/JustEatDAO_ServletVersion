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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class EditUserServlet
 */
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo GET del EditUserServlet");

		System.out.println(request.getParameter("name_register"));



		RequestDispatcher view = request.getRequestDispatcher("/register.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo POST del EditUserServlet");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		logger.info("Datos del usuario: " + user.getName() +" "+ user.getSurname() +" "+ user.getEmail() +" "+ user.getPassword());
		
		user.setName(request.getParameter("name_register"));
		user.setSurname(request.getParameter("surname_register"));
		user.setEmail(request.getParameter("email_register"));
		user.setPassword(request.getParameter("pass_register"));

		logger.info("NUEVOS datos del usuario: " + user.getName() +" "+ user.getSurname() +" "+ user.getEmail() +" "+ user.getPassword());
		
		userDAO.update(user);
		doGet(request,response);
	}

}
