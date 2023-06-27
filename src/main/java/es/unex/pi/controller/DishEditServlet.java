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
import java.util.logging.Logger;

public class DishEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    public DishEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del DishEditServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        DishDAO dishDAO = new JDBCDishDAOImpl();
        dishDAO.setConnection(conn);


        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));

        if (user.getId() == restaurant.getIdu()) {
            if (request.getParameter("dishid") != null) {
                Dish dish = dishDAO.get(Long.parseLong(request.getParameter("dishid")));
                if (dish != null) {
                    if (user.getId() == restaurant.getIdu() && dish.getIdr() == restaurant.getId()) {
                        request.setAttribute("dish", dish);
                        request.setAttribute("restaurant", restaurant);
                    } else {
                        RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                        view.forward(request, response);
                    }
                } else {
                    RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                    view.forward(request, response);
                }
            }
            request.setAttribute("restaurant", restaurant);
            RequestDispatcher view = request.getRequestDispatcher("/edit-dish.jsp");
            view.forward(request, response);
        } else {
            RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del DishEditServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        DishDAO dishDAO = new JDBCDishDAOImpl();
        dishDAO.setConnection(conn);

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        if (request.getParameter("dishid") != null) {
            Dish dish = dishDAO.get(Long.parseLong(request.getParameter("dishid")));
            if (dish != null) {
                Restaurant restaurant = restaurantDAO.get(dish.getIdr());
                if (user.getId() == restaurant.getIdu()) {
                    if (dish.getIdr() == restaurant.getId()) {
                        dish.setName(request.getParameter("nombre_plato"));
                        dish.setDescription(request.getParameter("descripcion_plato"));
                        dish.setPrice(Integer.parseInt(request.getParameter("precio_plato")));
                        dishDAO.update(dish);

                        request.setAttribute("dish", dish);
                        request.setAttribute("restaurant", restaurant);
                        RequestDispatcher view = request.getRequestDispatcher("DishListServlet");
                        view.forward(request, response);
                    } else {
                        RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                        view.forward(request, response);
                    }
                } else {
                    RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                    view.forward(request, response);
                }
            } else {
                RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                view.forward(request, response);
            }
        }else {
            Dish dish = new Dish();
            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));
            dish.setName(request.getParameter("nombre_plato"));
            dish.setDescription(request.getParameter("descripcion_plato"));
            dish.setPrice(Integer.parseInt(request.getParameter("precio_plato")));
            dish.setIdr(restaurant.getId());
            dishDAO.add(dish);

            request.setAttribute("dish", dish);
            request.setAttribute("restaurant", restaurant);

            RequestDispatcher view = request.getRequestDispatcher("DishListServlet");
            view.forward(request, response);
        }
    }
}
