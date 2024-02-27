package com.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author root
 * @since 2024-02-02
 */
@Getter
@Setter
@ToString
@ApiModel(value = "User对象", description = "")
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("昵称")
      private String nickname;

      @ApiModelProperty("密码")
      private String passwords;

      @ApiModelProperty("年龄")
      private Integer age;

      @ApiModelProperty("性别")
      private Integer sex;

      @ApiModelProperty("电话号码")
      private String phone;

      @ApiModelProperty("地址")
      private String address;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("0超级管理员 1管理员 2普通账号")
      private String role;

      @ApiModelProperty(" 是否有效 Y有效 其他无效 ")
      private String isvalid;  // 注意这里代码生成器会写成isValid 实际上是is_valid

      @ApiModelProperty("创建时间")
      private Date createTime;

      @ApiModelProperty("头像")
      private String avatarUrl;

}
