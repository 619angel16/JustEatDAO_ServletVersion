package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.Review;
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
import java.util.List;
import java.util.logging.Logger;

public class ReviewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    public ReviewsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del ReviewsServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
        reviewsDAO.setConnection(conn);

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        UserDAO userDAO = new JDBCUserDAOImpl();
        userDAO.setConnection(conn);

        if (request.getParameter("restaurantid") != null) {

            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));

            List<User> userList = new ArrayList<>();

            List<Review> reviewList = reviewsDAO.getAllByRestaurant(restaurant.getId());

            for (Review review : reviewList) {
                userList.add(userDAO.get(review.getIdu()));
            }
            request.setAttribute("userList", userList);
            request.setAttribute("reviews", reviewList);

        } else if (reviewsDAO.get(Long.parseLong(request.getParameter("reviewid")), user.getId()) != null) {
            Review review = reviewsDAO.get(Long.parseLong(request.getParameter("reviewid")), user.getId());
            request.setAttribute("review", review);
            RequestDispatcher view = request.getRequestDispatcher("/add-review.jsp");
            view.forward(request, response);
        } else {
            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("reviewid")));
            request.setAttribute("restaurant", restaurant);
            RequestDispatcher view = request.getRequestDispatcher("/add-review.jsp");
            view.forward(request, response);
        }
        RequestDispatcher view = request.getRequestDispatcher("/users-reviews.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del ReviewsServlet");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        ReviewsDAO reviewsDAO = new JDBCReviewsDAOImpl();
        reviewsDAO.setConnection(conn);

        if (request.getParameter("delete") != null) {

            reviewsDAO.delete(Long.parseLong(request.getParameter("restaurantid")), user.getId());

        } else if (request.getParameter("restaurantid") != null && request.getParameter("userid") != null) {

            if (reviewsDAO.get(Long.parseLong(request.getParameter("restaurantid")), user.getId()) != null) {

                Review review = reviewsDAO.get(Long.parseLong(request.getParameter("restaurantid")), Long.parseLong(request.getParameter("userid")));
                review.setReview(request.getParameter("description-textarea"));
                review.setGrade(Integer.parseInt(request.getParameter("ratingScore")));
                reviewsDAO.update(review);
                //TODO actualizar gradesAverage
            }
        } else if (request.getParameter("restaurantid") != null) {

            Review review = new Review();
            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));
            review.setIdr(restaurant.getId());
            review.setIdu(user.getId());
            review.setReview(request.getParameter("description-textarea"));
            review.setGrade(Integer.parseInt(request.getParameter("ratingScore")));
            reviewsDAO.add(review);
        } else {

            RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
            view.forward(request, response);
        }

        doGet(request, response);
    }
}
