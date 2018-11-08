package com.xinsane.auth.account.controller.rest.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xinsane.auth.account.entity.ConfigEntity;
import com.xinsane.auth.account.entity.UserEntity;
import com.xinsane.auth.account.repository.ConfigRepository;
import com.xinsane.auth.account.repository.UserRepository;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/config", method = RequestMethod.POST)
public class ConfigController {

    private final ConfigRepository configRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConfigController(ConfigRepository configRepository, UserRepository userRepository) {
        this.configRepository = configRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping
    public ApiResult index(@RequestBody String json, HttpSession session) {
        ApiResult check = checkAuth(session);
        if (check != null)
            return check;

        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        String key = object.get("key").getAsString();
        String value = object.get("value").getAsString();

        ConfigEntity config = configRepository.queryByKey(key);
        if (config == null)
            config = new ConfigEntity().setKey(key).setValue(value);
        else
            config.setValue(value);
        configRepository.save(config);

        return ApiResult.builder().set("config", config).build();
    }

    @RequestMapping("delete")
    public ApiResult delete(@RequestBody String json, HttpSession session) {
        ApiResult check = checkAuth(session);
        if (check != null)
            return check;

        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        String key = object.get("key").getAsString();

        ConfigEntity config = configRepository.queryByKey(key);
        if (config == null)
            return new ApiResult(404, "不存在该配置");

        configRepository.delete(config);
        return new ApiResult();
    }

//    @RequestMapping("query")
//    public ApiResult query(@RequestParam String key, HttpSession session) {
//        ApiResult check = checkAuth(session);
//        if (check != null)
//            return check;
//
//        String value = configRepository.value(key);
//        if (value == null)
//            return new ApiResult(404, "不存在该配置");
//
//        return new ApiResult(0, value);
//    }

    @RequestMapping("query_all")
    public ApiResult queryAll(HttpSession session) {
        ApiResult check = checkAuth(session);
        if (check != null)
            return check;

        return ApiResult.builder().set("configs", configRepository.queryAll()).build();
    }

    private ApiResult checkAuth(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "未登录");
        if (!userRepository.hasAuth(user.getUserId(), "config"))
            return new ApiResult(403, "权限不足");
        return null;
    }

    static class ConfigsResult extends ApiResult {
        private List<ConfigEntity> configs;

        public List<ConfigEntity> getConfigs() {
            return configs;
        }

        public ConfigsResult setConfigs(List<ConfigEntity> configs) {
            this.configs = configs;
            return this;
        }
    }
}
