package com.xinsane.auth.account.controller.rest.user;

import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invite")
public class InviteController {

    @RequestMapping("list")
    public ApiResult list() {
        return new ApiResult();
    }

    @RequestMapping("create")
    public ApiResult create() {
        return new ApiResult();
    }

    @RequestMapping("revoke")
    public ApiResult revoke() {
        return new ApiResult();
    }
}
