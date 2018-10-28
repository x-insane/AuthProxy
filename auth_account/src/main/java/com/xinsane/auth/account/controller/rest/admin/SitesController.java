package com.xinsane.auth.account.controller.rest.admin;

import com.xinsane.auth.account.entity.AuthSiteEntity;
import com.xinsane.auth.account.entity.UserEntity;
import com.xinsane.auth.account.repository.PageRepository;
import com.xinsane.auth.account.repository.SiteRepository;
import com.xinsane.auth.account.repository.SiteUserRepository;
import com.xinsane.auth.account.repository.UserRepository;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 全局站点管理
 */
@RestController
@RequestMapping(value = "/api/admin/sites", method = RequestMethod.POST)
public class SitesController {

    private final SiteRepository siteRepository;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;
    private final SiteUserRepository siteUserRepository;

    @Autowired
    public SitesController(SiteRepository siteRepository, UserRepository userRepository,
                           PageRepository pageRepository, SiteUserRepository siteUserRepository) {
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
        this.pageRepository = pageRepository;
        this.siteUserRepository = siteUserRepository;
    }

    @RequestMapping("add")
    public ApiResult add(@RequestBody AuthSiteEntity site, HttpSession session) {
        ApiResult result = checkAuth(session);
        if (result != null)
            return  result;

        AuthSiteEntity _site = siteRepository.save(site);
        return ApiResult.builder().set("site", _site).build();
    }

    @RequestMapping("modify")
    public ApiResult modify(@RequestBody AuthSiteEntity site, HttpSession session) {
        ApiResult result = checkAuth(session);
        if (result != null)
            return  result;

        AuthSiteEntity _site = siteRepository.queryFirstBySiteId(site.getSiteId());
        if (_site == null)
            return new ApiResult(404, "该站点不存在");

        _site = siteRepository.save(site);
        return ApiResult.builder().set("site", _site).build();
    }

    @RequestMapping("delete")
    public ApiResult delete(@RequestBody AuthSiteEntity site, HttpSession session) {
        ApiResult result = checkAuth(session);
        if (result != null)
            return  result;

        AuthSiteEntity _site = siteRepository.queryFirstBySiteId(site.getSiteId());
        if (_site == null)
            return new ApiResult(404, "该站点不存在");

        if (pageRepository.existsBySiteId(site.getSiteId()))
            return new ApiResult(402, "该站点含有页面，无法删除");

        if (siteUserRepository.existsBySiteId(site.getSiteId()))
            return new ApiResult(402, "该站点含有对用户的授权，无法删除");

        siteRepository.deleteById(site.getSiteId());
        return new ApiResult();
    }

    @RequestMapping("query-all")
    public ApiResult query_all(HttpSession session) {
        ApiResult result = checkAuth(session);
        if (result != null)
            return result;

        List<AuthSiteEntity> list = siteRepository.queryAll();
        return ApiResult.builder().set("sites", list).build();
    }

    private ApiResult checkAuth(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "未登录");
        if (!userRepository.hasAuth(user.getUserId(), "edit_sites"))
            return new ApiResult(403, "权限不足");
        return null;
    }

}
