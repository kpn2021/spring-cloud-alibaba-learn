package com.lxs.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

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
    public ResponseEntity<String> paymentSQL(@PathVariable("id") Long id) {
        return ResponseEntity.ok("订单号= " + id + ",支付成功,server.port" + serverPort);
    }
}
