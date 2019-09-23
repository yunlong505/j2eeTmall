package tmall.filter;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BackServletFilter implements Filter {
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getServletContext().getContextPath();
      //  System.out.println("request.getServletContext().getContextPath():" + contextPath);
        String uri = request.getRequestURI();
    //    System.out.println("request.getRequestURI():" + uri);
        uri = StringUtils.remove(uri, contextPath);
  //      System.out.println("StringUtilsz之后的uri:" + uri);
        if (uri.startsWith("/admin_")) {
            String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri, "_");
            request.setAttribute("method", method);
            req.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }
        chain.doFilter(request,response);

    }


}

