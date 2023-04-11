package com.wecheck.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
    private String code;
    private boolean success;
    private Object result;
    private String message;

    public CommonResponse(Object result) {
        this.success = true;
        this.result = result;
    }

    public CommonResponse(boolean success, Object result) {
        this.success = success;
        this.result = result;
    }

    public CommonResponse(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public CommonResponse(boolean success, Object result, String message, String code) {
        this.success = success;
        this.result = result;
        this.code = code;
        this.message = message;
    }

    public static CommonResponse of(Object result) {
        return new CommonResponse(result);
    }

    public static CommonResponse of(boolean success, @Nullable Object result) {
        return new CommonResponse(success, result);
    }

    public static CommonResponse of(boolean success, String code, String message) {
        return new CommonResponse(success, code, message);
    }

    public static CommonResponse of(boolean success, @Nullable Object result, String code, String message) {
        return new CommonResponse(success, result, code, message);
    }
}
