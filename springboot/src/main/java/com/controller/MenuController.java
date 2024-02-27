package com.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.Constants;
import com.common.Result;
import com.entity.Dict;
import com.entity.Menu;
import com.mapper.DictMapper;
import com.service.IMenuService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @GetMapping("/menuname/{menuname}")   //返回用户信息 根据用户名查到数据库里该用户的信息
    public Result find(@PathVariable String menuname) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menuname",menuname);
        return Result.success(menuService.getOne(queryWrapper));
    }


    @Resource
    private DictMapper dictMapper;

    @GetMapping("/icon")
    public Result getIcon(){ // 查到所有图标
        return Result.success(dictMapper.selectList(null));
    }

    @GetMapping("/icons")
    public Result getIcons(){ // 按类型查图标
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(null));
    }


    @PostMapping("/update") // 新增或插入
    public Boolean save(@RequestBody Menu menu) {
        return menuService.saveOrUpdate(menu);
    }

    @DeleteMapping("/{id}") //删
    public Boolean delete(@PathVariable Integer id) {
        return menuService.removeById(id);
    }

    @PostMapping("/del/batch")  //  批量删除
    public Boolean deleteBatch(@RequestBody List<Integer> ids){ // 传入的ids是List 数组型,但获取到的是一整个对象，里面不仅有id 还有其他属性 (multipleSelection)
        return menuService.removeByIds(ids);
    }

    @GetMapping("/list")   //列出所有
    public Result findAll(@RequestParam(defaultValue = "") String menuname) {
        return Result.success(menuService.findMenus(menuname));
    }

    @GetMapping("/{id}") // 传入id 搜索
    public Menu findOne(@PathVariable Integer id) {
        return menuService.getById(id);
    }

}