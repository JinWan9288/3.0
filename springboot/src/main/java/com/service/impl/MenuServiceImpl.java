package com.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Menu;
import com.mapper.MenuMapper;
import com.service.IMenuService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  role服务实现类
 * </p>
 *
 * @author root
 * @since 2024-02-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String menuname) { // 封装根据菜单名查找菜单的方法，可以在UserService中导入引用
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        if(!"".equals(menuname)){ // 名字的字符串不为空
            queryWrapper.like("menuname",menuname);
        }

        List<Menu> list = list(queryWrapper);
        //先找到一级菜单， pid为null的数据就是没有父级的一级菜单
        List<Menu> ParentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menus : ParentNode){ // 通过一级菜单去渲染 找到二级菜单 pid == 父级id
            List<Menu> ChildNode = list.stream().filter(m -> menus.getId().equals(m.getPid())).collect(Collectors.toList());
            menus.setChildren(ChildNode);
        }
        return ParentNode;
    }
}
