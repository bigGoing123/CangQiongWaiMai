package com.sky.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysFile {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("是否禁用链接")
    private Boolean enable;
}
