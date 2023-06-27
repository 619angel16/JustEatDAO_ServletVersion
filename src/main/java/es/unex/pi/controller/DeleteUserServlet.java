package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet implementation class DeleteUserServlet
 */
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del DeleteUserServlet");

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del DeleteUserServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        UserDAO userDAO = new JDBCUserDAOImpl();
        userDAO.setConnection(conn);

        //Falta eliminar todo lo relacionado con el usuario borrado
        if (request.getParameter("deleteid") != null) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
            restaurantDAO.setConnection(conn);

            ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
            reviewsDAO.setConnection(conn);

            DishDAO dishDAO = new JDBCDishDAOImpl();
            dishDAO.setConnection(conn);

            OrderDAO orderDAO = new JDBCOrderDAOImpl();
            orderDAO.setConnection(conn);

            OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
            orderDishesDAO.setConnection(conn);

            RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
            restaurantCategoriesDAO.setConnection(conn);

            List<Dish> dishesUserRestaurants = new ArrayList<>();

            List<RestaurantCategories> restaurantCategories = new ArrayList<>();

            List<Restaurant> restaurantUserList = restaurantDAO.getAllByUser(user.getId());

            List<Review> userReviews = reviewsDAO.getAllByUser(user.getId());

            List<OrderDishes> orderDishes = new ArrayList<>();

            for (Restaurant restaurant : restaurantUserList) {
                dishesUserRestaurants = dishDAO.getByRestaurantRID(restaurant.getId());
                restaurantCategories = restaurantCategoriesDAO.getAllByRestaurant(restaurant.getId());
            }

            List<Order> userOrders = orderDAO.getAllByUser(user.getId());
            for (Order order : userOrders) {
                orderDishes = orderDishesDAO.getAllByOrder(order.getId());
            }

            for (Dish dish : dishesUserRestaurants) {
                dishDAO.delete(dish.getId());
            }

            for (RestaurantCategories restaurantCategories1 : restaurantCategories) {
                restaurantCategoriesDAO.delete(restaurantCategories1.getIdr(), restaurantCategories1.getIdct());
            }

            for (Restaurant restaurant : restaurantUserList) {
                restaurantDAO.delete(restaurant.getId());
            }

            for (Order order : userOrders) {
                orderDAO.delete(order.getId());
            }

            for (Review review : userReviews) {
                reviewsDAO.delete(review.getIdr(), review.getIdu());
            }

            for (OrderDishes orderDishes1 : orderDishes) {
                orderDishesDAO.delete(orderDishes1.getIdo(), orderDishes1.getIddi());
            }

            logger.info("Borrando usuario con id - email: " + user.getId() + "-" + user.getEmail());
            userDAO.delete(user.getId());
            RequestDispatcher view = request.getRequestDispatcher("LogoutServlet");
            view.forward(request, response);
        }
    }

}
