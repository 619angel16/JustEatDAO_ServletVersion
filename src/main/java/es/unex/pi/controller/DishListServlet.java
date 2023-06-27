package es.unex.pi.controller;

import es.unex.pi.dao.DishDAO;
import es.unex.pi.dao.JDBCDishDAOImpl;
import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
import es.unex.pi.model.Dish;
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

public class DishListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    public DishListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del DishListServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));
        System.out.println(restaurant.getName());
        if (request.getParameter("restaurantid") != null && user.getId() == restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid"))).getIdu()) {

            DishDAO dishDAO = new JDBCDishDAOImpl();
            dishDAO.setConnection(conn);
            List<Dish> dishes = dishDAO.getByRestaurantRID(Long.parseLong(request.getParameter("restaurantid")));

            request.setAttribute("dishes", dishes);
            request.setAttribute("restaurant", restaurant);
            RequestDispatcher view = request.getRequestDispatcher("/restaurant-dish-list.jsp");
            view.forward(request, response);
        } else if (user.getId() != restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid"))).getIdu()) {
            RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
            view.forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del DishListServlet");
        doGet(request, response);
    }
}
