package com.xinsane.auth.account.controller.rest.admin;

import com.xinsane.auth.account.entity.AuthPageEntity;
import com.xinsane.auth.account.entity.AuthSiteEntity;
import com.xinsane.auth.account.entity.UserEntity;
import com.xinsane.auth.account.repository.*;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 被授权站点管理
 * 用户须具有edit_sites全局权限或manageable=true的站点
 */
@RestController
@RequestMapping(value = "/api/admin/site", method = RequestMethod.POST)
public class SiteController {

    private final UserRepository userRepository;
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final SiteUserRepository siteUserRepository;
    private final PageUserRepository pageUserRepository;

    @Autowired
    public SiteController(UserRepository userRepository, SiteUserRepository siteUserRepository,
                          SiteRepository siteRepository, PageRepository pageRepository,
                          PageUserRepository pageUserRepository) {
        this.userRepository = userRepository;
        this.siteUserRepository = siteUserRepository;
        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
        this.pageUserRepository = pageUserRepository;
    }

    @RequestMapping("page/list")
    public ApiResult page_list(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if  (user == null)
            return new ApiResult(403, "未登录");

        List<AuthSiteEntity> sites;
        if (userRepository.hasAuth(user.getUserId(), "edit_sites"))
            sites = siteRepository.queryAll();
        else
            sites = siteRepository.queryUserManagedSites(user.getUserId());
        if (sites == null ||  sites.isEmpty())
            return ApiResult.builder().set("sites", new byte[0]).build();

        List<Map<String, Object>> list = new ArrayList<>();
        for (AuthSiteEntity site : sites) {
            Map<String, Object> map = new HashMap<>();
            map.put("info", site);
            map.put("pages", pageRepository.queryAllBySiteId(site.getSiteId()));
            list.add(map);
        }
        return ApiResult.builder().set("sites", list).build();
    }

    @RequestMapping("page/add")
    public ApiResult page_add(@RequestBody AuthPageEntity page, HttpSession session) {
        ApiResult result = checkAuth(session, page.getSiteId());
        if (result != null)
            return result;

        AuthPageEntity _page = pageRepository.save(page.setPageId(0));
        return ApiResult.builder().set("page", _page).build();
    }

    @RequestMapping("page/modify")
    public ApiResult page_modify(@RequestBody AuthPageEntity page, HttpSession session) {
        AuthPageEntity _page = pageRepository.queryFirstByPageId(page.getPageId());
        if (_page == null)
            return new ApiResult(404, "页面不存在");

        ApiResult result = checkAuth(session, _page.getSiteId());
        if (result != null)
            return result;

        _page = pageRepository.save(page.setSiteId(_page.getSiteId()));
        return ApiResult.builder().set("page", _page).build();
    }

    @RequestMapping("page/delete")
    public ApiResult page_delete(@RequestBody AuthPageEntity page, HttpSession session) {
        AuthPageEntity _page = pageRepository.queryFirstByPageId(page.getPageId());
        if (_page == null)
            return new ApiResult(404, "页面不存在");

        ApiResult result = checkAuth(session, _page.getSiteId());
        if (result != null)
            return result;

        if (pageUserRepository.existsByPageId(page.getPageId()))
            return new ApiResult(402, "该页面含有对用户的授权，无法删除");

        pageRepository.deleteById(page.getPageId());
        return new ApiResult();
    }

    @RequestMapping("page/apply/list")
    public ApiResult list_page_apply() {
        // TODO: 2018/10/28
        return new ApiResult();
    }

    @RequestMapping("page/apply/handle")
    public ApiResult handle_page_apply() {
        // TODO: 2018/10/28
        return new ApiResult();
    }

    @RequestMapping("apply/list")
    public ApiResult list_apply() {
        // TODO: 2018/10/28
        return new ApiResult();
    }

    @RequestMapping("apply/handle")
    public ApiResult handle_apply() {
        // TODO: 2018/10/28
        return new ApiResult();
    }

    private ApiResult checkAuth(HttpSession session, int siteId) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null)
            return new ApiResult(403, "未登录");
        if (!userRepository.hasAuth(user.getUserId(), "edit_sites") &&
                !siteUserRepository.existsByUserIdAndSiteIdAndManageableIsTrue(user.getUserId(), siteId))
            return new ApiResult(403, "权限不足");
        return null;
    }
}
