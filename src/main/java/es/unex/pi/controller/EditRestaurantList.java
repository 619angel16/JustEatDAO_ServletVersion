package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
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
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Servlet implementation class ListRestaurantServlet
 */
public class EditRestaurantList extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRestaurantList() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
        categoryDAO.setConnection(conn);

        List<Category> categories = categoryDAO.getAll();
        request.setAttribute("categories", categories);

        if (request.getParameter("restaurantid") != null) {
            RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
            restaurantDAO.setConnection(conn);

            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));
            if (user.getId() == restaurant.getIdu()) {
                RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
                restaurantCategoriesDAO.setConnection(conn);

                List<RestaurantCategories> restaurantCategories = restaurantCategoriesDAO.getAllByRestaurant(restaurant.getId());

                request.setAttribute("restaurantCategories", restaurantCategories);
                request.setAttribute("restaurant", restaurant);
            } else {
                RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                view.forward(request, response);
            }
        }
        RequestDispatcher view = request.getRequestDispatcher("/restaurant-edit.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del EditRestaurantList");
        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        UserDAO userDAO = new JDBCUserDAOImpl();
        userDAO.setConnection(conn);

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
        categoryDAO.setConnection(conn);

        RestaurantCategoriesDAO restaurantsCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
        restaurantsCategoriesDAO.setConnection(conn);

        if (request.getParameter("restaurantid") == null) {

            Restaurant restaurant = new Restaurant();

            restaurant.setIdu((int) user.getId());
            restaurant.setName(request.getParameter("name"));
            restaurant.setCity(request.getParameter("city"));
            restaurant.setAddress(request.getParameter("address"));
            restaurant.setTelephone(request.getParameter("telephone"));
            restaurant.setContactEmail(request.getParameter("email"));
            restaurant.setMinPrice(Integer.parseInt(request.getParameter("minPrice")));
            restaurant.setMaxPrice(Integer.parseInt(request.getParameter("maxPrice")));

            if (Objects.equals(request.getParameter("bike-friendly"), "si"))
                restaurant.setBikeFriendly(1);
            else
                restaurant.setBikeFriendly(0);

            if (Objects.equals(request.getParameter("status"), "si"))
                restaurant.setAvailable(1);
            else
                restaurant.setAvailable(0);
            long id = restaurantDAO.add(restaurant);

            String[] selCategories = request.getParameterValues("categorias");
            List<Category> categories = categoryDAO.getAll();
            for (Category category : categories) {
                for (String selCategory : selCategories) {
                    if (Objects.equals(category.getName(), selCategory)) {
                        RestaurantCategories restaurantCategory = new RestaurantCategories();
                        restaurantCategory.setIdr(id);
                        restaurantCategory.setIdct(category.getId());
                        restaurantsCategoriesDAO.add(restaurantCategory);
                    }
                }
            }
        } else if (request.getParameter("restaurantid") != null) {
            Restaurant restaurant = restaurantDAO.get(Long.parseLong(request.getParameter("restaurantid")));

            if (restaurant.getIdu() == user.getId()) {

                restaurant.setName(request.getParameter("name"));
                restaurant.setCity(request.getParameter("city"));
                restaurant.setAddress(request.getParameter("address"));
                restaurant.setTelephone(request.getParameter("telephone"));
                restaurant.setContactEmail(request.getParameter("email"));
                restaurant.setMinPrice(Integer.parseInt(request.getParameter("minPrice")));
                restaurant.setMaxPrice(Integer.parseInt(request.getParameter("maxPrice")));

                if (Objects.equals(request.getParameter("bike-friendly"), "si"))
                    restaurant.setBikeFriendly(1);
                else
                    restaurant.setBikeFriendly(0);

                if (Objects.equals(request.getParameter("status"), "si"))
                    restaurant.setAvailable(1);
                else
                    restaurant.setAvailable(0);

                System.out.println(restaurant.getId() + restaurant.getName() + restaurant.getContactEmail());

                restaurantDAO.update(restaurant);

                List<RestaurantCategories> restaurantCategoriesDel = restaurantsCategoriesDAO.getAllByRestaurant(restaurant.getId());
                for (RestaurantCategories restaurantCategory : restaurantCategoriesDel) {
                    restaurantsCategoriesDAO.delete(restaurantCategory.getIdr(), restaurantCategory.getIdct());
                }

                String[] selCategories = request.getParameterValues("categorias");
                List<Category> categories = categoryDAO.getAll();
                for (Category category : categories) {
                    for (String selCategory : selCategories) {
                        if (Objects.equals(category.getName(), selCategory)) {
                            RestaurantCategories restaurantCategory = new RestaurantCategories();
                            restaurantCategory.setIdr(restaurant.getId());
                            restaurantCategory.setIdct(category.getId());
                            restaurantsCategoriesDAO.add(restaurantCategory);
                        }
                    }
                }
            }else {
                RequestDispatcher view = request.getRequestDispatcher("InicioServlet");
                view.forward(request, response);
            }
        }
        RequestDispatcher view = request.getRequestDispatcher("UserRestaurantListServlet");
        view.forward(request, response);
    }
}


