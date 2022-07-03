package com.lxs.springcloud.alibaba;

import lombok.*;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success = false;
    private T data;
    private String code;


    private String msg;
    private String source;
    private String reqTime;
}
