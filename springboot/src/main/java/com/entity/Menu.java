package com.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

/**
 * @author root
 * @since 2024-02-14
 */
@Getter
@Setter
@ToString
@ApiModel(value = "Menu对象", description = "")
@Data
@TableName("menu")
public class Menu {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父级id")
    private Integer pid;

    @ApiModelProperty("名称")
    private String menuname;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("菜单路由页面路径")
    private String pagePath;

    @TableField(exist = false)  // 在数据库中不存在 但是在前端显示是树形结构 需要显示树型关系 用children承接子级
    private List<Menu> children;

}
