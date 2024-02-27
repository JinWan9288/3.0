package com.controller;
import cn.hutool.core.annotation.Alias;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import com.Utils.TokenUtils;
import com.common.Constants;
import com.common.Result;
import com.controller.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import org.springframework.web.bind.annotation.*;
import com.service.IUserService;
import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author root
 * @since 2024-02-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String passwords = userDTO.getPasswords();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(passwords)){ // hutool isBlank 首先整个字符不能为空, 然后是字符串的长度也不为0,  空格字符串是非空
            return Result.error(Constants.CODE_400,"参数错误"); // 有一个空就false 有一个没填就不需要去数据库查询了
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String passwords = userDTO.getPasswords();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(passwords)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        User dto = userService.register(userDTO);
        return Result.success(dto);
    }

    @GetMapping("/username/{username}")   //返回用户信息 根据用户名查到数据库里该用户的信息
    public Result find(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @GetMapping("/test")  // 测试
    public String hello(){
        return "hello 测试";
    }

    @PostMapping // 新增或插入
    public Boolean save(@RequestBody User user) {
            return userService.saveOrUpdate(user);
            }

    @DeleteMapping("/{id}") //删
    public Boolean delete(@PathVariable Integer id) {
            return userService.removeById(id);
            }

    @PostMapping("/del/batch")  //  批量删除
    public Boolean deleteBatch(@RequestBody List<Integer> ids){ // 传入的ids是List 数组型,但获取到的是一整个对象，里面不仅有id 还有其他属性 (multipleSelection)
            return userService.removeByIds(ids);
            }

    @GetMapping("/list")   //列出所有
    public List<User> findAll() {
            return userService.list();
            }

    @GetMapping("/{id}") // 传入id 搜索
    public User findOne(@PathVariable Integer id) {
            return userService.getById(id);
            }

    @GetMapping("/pageMbP") // 分页
    public IPage<User> findPageMbP(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username, // defaultValue 默认值 可以不输其中几个值,单独查询
                                   @RequestParam(defaultValue = "") String address,
                                   @RequestParam(defaultValue = "") String email){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if(!"".equals(username)){ // 名字的字符串不为空
            queryWrapper.like("username",username);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }

        // 获取当前登录用户的信息
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("当前登录用户 ===========================" + currentUser.getUsername());
        System.out.println("当前登录用户 ===========================" + currentUser.getPasswords());
        System.out.println("当前登录用户 ===========================" + currentUser.getAddress());
        System.out.println("当前登录用户 ===========================" + currentUser.getNickname());
        System.out.println("当前登录用户 ===========================" + currentUser.getEmail());


        return userService.page(page,queryWrapper);
    }

    @GetMapping("/export") // 导出
    public void export(HttpServletResponse response) throws Exception{ // 抛出例外 抛出
        List<User> list = userService.list(); // 先从数据库中查询到所有数据
        ExcelWriter writer = ExcelUtil.getWriter(true);  // 在内存操作, 写出到浏览器
        // 自定义标题别名 否则会是字段的英文原名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("phone","电话号码");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("email","电子邮箱");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("avatarUrl","头像");

        writer.write(list,true); // 通过write方法将list对象写至writer 也就是ExcelWriter中  写出

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); // 内容的类型
        String filename = URLEncoder.encode("用户信息","UTF-8");   // 将文件名称用URLEncorder重新编码
        response.setHeader("Content-Disposition","attachment;filename"+filename+".xlsx");

        ServletOutputStream outStream = response.getOutputStream(); // response通过getOutputStream方法获取到输出流 ,
        writer.flush(outStream,true); // 把writer里的流刷新
        outStream.close();
        writer.close();

        // 先获取数据, 在writer里写数据, 写好数据的writer刷新到数据流里去
    }
    @Alias("username")
    @PostMapping("/import")  // 导入
    public Boolean importt(MultipartFile file) throws Exception{ // 前端获取文件对象,将文件转化为文件对象
        InputStream inputStream = file.getInputStream();    // 从文件对象中获取到inputStream流
        ExcelReader reader = ExcelUtil.getReader(inputStream); // 把stream流放到reader中
     // List<User> list = reader.readAll(User.class); // 读取reader里的泛型
                                                    //   User.class将流转化为泛型, 再用list接收 ReaderAll接收的数据只能是英文的,中文的会为 null
                                                    //   解决方法1 : 将export中的addHeaderAlias增加别名的方法注释掉 名字就显示英文
                                                    //   解决方法2 :  reader.addHeaderAlias("用户名","username") 反转传入的字段
                                                    //   解决方法3 : 使用read方法 指定了导入开始的行 从第二行开始
//      List<User> list = reader.read(1,2,User.class);
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("昵称","nickname");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("电话号码","phone");
        reader.addHeaderAlias("地址","address");
        reader.addHeaderAlias("电子邮箱","email");
        reader.addHeaderAlias("创建时间","createTime");
        reader.addHeaderAlias("头像","avatarUrl");
        List<User> list = reader.readAll(User.class);
        System.out.println(list);
        userService.saveBatch(list);// 将获取到的数据插入数据库中
        return true;
    }

}