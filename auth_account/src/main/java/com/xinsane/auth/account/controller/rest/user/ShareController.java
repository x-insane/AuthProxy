package com.xinsane.auth.account.controller.rest.user;

import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/share")
public class ShareController {

    @RequestMapping("list_all")
    public ApiResult list_all() {
        return new ApiResult();
    }

    @RequestMapping("revoke")
    public ApiResult revoke() {
        return new ApiResult();
    }

    @RequestMapping("add_site_to_token")
    public ApiResult add_site_to_token() {
        return new ApiResult();
    }

    @RequestMapping("remove_site_from_token")
    public ApiResult remove_site_from_token() {
        return new ApiResult();
    }

    @RequestMapping("add_page_to_token")
    public ApiResult add_page_to_token() {
        return new ApiResult();
    }

    @RequestMapping("remove_page_from_token")
    public ApiResult remove_page_from_token() {
        return new ApiResult();
    }
}
