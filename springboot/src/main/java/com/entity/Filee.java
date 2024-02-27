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

@Getter
@Setter
@ToString
@Data
@ApiModel(value = "File对象", description = "")
@TableName("file")
public class Filee {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;  // id不应该自增 应该采用雪花算法

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件链接")
    private String url;

    @ApiModelProperty("是否删除 0表示已删除 1表示未删除仍有效")
    private Boolean isDelete;

    @ApiModelProperty("是否禁用 0表示禁用中 1表示未禁用仍有效")
    private Boolean enable;

    @ApiModelProperty("文件Md5")
    private String md5;
}
