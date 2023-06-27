package es.unex.pi.controller;

import es.unex.pi.dao.*;
import es.unex.pi.model.Category;
import es.unex.pi.model.Restaurant;
import es.unex.pi.model.RestaurantCategories;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Servlet implementation class RestaurantesBusquedaServlet
 */
public class RestaurantesBusquedaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantesBusquedaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo GET del RestaurantesBusquedaServlet");

        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        RestaurantCategoriesDAO restaurantCategoriesDAO = new JDBCRestaurantCategoriesDAOImpl();
        restaurantCategoriesDAO.setConnection(conn);

        RestaurantDAO restaurantDAO = new JDBCRestaurantDAOImpl();
        restaurantDAO.setConnection(conn);

        List<Restaurant> restaurantList = new ArrayList<>();

        CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
        categoryDAO.setConnection(conn);

        HashMap<Long, String> categoriesByRestaurant = new HashMap<>();


        if (request.getParameter("categoryid") != null) {
            restaurantList = restaurantDAO.getAllByCategory(Long.parseLong(request.getParameter("categoryid")));
        } else if (request.getParameter("search") != null) {
            if (restaurantDAO.getAllByCity(request.getParameter("search")) != null)
                restaurantList = restaurantDAO.getAllByCity(request.getParameter("search"));
            if (restaurantList.isEmpty())
                restaurantList = restaurantDAO.getAllBySearchName(request.getParameter("search"));
        }

        List<Restaurant> restaurantsListAux = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {

            if (Objects.equals(request.getParameter("availabity"), "1")) {
                if (restaurant.getAvailable() == 1) {
                    restaurantsListAux.add(restaurant);
                }
            } else if (Objects.equals(request.getParameter("availabity"), "0")) {
                if (restaurant.getAvailable() == 0) {
                    restaurantsListAux.add(restaurant);
                }
            }
            final ArrayList<Category> categoriesList = new ArrayList<>();
            List<RestaurantCategories> restaurantCategories = restaurantCategoriesDAO.getAllByRestaurant(restaurant.getId());
            for (RestaurantCategories resCat : restaurantCategories) {
                Category category = categoryDAO.get(resCat.getIdct());
                categoriesList.add(category);
            }
            final String categoriesString = categoriesList.stream().map(category -> category.getName()).collect(Collectors.joining(" • "));
            categoriesByRestaurant.put(restaurant.getId(), categoriesString);
        }
        if (request.getParameter("availabity") != null) {
            restaurantList = restaurantsListAux;
        }
        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("categoriesByRestaurant", categoriesByRestaurant);
        RequestDispatcher view = request.getRequestDispatcher("/restaurantes-busqueda.jsp");
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Atendiendo POST del RestaurantesBusquedaServlet");
        doGet(request, response);
    }

}
