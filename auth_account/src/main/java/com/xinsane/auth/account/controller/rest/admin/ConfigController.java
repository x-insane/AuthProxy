package com.xinsane.auth.account.controller.rest.admin;

import com.xinsane.auth.account.entity.ConfigEntity;
import com.xinsane.auth.account.entity.UserEntity;
import com.xinsane.auth.account.repository.ConfigRepository;
import com.xinsane.auth.account.repository.UserRepository;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResult index(@RequestParam String key, @RequestParam String value, HttpSession session) {
        ApiResult check = checkAuth(session);
        if (check != null)
            return check;

        ConfigEntity config = configRepository.queryByKey(key);
        if (config == null)
            config = new ConfigEntity().setKey(key).setValue(value);
        else
            config.setValue(value);
        configRepository.save(config);

        return new ApiResult();
    }

    @RequestMapping("query")
    public ApiResult query(@RequestParam String key, HttpSession session) {
        ApiResult check = checkAuth(session);
        if (check != null)
            return check;

        String value = configRepository.value(key);
        if (value == null)
            return new ApiResult(404, "不存在该配置");

        return new ApiResult(0, value);
    }

    @RequestMapping("query-all")
    public ApiResult queryAll(HttpSession session) {
        ApiResult check = checkAuth(session);
        if (check != null)
            return check;

        ConfigsResult result = new ConfigsResult();
        result.configs = configRepository.queryAll();
        return result;
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
