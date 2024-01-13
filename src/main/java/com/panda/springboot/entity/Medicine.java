package com.panda.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("medicine")
@ApiModel(value = "Medicine对象", description = "")
public class Medicine {
    @ApiModelProperty("药品编号")
    @TableId(value = "药品编号", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("药品名称")
    private String medicineName;

    @ApiModelProperty("规格")
    private String medicineSpec;

    @ApiModelProperty("生产厂家")
    private String manufacturer;

    @ApiModelProperty("有效期")
    private String expiryDate;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("适应症")
    private String indications;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("禁忌症")
    private String contraindications;
}
