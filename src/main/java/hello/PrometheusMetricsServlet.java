package hello;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/prometheus")
public class PrometheusMetricsServlet extends MetricsServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    DefaultExports.initialize();
    new TomcatExporter().register();
  }

  @Bean
  public FilterRegistrationBean alleRequestsMessen2() {
    final FilterRegistrationBean result = new FilterRegistrationBean();
    result.setOrder(Integer.MIN_VALUE);
    result.setFilter(new RequestFilter("requests", "request timings"));
    result.addUrlPatterns("/*");
    return result;
  }

}
