package com.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.entity.Role;
import com.service.IRoleService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @GetMapping("/rolename/{rolename}")   //返回用户信息 根据用户名查到数据库里该用户的信息
    public Result find(@PathVariable String rolename) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rolename",rolename);
        return Result.success(roleService.getOne(queryWrapper));
    }

    @PostMapping("/roleMenu/{roleId}") // 绑定角色和菜单的关系 也就是为角色分配菜单
    public Result getRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/menu/{roleId}")  // 返回已选择的菜单id
    public Result getMenuIfo(@PathVariable Integer roleId){
        return Result.success( roleService.getMenu(roleId) );
    }

    @PostMapping("/update") // 新增或插入
    public Boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    @DeleteMapping("/{id}") //删
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    @PostMapping("/del/batch")  //  批量删除
    public Boolean deleteBatch(@RequestBody List<Integer> ids){ // 传入的ids是List 数组型,但获取到的是一整个对象，里面不仅有id 还有其他属性 (multipleSelection)
        return roleService.removeByIds(ids);
    }

    @GetMapping("/list")   //列出所有
    public Result findAll() {
        return Result.success(roleService.list());
    }

    @GetMapping("/{id}") // 传入id 搜索
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/page") // 分页
    public IPage<Role> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String rolename){
        IPage<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        if(!"".equals(rolename)){ // 名字的字符串不为空
            queryWrapper.like("rolename",rolename);
        }
        return roleService.page(page,queryWrapper);
    }
}
