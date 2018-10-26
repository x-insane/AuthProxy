package com.xinsane.auth.account.transfer;

import java.util.HashMap;

public class ApiResult extends HashMap<String, Object> {

    public ApiResult() {
        this(0, "ok");
    }

    public ApiResult(int error, String msg) {
        put("error", error);
        put("msg", msg);
    }

    public static Builder builder() {
        return new Builder();
    }

    private static class Builder {
        private ApiResult result = new ApiResult();
        public Builder set(String key, Object value) {
            result.put(key, value);
            return this;
        }
        public ApiResult build() {
            return result;
        }
    }
}
