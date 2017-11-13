package hello;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.prometheus.client.filter.MetricsFilter;

@Configuration
public class Metrics {

  @Bean
  public ServletRegistrationBean registerPrometheusExporterServlet() {
    CollectorRegistry.defaultRegistry.clear();
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new MetricsServlet(), "/prometheus");
    DefaultExports.initialize();
    return servletRegistrationBean;
  }

  // @Bean
  // public FilterRegistrationBean alleRequestsMessen() {
  //   final FilterRegistrationBean result = new FilterRegistrationBean();
  //   result.setOrder(Integer.MIN_VALUE);
  //   result.setFilter(
  //       new MetricsFilter(
  //               "request_duration_seconds",
  //               "Help for my filter",
  //               1,
  //               new double[]{0.005,0.01,0.025,0.05,0.075,0.1,0.25,0.5,0.75,1,2.5,5,7.5,10}
  //       )
  //       );
  //   result.addUrlPatterns("#<{(|");
  //   return result;
  // }

  @Bean
  public FilterRegistrationBean alleRequestsMessen2() {
    final FilterRegistrationBean result = new FilterRegistrationBean();
    result.setOrder(Integer.MIN_VALUE);
    result.setFilter(new RequestFilter("requests", "request timings"));
    result.addUrlPatterns("/*");
    return result;
  }

}
