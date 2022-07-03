package com.lxs.springcloud.alibaba;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
@Slf4j
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
    public ResultVO<String> consumerFeign(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);

    }


    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "dealTestHotKey")
    public ResultVO<String> testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2){
        log.info("p1:{}",p1);
        log.info("p2:{}",p2);
        log.info(Thread.currentThread().getName()+"\t"+"*****testHotKey******");
        ResultVO<String> res = new ResultVO<>();
        res.setSuccess(true);
        res.setSource("/testHotKey");
        res.setReqTime(String.valueOf(System.currentTimeMillis()));
        res.setCode("200");
        res.setMsg("ok");
        res.setData("*****testHotKey******");
        return res;
    }


    public ResultVO<String> dealTestHotKey(String p1,String p2,BlockException exception){
        ResultVO<String> res = new ResultVO<>();
        res.setSuccess(true);
        res.setSource("/testHotKey");
        res.setReqTime(String.valueOf(System.currentTimeMillis()));
        res.setCode("200");
        res.setMsg("ok");
        res.setData("*****dealTestHotKey******");
        return res;
    }








}
