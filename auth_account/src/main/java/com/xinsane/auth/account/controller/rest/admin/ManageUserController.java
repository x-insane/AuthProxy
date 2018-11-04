package com.xinsane.auth.account.controller.rest.admin;

import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对用户的管理需满足如下条件之一：
 * - 用户对子账号
 * - 管理员对普通用户
 * - 超级管理员对所有用户
 */
@RestController
@RequestMapping("/api/admin/manage_user")
public class ManageUserController {

    @RequestMapping("list_user")
    public ApiResult list_user() {
        return new ApiResult();
    }

    @RequestMapping("list_manager")
    public ApiResult list_manager() {
        return new ApiResult();
    }

    @RequestMapping("list_all")
    public ApiResult list_all() {
        return new ApiResult();
    }

    @RequestMapping("list_user_auth")
    public ApiResult list_user_auth() {
        return new ApiResult();
    }

    /**
     * 授权全局通用权限给用户
     * 需要权限：该用户管理权限、已有该全局权限、满足权限前提
     */
    @RequestMapping("grant_auth_to_user")
    public ApiResult grant_auth_to_user() {
        return new ApiResult();
    }

    /**
     * 收回用户的全局通用权限
     * 需要权限：该用户管理权限、已有该全局权限、满足权限前提
     */
    @RequestMapping("revoke_auth_from_user")
    public ApiResult revoke_auth_from_user() {
        return new ApiResult();
    }

    /**
     * 授权站点权限给用户
     * 需要权限：该用户管理权限、该页面该权限 / 对应站点该权限 / edit_sites权限
     */
    @RequestMapping("grant_site_to_user")
    public ApiResult grant_site_to_user() {
        return new ApiResult();
    }

    /**
     * 收回用户的站点权限
     * 需要权限：该用户管理权限、该站点该权限 / edit_sites权限
     */
    @RequestMapping("revoke_site_from_user")
    public ApiResult revoke_site_from_user() {
        return new ApiResult();
    }

    /**
     * 授权页面权限给用户
     * 需要权限：该用户管理权限、该站点该权限 / edit_sites权限
     */
    @RequestMapping("grant_page_to_user")
    public ApiResult grant_page_to_user() {
        return new ApiResult();
    }

    /**
     * 收回用户的页面权限
     * 需要权限：该用户管理权限、该站点该权限 / edit_sites权限
     */
    @RequestMapping("revoke_page_from_user")
    public ApiResult revoke_page_from_user() {
        return new ApiResult();
    }
}
