package com.controller.dto;
import com.entity.Menu;
import lombok.Data;
import java.util.List;

@Data
// 与数据库有关的在usercontroller  接受用户信息的使用dto
// 用来接受前端的登录请求
public class UserDTO {
    private String username;
    private String passwords;
    private String nickname;
    private String avatarUrl;
    private String token;

    private String role; // 在登录的时候就返回角色是管理员还是普通用户 以及他们被分配的菜单
    private List<Menu> menus;
}
