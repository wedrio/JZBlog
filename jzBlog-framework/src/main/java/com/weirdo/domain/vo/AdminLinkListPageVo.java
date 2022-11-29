package com.weirdo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --13:20
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLinkListPageVo {
    private Long id;
    private String name;
    private String description;
    private String logo;
    private String address;
    private String status;
}
