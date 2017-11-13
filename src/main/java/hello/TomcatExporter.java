package hello;

import io.prometheus.client.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class TomcatExporter extends Collector {

  private static final Logger LOG = LoggerFactory.getLogger(TomcatExporter.class);

  private MBeanServer mBeanServer;

  public TomcatExporter() {
    mBeanServer = ManagementFactory.getPlatformMBeanServer();
  }

  @Override
  public List<MetricFamilySamples> collect() {
    List<MetricFamilySamples> mfs = new ArrayList<>();
    addMetrics(
            mfs,
            "Catalina:type=ThreadPool,name=*",
            new ArrayList<String>() {{
              add("name");
            }},
            "thread_pool",
            /* null */
            new ArrayList<String>() {{
                /* add("acceptorThreadCount"); */
                /* add("acceptorThreadPriority"); */
                /* add("backlog"); */
                add("connectionCount");
                add("currentThreadCount");
                add("currentThreadsBusy");
                /* add("executorTerminationTimeoutMillis"); */
                /* add("keepAliveCount"); */
                /* add("keepAliveTimeout"); */
                /* add("localPort"); */
                add("maxConnections");
                /* add("maxHeaderCount"); */
                /* add("maxKeepAliveRequests"); */
                add("maxThreads");
                /* add("minSpareThreads"); */
                /* add("oomParachute"); */
                /* add("pollerThreadCount"); */
                /* add("pollerThreadPriority"); */
                /* add("port"); */
                /* add("selectorTimeout"); */
                /* add("sessionTimeout"); */
                /* add("soLinger"); */
                /* add("soTimeout"); */
                /* add("threadPriority"); */
            }}
    );
    addMetrics(
            mfs,
            "Catalina:type=Executor,name=*",
            new ArrayList<String>() {{
              add("name");
            }},
            "executor",
            /* null */
            new ArrayList<String>() {{
                add("activeCount");
                /* add("queueSize"); */
                /* add("largestPoolSize"); */
                /* add("poolSize"); */
                /* add("maxIdleTime"); */
                /* add("threadPriority"); */
                /* add("minSpareThreads"); */
                /* add("corePoolSize"); */
                add("completedTaskCount");
                /* add("maxThreads"); */
            }}
    );
    addMetrics(
            mfs,
            "Catalina:type=GlobalRequestProcessor,name=*",
            new ArrayList<String>() {{
              add("name");
            }},
            "request_processor",
            /* null */
            new ArrayList<String>() {{
              add("requestCount");
              add("maxTime");
              add("bytesReceived");
              add("bytesSent");
              add("processingTime");
              add("errorCount");
            }}
    );
    addMetrics(
            mfs,
            "Catalina:type=Manager,context=*,host=*",
            new ArrayList<String>() {{
              add("context");
              add("host");
            }},
            "manager",
            /* null */
            new ArrayList<String>() {{
                add("activeSessions");
                /* add("duplicates"); */
                add("expiredSessions");
                /* add("maxActive"); */
                /* add("maxActiveSessions"); */
                /* add("maxInactiveInterval"); */
                /* add("processExpiresFrequency"); */
                add("processingTime");
                add("rejectedSessions");
                add("sessionAverageAliveTime");
                add("sessionCounter");
                /* add("sessionCreateRate"); */
                /* add("sessionExpireRate"); */
                /* add("sessionIdLength"); */
                add("sessionMaxAliveTime");
            }}

    );

    return mfs;
  }

  private void addMetrics(
          List<MetricFamilySamples> mfs,
          String filterName,
          List<String> propertyKeys,
          String metricPrefix,
          List<String> beanNames
  ) {
    final String prefix = "tomcat_" + metricPrefix;
    MBeanServer mBeanServer = this.mBeanServer;
    try {
      List<MetricFamilySamples> samples = MetricsFactory.createGaugeMetrics(mBeanServer, filterName, propertyKeys, beanNames, prefix);

      mfs.addAll(samples);
    } catch (Exception e) {
      LOG.error("Error when quering Beans for : " + filterName, e);
    }
  }
}

