package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import java.util.Random;

@RestController
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private int waitMillisecondsMin = 10;
    private int waitMillisecondsMax = 500;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        LOGGER.info("request: /");
        return "Hello Docker World";
    }

    @RequestMapping("/createstring")
    @ResponseBody
    public String createstring(@RequestParam(value="count", required=false, defaultValue="1") int count) {
        LOGGER.info("request: /createstring value: " + count);
        Vector v = new Vector();
        for(int i=0;i<=count;i++) {
          String foo = new String("new String");
          v.add(foo);
        }
        return "ok";
    }
    
    @RequestMapping("/cpu")
    @ResponseBody
    public String cpu(@RequestParam(value="count", required=false, defaultValue="1") int count) {
        LOGGER.info("request: /cpu value: " + count);
        for(int j=0;j<=count;j++) {
          long increment =0l;
          for(long i=0;i<Integer.MAX_VALUE;i++) {
            increment +=1;
          }
        }
        return "ok";
    }


    @RequestMapping("/memory_usage")
    @ResponseBody
    public String memory_usage(@RequestParam(value="usage", required=false, defaultValue="1") int mb_usage) {
        LOGGER.info("request: /memory_usage value: " + mb_usage);
        Vector v = new Vector();
        for(int i=0; i<mb_usage; i++) {
          byte[] bytes = new byte[1024*1024];
          bytes[0] = 0;
          bytes[1] = 0;
          bytes[2] = 0;
          v.add(bytes);
        }
        return "ok";
    }

    @RequestMapping("/sleep")
    @ResponseBody
    public String sleep(@RequestParam(value="milliseconds", required=false, defaultValue="1") int milliseconds) {
        LOGGER.info("request: /sleep value: " + milliseconds);
        // TimeUnit.SECONDS.sleep(seconds);
        try {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException ex) {
            // Thread.currentThread().interrupt();
        }

        return "ok";
    }



    @RequestMapping(value="set_wait", method = RequestMethod.PUT)
    @ResponseBody
    public String set_wait(@RequestParam(value="max", required=true) int maxMilliseconds, @RequestParam(value="min", required=true, defaultValue="10") int minMilliseconds) {
        waitMillisecondsMax = maxMilliseconds;
        waitMillisecondsMin = minMilliseconds;
        LOGGER.info("request: set_wait: max: " + maxMilliseconds);
        LOGGER.info("request: set_wait: min: " + minMilliseconds);
        return "ok";
    }

    @RequestMapping(value="get_wait", method = RequestMethod.GET)
    @ResponseBody
    public String get_wait() {
        LOGGER.info("request: get_wait: " + waitMillisecondsMax);
        Random rand = new Random(System.currentTimeMillis()); 
        int currentWaitMilliseconds = rand.nextInt((waitMillisecondsMax - waitMillisecondsMin) + 1 ) + waitMillisecondsMin; 
        try {
            Thread.sleep(currentWaitMilliseconds);
        }
        catch(InterruptedException ex) {
            // Thread.currentThread().interrupt();
        }
        return "waited for (milliseconds): " + currentWaitMilliseconds + "\n";
    }


}
