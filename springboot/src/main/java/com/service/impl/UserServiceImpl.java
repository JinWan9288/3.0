package com.service.impl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.Utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.Constants;
import com.controller.dto.UserDTO;
import com.entity.Menu;
import com.entity.User;
import com.exception.ServiceException;
import com.mapper.RoleMapper;
import com.mapper.RoleMenuMapper;
import com.mapper.UserMapper;
import com.service.IMenuService;
import com.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author root
 * @since 2024-02-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get(); // hutool打印异常队列

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) { // 对userDTO进行校验
        User user = getUserInfo(userDTO);
        if(user != null){   //  传到后端的数据只有用户名密码, 需要增加属性再返回去
            BeanUtil.copyProperties(user,userDTO,true); // 用户存在 把查到的user复制到dto中 忽略大小写

            //设置token
            String token = TokenUtils.getToken(user.getId().toString(),user.getPasswords());  // id是整型 token是字符串所以要转换
            userDTO.setToken(token); // 将token注入dto

            String flag = user.getRole(); //     ROLE_ADMIN,ROLE_USER
            List<Menu> roleMenu = getRoleMenus(flag);
            userDTO.setMenus(roleMenu);
            return userDTO;  // 源是user, 目的是dto ,最后返回的也是dto 实际是将源中的属性一一复制到目标中(只要两个对象有相同的属性名)
        }else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO){ // 先校验, 再插入数据库
        User user = getUserInfo(userDTO);
        if(user == null){ // 要插入新用户, 源数据库中必须没有相同的数据, 这与登录是相反的逻辑
            user = new User();
            BeanUtil.copyProperties(userDTO,user,true);// 现在是dto里有数据, 需要插入数据库 ,
            save(user); // 插入数据库
        }else {
            throw new ServiceException(Constants.CODE_600, "该用户已存在");
        }
        return user;
    }


    private User getUserInfo(UserDTO userDTO){   //封装得到用户信息的方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();    //条件构造器  User 去数据库查询
        queryWrapper.eq("username",userDTO.getUsername()); // 在数据库中比较是否相等
        queryWrapper.eq("passwords",userDTO.getPasswords());

//        // getOne方法只能找到数据库中唯一的数据的用户名密码, 当出现两个一样的数据时会无法登录  适用于用户名唯一 不能重复的情况
//        User user = getOne(queryWrapper);   // 用户名密码都存在并且与数据库中的相等 用user存储 user不为空就是存在 返回true
//        return user != null;
//        // 解决方法1: 使用list, 只要查到一个就允许登录
//        List<User> list = list(queryWrapper); // 将查到的符合条件的数据都列出来，不论是唯一还是有多个
//        return list.size() != 0;

        // 解决方法2: 使用try catch捕获异常, 只要发现了多条一样的数据就会捕获到异常, 将查询语句try , 一旦发生异常，就false
        User user;
        try { // try-catch防止脏数据  if判断用户在不在数据库 是否在数据库存在
            user = getOne(queryWrapper);
        } catch (Exception e) { // 脏数据
            e.printStackTrace(); // 打印异常队列
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return user;
    }


    private List<Menu> getRoleMenus(String flag){ // 获取当前角色菜单列表
        Integer roleId = roleMapper.selectByFlag(flag);// 根据flag查出role id
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId); // 被分配的菜单号

        List<Menu> menus = menuService.findMenus(""); // 查出所有菜单号
        List<Menu> roleMenu = new ArrayList<>(); // 最后显示的菜单用roleMenu承接
        for (Menu menu : menus){  // 筛选当前登录用户管理的菜单 过滤
            if (menuIds.contains(menu.getId())){ // 如果在menus中有查到的菜单id，就显示出来
                roleMenu.add(menu);
            }
            List<Menu> children = menu.getChildren(); // 拿到这个用户的管理的所有菜单的 子菜单
            children.removeIf( child -> !(menuIds.contains(menu.getId())) ); // 如果子菜单children不包含于查到的分配给用户的菜单中就删除子
        }
        return roleMenu;
    }

}