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
 * @since 2024-02-16
 */
@Getter
@Setter
@ToString
@ApiModel(value = "Dict对象", description = "")
@Data
@TableName("dict")
public class Dict {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "dictname")
    private String dictname;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("内容")
    private String value;
}
