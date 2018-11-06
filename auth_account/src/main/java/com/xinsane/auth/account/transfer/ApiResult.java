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

    public static Element element() {
        return new Element();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(int error, String msg) {
        return new Builder().set("error", error).set("msg", msg);
    }

    public static class Builder {
        private ApiResult result = new ApiResult();
        public Builder set(String key, Object value) {
            result.put(key, value);
            return this;
        }
        public ApiResult build() {
            return result;
        }
    }

    public static class Element extends HashMap<String, Object> {
        public Element set(String key, Object value) {
            super.put(key, value);
            return this;
        }
    }
}
