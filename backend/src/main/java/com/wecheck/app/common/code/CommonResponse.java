package com.wecheck.app.common.code;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private String code;
    private Integer status;
    private T result;
    private boolean success = false;

    @Builder
    public CommonResponse(String code, Integer status, T result, boolean success) {
        this.code = code;
        this.status = status;
        this.result = result;
        this.success = success;
    }

    public CommonResponse(T result) {
        this.result = result;
        this.success = true;
    }

    public CommonResponse(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public static <T> CommonResponse<T> of(@Nullable T result) {
        return new CommonResponse<>(result);
    }

    public static <T> CommonResponse<T> of(boolean success, @Nullable T result) {
        return new CommonResponse<>(success, result);
    }
}
