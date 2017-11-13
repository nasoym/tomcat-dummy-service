package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

@RestController
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

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

}
