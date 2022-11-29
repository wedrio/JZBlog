package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/29 --14:25
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLinkStatusDto {
    private Long id;
    private String status;
}
