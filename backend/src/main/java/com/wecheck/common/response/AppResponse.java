package com.wecheck.common.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AppResponse {
    private Object data;
    private Integer totalCount;
}
