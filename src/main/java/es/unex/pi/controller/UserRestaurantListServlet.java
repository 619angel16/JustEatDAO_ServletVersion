package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;



/**
 * Servlet implementation class UserRestaurantListServlet
 */
public class UserRestaurantListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRestaurantListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo GET del UserRestaurantListServlet");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        UserDAO userDAO = new JDBCUserDAOImpl();
        userDAO.setConnection(conn);

        RestaurantDAO RestaurantDAO = new JDBCRestaurantDAOImpl();
        RestaurantDAO.setConnection(conn);
        
        CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
        categoryDAO.setConnection(conn);
        
        RestaurantCategoriesDAO restaurantsCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
        restaurantsCategoriesDAO.setConnection(conn);
        
        HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

        List<Restaurant> restaurantList = RestaurantDAO.getAllByUser(user.getId());
        for (Restaurant res: restaurantList) {
            System.out.println(res.getName());
        }
        request.setAttribute("restaurantList", restaurantList);
        RequestDispatcher view = request.getRequestDispatcher("/user-restaurants.jsp");
        view.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo POST del UserRestaurantListServlet");
		doGet(request, response);
	}

}
