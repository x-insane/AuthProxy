package com.xinsane.auth.account.controller.rest.user;

import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查看和分享我被授权的站点和页面
 */
@RestController
@RequestMapping(value = "/api/user/site_page", method = RequestMethod.POST)
public class SitePageController {

    @RequestMapping("list_all")
    public ApiResult list_all() {
        return new ApiResult();
    }

    @RequestMapping("share_site")
    public ApiResult share_site() {
        return new ApiResult();
    }

    @RequestMapping("share_page")
    public ApiResult share_page() {
        return new ApiResult();
    }
}
