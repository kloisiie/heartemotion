package com.brframework.commonwebadmin.service.admin;

import com.brframework.cms2.entity.cms.CmsMenu;
import com.brframework.cms2.entity.cms.CmsMenuFun;
import com.brframework.cms2.entity.cms.CmsMenuSub;
import com.brframework.cms2.entity.cms.CmsPage;
import com.brframework.cms2.globals.CmsConstant;
import com.brframework.cms2.service.cms.CmsMenuFunService;
import com.brframework.cms2.service.cms.CmsMenuService;
import com.brframework.cms2.service.cms.CmsMenuSubService;
import com.brframework.cms2.service.cms.CmsPageService;
import com.brframework.commonwebadmin.json.admin.adminuser.AdminMenu;
import com.brframework.commonwebadmin.json.admin.adminuser.AdminMenuFun;
import com.brframework.commonwebadmin.json.admin.adminuser.AdminMenuSub;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author xu
 * @date 2018/3/12 0012 下午 8:01
 * 功能列表
 */
@Service
public class FunctionService{

    /** 菜单Service */
    @Autowired
    public CmsMenuService cmsMenuService;

    /** 子菜单Service */
    @Autowired
    public CmsMenuSubService cmsMenuSubService;

    /** 菜单功能Service */
    @Autowired
    public CmsMenuFunService cmsMenuFunService;

    /** 页面Service */
    @Autowired
    public CmsPageService cmsPageService;

    /**
     * 获取当前登录用户的功能列表
     *
     * @return
     */
    public List<AdminMenu> getRoleMenus(Collection<GrantedAuthority> roles) {
        if(roles.size() == 0) {
            return Lists.newArrayList();
        }

        List<AdminMenu> menus = Lists.newArrayList();
        List<CmsMenu> adminMenus = cmsMenuService.listCmsMenu();
        for (CmsMenu adminMenu : adminMenus) {
            List<CmsMenuSub> adminSubMenus = cmsMenuSubService.listCmsMenuSubByMenuId(adminMenu.getId());
            AdminMenu master = new AdminMenu();
            master.setName(adminMenu.getName());
            master.setIcon(adminMenu.getIcon());
            master.setType(adminMenu.getType());
            master.setHide(adminMenu.getHide());

            master.setChildren(Lists.newArrayList());
            for (CmsMenuSub subMenu : adminSubMenus) {
                //验证是否有权限
                if(!hasRole(roles, subMenu.getRole())){
                    //没有权限
                    continue;
                }

                AdminMenuSub sub = new AdminMenuSub();
                sub.setName(subMenu.getName());
                sub.setIcon(subMenu.getIcon());
                sub.setHide(subMenu.getHide());

                CmsPage cmsPage = cmsPageService.findCmsPageById(subMenu.getPageId());
                sub.setPageRoute(cmsPage.getRoute());
                sub.setRole(subMenu.getRole());
                sub.setChildren(Lists.newArrayList());

                List<CmsMenuFun> menuFuns = cmsMenuFunService.listCmsMenuFunByMenuSubId(subMenu.getId());

                for (CmsMenuFun menuFun : menuFuns) {
                    //验证是否有权限
                    if(!hasRole(roles, menuFun.getRole())){
                        //没有权限
                        continue;
                    }

                    AdminMenuFun fun = new AdminMenuFun();
                    fun.setName(menuFun.getName());
                    fun.setRole(menuFun.getRole());

                    sub.getChildren().add(fun);
                }

                master.getChildren().add(sub);
            }

            if(master.getChildren().size() != 0){
                menus.add(master);
            }

        }
        return menus;
    }


    private boolean hasRole(Collection<GrantedAuthority> roles, String hasRole){
        for (GrantedAuthority role : roles) {
            if(StringUtils.isBlank(hasRole)){
                return true;
            }
            if(("ROLE_" + hasRole).equals(role.getAuthority())){
                return true;
            }
        }
        return false;
    }



    /**
     * 获取所有菜单功能
     * @return
     */
    public List<CmsMenuFun> getMenuFunList(){
        List<CmsMenuFun> menuFunList = Lists.newArrayList();
        for (CmsMenu menu : cmsMenuService.listCmsMenu()) {
            for (CmsMenuSub menuSub : cmsMenuSubService.listCmsMenuSubByMenuId(menu.getId())) {
                menuFunList.addAll(cmsMenuFunService.listCmsMenuFunByMenuSubId(menuSub.getId()));
            }
        }
        return menuFunList;
    }

}
