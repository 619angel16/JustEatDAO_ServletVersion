package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.Dish;
import es.unex.pi.model.Order;
import es.unex.pi.model.OrderDishes;
import es.unex.pi.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class NewOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    public NewOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del NewOrderServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        DishDAO dishDAO = new JDBCDishDAOImpl();
        dishDAO.setConnection(conn);

        HashSet<Long> orderDishes = (HashSet<Long>) session.getAttribute("orderDishes");

        List<Dish> dishes = new ArrayList<>();
        int totalPrice = 0;
        for (Long id : orderDishes) {
            dishes.add(dishDAO.get(id));
            totalPrice += dishDAO.get(id).getPrice();
        }
        request.setAttribute("dishes", dishes);
        request.setAttribute("orderDishes", orderDishes);
        request.setAttribute("totalPrice", totalPrice);
        RequestDispatcher view = request.getRequestDispatcher("PreviewRestaurantServlet");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del NewOrderServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new JDBCOrderDAOImpl();
        orderDAO.setConnection(conn);

        OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
        orderDishesDAO.setConnection(conn);

        DishDAO dishDAO = new JDBCDishDAOImpl();
        dishDAO.setConnection(conn);

        Order order = new Order();
        order.setIdu(user.getId());

        HashSet<Long> orderDishes = (HashSet<Long>) session.getAttribute("orderDishes");
        int totalPrice = 0;

        if (request.getParameter("adddishid") != null) {
            if (orderDishes != null) {
                if (!orderDishes.contains(Long.parseLong(request.getParameter("adddishid")))) {
                    orderDishes.add(Long.parseLong(request.getParameter("adddishid")));
                    session.setAttribute("orderDishes", orderDishes);
                } else {
                    orderDishes.removeIf(orderDish -> Long.parseLong(request.getParameter("adddishid")) == orderDish);
                    session.setAttribute("orderDishes", orderDishes);
                }
            } else {
                orderDishes = new HashSet<>();
                orderDishes.add(Long.parseLong(request.getParameter("adddishid")));
                session.setAttribute("orderDishes", orderDishes);
            }
            for (Long dishid : orderDishes) {
                totalPrice += dishDAO.get(dishid).getPrice();
            }

            request.setAttribute("totalPrice", totalPrice);
            doGet(request, response);
        } else {
            for (Long dishid : orderDishes) {
                totalPrice += dishDAO.get(dishid).getPrice();
            }
            order.setTotalPrice(totalPrice);
            long orderid = orderDAO.add(order);
            for (Long orderDishIds : orderDishes) {
                OrderDishes orderDishes1 = new OrderDishes();
                orderDishes1.setIddi(dishDAO.get(orderDishIds).getId());
                orderDishes1.setIdo(orderid);
                orderDishesDAO.add(orderDishes1);
            }
            orderDishes.clear();
            session.setAttribute("orderDishes", orderDishes);
        }
        doGet(request, response);
    }
}
