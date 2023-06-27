package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet implementation class PreviewRestaurantServlet
 */
public class PreviewRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreviewRestaurantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo GET del PreviewRestaurantServlet");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

        UserDAO userDAO = new JDBCUserDAOImpl();
        userDAO.setConnection(conn);

        RestaurantDAO RestaurantDAO = new JDBCRestaurantDAOImpl();
        RestaurantDAO.setConnection(conn);

        CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
        categoryDAO.setConnection(conn);

        RestaurantCategoriesDAO restaurantsCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
        restaurantsCategoriesDAO.setConnection(conn);

        ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
        reviewsDAO.setConnection(conn);

        DishDAO dishDAO = new JDBCDishDAOImpl();
        dishDAO.setConnection(conn);

        long id = Long.parseLong(request.getParameter("restaurantid"));
        logger.info("get parameter id (" + id + ")");

        Restaurant restaurant = RestaurantDAO.get(id);

        List<RestaurantCategories> resCatList = restaurantsCategoriesDAO.getAllByRestaurant(restaurant.getId());
        List <Category> catNameList = new ArrayList<>();
        for (RestaurantCategories resCatIT : resCatList) {
             catNameList.add(categoryDAO.get(resCatIT.getIdct()));
        }

        List<Review> reviewList = reviewsDAO.getAllByRestaurant(restaurant.getId());
        List<Dish> dishList = dishDAO.getByRestaurantRID(restaurant.getId());


        System.out.println("Restaurant Data: " + restaurant.getName() + " " + restaurant.getId());
        request.setAttribute("restaurant", restaurant);
        request.setAttribute("reviews", reviewList);
        request.setAttribute("catNameList", catNameList);
        request.setAttribute("dishList", dishList);
        RequestDispatcher view = request.getRequestDispatcher("/restaurant-details.jsp");
        view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Atendiendo POST del PreviewRestaurantServlet");
		doGet(request, response);
	}

}
