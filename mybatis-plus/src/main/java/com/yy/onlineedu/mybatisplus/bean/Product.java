package com.yy.onlineedu.mybatisplus.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
@Data
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer price;
    @Version
    private Integer version;


}
