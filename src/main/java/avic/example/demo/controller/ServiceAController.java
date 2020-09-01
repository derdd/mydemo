package avic.example.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceAController {
        @Autowired
        private RestTemplate restTemplate;
        
        @Bean
        public RestTemplate getRestTemplate() {
                return new RestTemplate();
        }
        
        @RequestMapping("/getInfo")
        @SentinelResource(value = "getInfo", fallback = "info2", blockHandler = "info3")
        public String info() {
                return "这是Service A";
        }
        
        public String info2() {
                return "这是降级返回结果";
        }
        
        // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
        public String info3(BlockException ex) {
                // Do some log here.
                ex.printStackTrace();
                return "这是熔断降级返回结果";
        }
}
