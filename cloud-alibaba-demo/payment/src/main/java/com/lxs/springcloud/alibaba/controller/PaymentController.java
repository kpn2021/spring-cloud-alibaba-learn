package com.lxs.springcloud.alibaba.controller;

import com.lxs.springcloud.alibaba.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@RestController
@RequestScope
public class PaymentController {
    @Value("${config.info:test}")
    private String configInfo;


    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/paymentSQL/{id}")
    public ResultVO<String> paymentSQL(@PathVariable("id") Long id) {
        log.info(Thread.currentThread().getName()+"\t"+"*****paymentSQL******");

        ResultVO<String> res = new ResultVO<>();
        res.setSuccess(true);
        res.setSource("/consumer/feign/{id}");
        res.setReqTime(String.valueOf(System.currentTimeMillis()));
        res.setCode("200");
        res.setMsg("ok");
        res.setData("订单号= " + id + ",支付成功,server.port" + serverPort);
        return res;
    }
}
