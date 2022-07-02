package com.lxs.springcloud.alibaba;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    public static final String SERVICE_URL = "http://payment-service";
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/consumer/ribbon/{id}")
    @SentinelResource(value = "ribbon", fallback = "handlerFallback", blockHandler = "blockHandler", exceptionsToIgnore = {
            IllegalArgumentException.class
    })
    public ResponseEntity<String> consumerRibbon(@PathVariable("id") Long id) {
        String result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, String.class);
        if (4 == id) {
            throw new IllegalArgumentException("id=4,抛出非法参数异常");
        } else if (id == null) {
            throw new NullPointerException("空指针异常,id不存在...");
        }
        return ResponseEntity.ok("ribbon方式:订单调用支付成功,订单号=" + id + ",调用结果=" + result);
    }

    public ResponseEntity<String> handlerFallback(@PathVariable("id") Long id, Throwable e) {
        return new ResponseEntity<String>("异常降级方法e=" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> blockHandler(@PathVariable("id") Long id, BlockException e) {
        return new ResponseEntity<String>("异常降级方法e=" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consumer/feign/{id}")
    public ResponseEntity<String> consumerFeign(@PathVariable("id") Long id) {
        ResponseEntity<String> result = paymentService.paymentSQL(id);
        return ResponseEntity.ok("feign方式:订单调用支付成功,订单号=" + id + ",调用结果=" + result.getBody());
    }



}
