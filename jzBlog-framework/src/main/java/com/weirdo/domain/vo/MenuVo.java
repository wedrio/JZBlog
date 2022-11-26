package com.weirdo.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.weirdo.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: xiaoli
 * @Date: 2022/11/26 --13:05
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {
    //菜单ID
    @TableId
    private Long id;
    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
    //创建时间
    private Date createTime;


    @TableField(exist = false)//该字段在表中不存在
    private List<MenuVo> children = new ArrayList<>();
}
