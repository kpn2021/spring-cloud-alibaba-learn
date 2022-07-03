package com.lxs.springcloud.alibaba;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public ResultVO<String> paymentSQL(Long id) {

        log.info("触发fallback降级!");

        ResultVO<String> res = new ResultVO<>();

        res.setSuccess(true);
        res.setSource("/consumer/feign/{id}");
        res.setReqTime(String.valueOf(System.currentTimeMillis()));
        res.setCode("500");
        res.setMsg("ok");
        res.setData("Feign调用，异常降级方法");
        return res;
    }
}
