package com.weirdo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xiaoli
 * @Date: 2022/11/27 --21:52
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusDto {
    private Long id;
    private String status;
}
