package com.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author root
 * @since 2024-02-14
 */
@Getter
@Setter
@ToString
@ApiModel(value = "Role对象", description = "")
@Data
@TableName("user_role")
public class Role {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("唯一标识")
    private String flag;

    @ApiModelProperty("名称")
    private String rolename;

    @ApiModelProperty("描述")
    private String description;
}
