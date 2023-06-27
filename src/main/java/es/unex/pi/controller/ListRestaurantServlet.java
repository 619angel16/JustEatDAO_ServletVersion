package es.unex.pi.controller;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.dao.RestaurantCategoriesDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.JDBCRestaurantCategoriesDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import es.unex.pi.model.User;
import es.unex.pi.util.Triplet;

import jakarta.servlet.RequestDispatcher;

import java.sql.Connection;



/**
 * Servlet implementation class ListRestaurantServlet
 */
@WebServlet("/ListRestaurantServlet.do")
public class ListRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListRestaurantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		logger.info("Atendiendo GET");
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		RestaurantDAO RestaurantDAO = new JDBCRestaurantDAOImpl();
		RestaurantDAO.setConnection(conn);
		
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);
		
		RestaurantCategoriesDAO restaurantsCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
		restaurantsCategoriesDAO.setConnection(conn);
		
		List<Restaurant> restaurantsList = RestaurantDAO.getAll();
		
		Iterator<Restaurant> itRestaurantList = restaurantsList.iterator();

		List<Triplet<Restaurant, User, List<RestaurantCategories>>> restaurantsUserList = new ArrayList<Triplet<Restaurant, User, List<RestaurantCategories>>>();

		while(itRestaurantList.hasNext()) {
			Restaurant restaurant = (Restaurant) itRestaurantList.next();
			User user = userDAO.get(restaurant.getIdu());
			List<RestaurantCategories> restaurantsCategories = restaurantsCategoriesDAO.getAllByRestaurant(restaurant.getId());
			
			logger.info("User " + user.getName());

			restaurantsUserList.add(new Triplet<Restaurant, User, List<RestaurantCategories>>(restaurant,user,restaurantsCategories));
		}
		
		
		List<User> listUser = new ArrayList<User>();
		listUser = userDAO.getAll();
		Iterator<User> itUser = listUser.iterator();
		Map<User,List<Restaurant>> userRestaurantsMap = new HashMap<User,List<Restaurant>>();
		
		while(itUser.hasNext()) {
			User user = itUser.next();
			restaurantsList = RestaurantDAO.getAllByUser(user.getId());
			userRestaurantsMap.put(user, restaurantsList);
		}
		
		request.setAttribute("restaurantsList",restaurantsUserList);
		request.setAttribute("usersMap", userRestaurantsMap);
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/ListRestaurantUser.jsp");
		view.forward(request,response);
		
	
	}

	
}
