package hello;

import io.prometheus.client.Summary;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestFilter implements Filter {

  private Summary requestSummary;
  private int pathComponents = 1;

  public RequestFilter(String name, String help, int pathComponents) {
    this(name, help);
    this.pathComponents = pathComponents;
  }

  public RequestFilter(String name, String help) {
    requestSummary = Summary.build(name, help)
        .quantile(0.5, 0.05)
        .quantile(0.75, 0.03)
        .quantile(0.9, 0.01)
        .quantile(0.95, 0.005)
        .quantile(0.99, 0.001)
        .labelNames("path","method")
        // .ageBuckets(1)
        // .maxAgeSeconds(300)
        .register();
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    if (!(servletRequest instanceof HttpServletRequest)) {
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    HttpServletRequest request = (HttpServletRequest) servletRequest;

    String path = request.getRequestURI();

    Summary.Timer timer = requestSummary
        .labels(getComponents(path), request.getMethod())
        .startTimer();

    try {
        filterChain.doFilter(servletRequest, servletResponse);
    } finally {
        timer.observeDuration();
    }
  }

  private String getComponents(String str) {
      if (str == null || pathComponents < 1) {
          return str;
      }
      int count = 0;
      int i =  -1;
      do {
          i = str.indexOf("/", i + 1);
          if (i < 0) {
              // Path is longer than specified pathComponents.
              return str;
          }
          count++;
      } while (count <= pathComponents);

      return str.substring(0, i);
  }


  @Override
  public void destroy() {
  }

}
