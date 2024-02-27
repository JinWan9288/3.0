package com.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.entity.RoleMenu;
import com.service.IRoleMenuService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    @Resource
    private IRoleMenuService roleMenuService;

    @PostMapping("/update") // 新增或插入
    public Boolean save(@RequestBody RoleMenu roleMenu) {
        return roleMenuService.saveOrUpdate(roleMenu);
    }

    @DeleteMapping("/{id}") //删
    public Boolean delete(@PathVariable Integer id) {
        return roleMenuService.removeById(id);
    }

    @PostMapping("/del/batch")  //  批量删除
    public Boolean deleteBatch(@RequestBody List<Integer> ids){ // 传入的ids是List 数组型,但获取到的是一整个对象，里面不仅有id 还有其他属性 (multipleSelection)
        return roleMenuService.removeByIds(ids);
    }

    @GetMapping("/list")   //列出所有
    public List<RoleMenu> findAll() {
        return roleMenuService.list();
    }

    @GetMapping("/{id}") // 传入id 搜索
    public RoleMenu findOne(@PathVariable Integer id) {
        return roleMenuService.getById(id);
    }

    @GetMapping("/page") // 分页
    public IPage<RoleMenu> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String dictname){
        IPage<RoleMenu> page = new Page<>(pageNum,pageSize);
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();

        if(!"".equals(dictname)){ // 名字的字符串不为空
            queryWrapper.like("dictname",dictname);
        }
        return roleMenuService.page(page,queryWrapper);
    }
}
