package hello;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  @Bean
  public FilterRegistrationBean alleRequestsMessen2() {
    final FilterRegistrationBean result = new FilterRegistrationBean();
    result.setOrder(Integer.MIN_VALUE);
    result.setFilter(new RequestFilter("requests", "request timings"));
    result.addUrlPatterns("/*");
    return result;
  }

}
