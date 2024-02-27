package com.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.entity.Filee;
import com.entity.User;
import com.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  文件上传控制器  上传 下载
 * </p>
 * @author root
 * @since 2024-02-08
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}") // 引入文件下载地址
    private String UploadPath;
    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize(); // bigint 就是java里的long  tinyint是boolean
        String uuid = IdUtil.fastSimpleUUID();
        String FinalUid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(UploadPath + FinalUid);
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()){ // 判断配置的文件目录是否存在 不存在就创建一个新的文件目录
            parentFile.mkdirs(); // 创建
        }
        String url;
        file.transferTo(uploadFile);
        String md5 = SecureUtil.md5(uploadFile);
        Filee dbFile = getFileBymd5(md5);
        if (dbFile != null){
            url = dbFile.getUrl();
            uploadFile.delete();
        }else {
            url = "http://localhost:9090/file/" + FinalUid;
        }
        // 插入数据库
        Filee FinalFile = new Filee();
        FinalFile.setFilename(originalFilename);
        FinalFile.setType(type);
        FinalFile.setSize(size/1024);
        FinalFile.setUrl(url);
        FinalFile.setMd5(md5);
        fileMapper.insert(FinalFile); // 存储数据库
        return url;
    }

    @GetMapping("/{uuid}")  // 根据唯一的标识码获取文件
    public void download(@PathVariable String uuid, HttpServletResponse response) throws IOException{
        File FinalUploadFile = new File(UploadPath + uuid); // 文件名 也就是设置输出流的格式
        ServletOutputStream servletOutputStream = response.getOutputStream(); // 获取输出流 通过输出流返回文件 通过输出流返回文件

        // 设置输出流返回格式
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(uuid,"UTF-8"));
        response.setContentType("application/octet-stream");

//        byte[] bytes = FileUtil.readBytes(basePath + FinalUid); // 文件路径读取文件字节流

        servletOutputStream.write(FileUtil.readBytes(FinalUploadFile)); // 通过文件的路径读取文件字节流
        servletOutputStream.flush(); // 清空缓冲区
        servletOutputStream.close();
    }

    private Filee getFileBymd5(String md5){ // 根据文件md5查询文件
        QueryWrapper<Filee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Filee> fileeList = fileMapper.selectList(queryWrapper);
        return fileeList.size() == 0 ? null : fileeList.get(0); // 如果查询不到相同文件返回空 查到相同文件就取列表中的首个元素
    }

    @PostMapping("/update") // 新增或插入
    public Result update(@RequestBody Filee files) {
        return Result.success(fileMapper.updateById(files));
    }

    @DeleteMapping("/{id}") //删
    public Result delete(@PathVariable Integer id) {
        Filee files = fileMapper.selectById(id);
        files.setIsDelete(false);
        fileMapper.updateById(files);
        return Result.success();
    }

    @PostMapping("/del/batch")  //  批量删除
    public Result deleteBatch(@RequestBody List<Integer> ids){ // 传入的ids是List 数组型,但获取到的是一整个对象，里面不仅有id 还有其他属性 (multipleSelection)
        QueryWrapper<Filee> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);  // select * from file where id in (id,id,id ...)
        List<Filee> files = fileMapper.selectList(queryWrapper);

        for (Filee file : files){ //
            file.setIsDelete(false);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    @GetMapping("/page") // 分页
    public Result findPageMbP(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String filename){
        QueryWrapper<Filee> queryWrapper = new QueryWrapper<>();

        // 查询未被删除的文件
        queryWrapper.eq("is_delete", true);
//        queryWrapper.orderByDesc("id"); // 逆向展示文件，以便更好看见添加的文件
        if(!"".equals(filename)){ // 名字的字符串不为空
            queryWrapper.like("filename",filename);
        }

        return Result.success( fileMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper) );
    }
}