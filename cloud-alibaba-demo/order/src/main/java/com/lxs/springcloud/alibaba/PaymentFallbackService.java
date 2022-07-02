package com.lxs.springcloud.alibaba;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public ResponseEntity<String> paymentSQL(Long id) {
        return new ResponseEntity<String>("Feign调用，异常降级方法", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
