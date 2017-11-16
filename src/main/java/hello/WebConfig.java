package hello;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Configuration
// @SpringBootApplication
public class WebConfig {

  // @Bean
  // public FilterRegistrationBean alleRequestsMessen2() {
  //   final FilterRegistrationBean result = new FilterRegistrationBean();
  //   result.setOrder(Integer.MIN_VALUE);
  //   result.setFilter(new RequestFilter("requests", "request timings"));
  //   result.setName("requestFilter");
  //   result.addUrlPatterns("#<{(|");
  //   return result;
  // }

}
