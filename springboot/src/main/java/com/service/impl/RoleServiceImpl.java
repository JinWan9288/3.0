package com.service.impl;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Menu;
import com.entity.Role;
import com.entity.RoleMenu;
import com.mapper.RoleMapper;
import com.mapper.RoleMenuMapper;
import com.service.IMenuService;
import com.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  role服务实现类
 * </p>
 *
 * @author root
 * @since 2024-02-14
 */
 @Service
 public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;

    @Transactional //写在实现类或实现方法,而不是接口类,且方法中只有public的方法才有用。 方法一旦抛出异常，就会回滚，数据库里面的数据也会回滚。
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id",roleId);
//        roleMenuMapper.delete(queryWrapper);

        // 先删除角色所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds); // menuIds的数组副本  为了使存在子菜单的父菜单只插入一次
        // 将前端传来的menuId绑定到RoleId 上
        for (Integer menuId : menuIds){

            Menu menu = menuService.getById(menuId); // 获取拿到的菜单的id
            if( menu.getPid() != null && !( menuIdsCopy.contains(menu.getPid()) ) ){ // pid存在 有父级菜单 , 获取的菜单的父菜单的id不在所有菜单id中
                // 如果获取到的菜单是二级菜单 并且传过来的id没有他的父级id 说明只选择了部分子菜单
                // 那么就要在拿到的菜单id中加上父级菜单id 子菜单才会显示

                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);

        }
    }

    @Override
    public List<Integer> getMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
