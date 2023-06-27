package es.unex.pi.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;


@WebFilter(urlPatterns = {"/rest/*"})
public class LoginFilter implements Filter {
    private static final Logger logger = Logger.getLogger(Filter.class.getName());

    public void init(FilterConfig fc) {

    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        logger.info("Checking user in session");

        if (session.getAttribute("user") == null && req.getRequestURI().equals("/rest/users/") && req.getMethod().equals("POST")) {
            res.sendRedirect(req.getContextPath() + "/LoginServlet");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

}

