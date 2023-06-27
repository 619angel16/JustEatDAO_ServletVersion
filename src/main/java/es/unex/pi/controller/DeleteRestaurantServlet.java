package es.unex.pi.controller;

import es.unex.pi.dao.JDBCRestaurantDAOImpl;
import es.unex.pi.dao.RestaurantDAO;
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

/**
 * Servlet implementation class DeleteRestaurantServlet
 */
public class DeleteRestaurantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRestaurantServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del DeleteRestaurantServlet");
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del DeleteRestaurantServlet");

        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        if (request.getParameter("restaurantid") != null) {
            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));
            if (restaurant.getIdu() == user.getId()) {
                logger.info("Borrando restaurant con id: " + restaurant.getId());
                restaurantDAO.delete(restaurant.getId());
                response.sendRedirect("UserRestaurantListServlet");
            }else {
				RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
				view.forward(request, response);
			}
        }
    }

}
