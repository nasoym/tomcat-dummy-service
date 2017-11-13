package hello;

import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MetricsFactory {

  @SuppressWarnings("MethodWithMultipleLoops")
  public static List<Collector.MetricFamilySamples> createGaugeMetrics(MBeanServer mBeanServer, String filterName, List<String> propertyKeys, List<String> attributeNames, String prefix) throws MalformedObjectNameException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
    List<Collector.MetricFamilySamples> samples = new ArrayList<>();
    ObjectName beanFilter = new ObjectName(filterName);
    Set<ObjectInstance> mBeans = mBeanServer.queryMBeans(beanFilter, null);

    for (String attributeName : attributeNames) {
      String metricName = attributeName.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
      GaugeMetricFamily gaugeMetricFamily = new GaugeMetricFamily(
          prefix + "_" + metricName,
          attributeName,
          propertyKeys
      );
      if (mBeans.size() > 0) {
        for (final ObjectInstance mBean : mBeans) {
          List<String> labelValueList = new ArrayList<>();
          for (String propertyKey : propertyKeys) {
            labelValueList.add(mBean.getObjectName().getKeyProperty(propertyKey).replaceAll("\"", "").replaceAll("-[0-9]+(\\.[0-9]+){5}", ""));
          }
          Object attribute = mBeanServer.getAttribute(mBean.getObjectName(), attributeName);
          gaugeMetricFamily.addMetric(labelValueList, Double.parseDouble(attribute.toString()));
        }
      }
      samples.add(gaugeMetricFamily);
    }
    return samples;
  }
}

