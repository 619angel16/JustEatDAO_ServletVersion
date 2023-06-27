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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class OrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del OrdersServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new JDBCOrderDAOImpl();
        orderDAO.setConnection(conn);

        DishDAO dishDAO = new JDBCDishDAOImpl();
        dishDAO.setConnection(conn);

        OrderDishesDAO orderDishesDAO = new JDBCOrderDishesDAOImpl();
        orderDishesDAO.setConnection(conn);

        HashMap<Long, List<Dish>> dishesByOrder = new HashMap<>();

        List<Order> orders = orderDAO.getAllByUser(user.getId());
        for (Order order : orders) {
            List<OrderDishes> orderDishes = orderDishesDAO.getAllByOrder(order.getId());
            List<Dish> dishes = new ArrayList<>();
            for (OrderDishes ordDish : orderDishes) {
                dishes.add(dishDAO.get(ordDish.getIddi()));
            }
            dishesByOrder.put(order.getId(), dishes);
        }

        request.setAttribute("dishesByOrder", dishesByOrder);
        request.setAttribute("userOrders", orders);

        RequestDispatcher view = request.getRequestDispatcher("/user-orders.jsp");
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del LoginServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
    }
}

