package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.model.Category;

/**
 * Servlet implementation class InicioServlet
 */
public class InicioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName()); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InicioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo GET del InicioServlet");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);
		
		List<Category> categoriesList = categoryDAO.getAll();
		
		request.setAttribute("categoriesList",categoriesList);
		
		RequestDispatcher view = request.getRequestDispatcher("/inicio-busqueda.jsp");
		view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo POST del InicioServlet");
		doGet(request, response);
	}

}
