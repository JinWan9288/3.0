package com.entity;
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
 * @since 2024-02-17
 */
@Getter
@Setter
@ToString
@ApiModel(value = "roleMenu对象", description = "")
@Data
@TableName("role_menu")
public class RoleMenu {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "roleId")
    private Integer roleId;

//    @TableId(value = "menuId")
    @ApiModelProperty("菜单id")
    private Integer menuId;
}
