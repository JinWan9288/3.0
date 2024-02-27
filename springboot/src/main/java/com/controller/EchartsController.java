package com.controller;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.common.Result;
import com.entity.User;
import com.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    private IUserService userService;
    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" ));
        map.put("y", CollUtil.newArrayList( 820, 932, 901, 934, 1290, 1330, 1320));
        return Result.success(map);
    }
    @GetMapping("/member")
    public Result member(){
        List<User> list = userService.list();
        int s1 = 0; // 第一季度
        int s2 = 0; // 第二季度
        int s3 = 0; // 第三季度
        int s4 = 0; // 第四季度
        for (User user : list){
            Date dateTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(dateTime); //
            switch (quarter) {
                case Q1 : s1 += 1; break;
                case Q2 : s2 += 1; break;
                case Q3 : s3 += 1; break;
                case Q4 : s4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(s1,s2,s3,s4));
    }
}
