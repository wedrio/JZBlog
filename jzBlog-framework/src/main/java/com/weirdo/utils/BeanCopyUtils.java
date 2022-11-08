package com.weirdo.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiaoli
 * @Date: 2022/11/6 --21:12
 * @Description:
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){
    }

    /**
     * 拷贝bean
     * @param o
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object o,Class<T> clazz) {
        T result = null;
        try {
            //实例化目标对象
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(o, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 拷贝beanList
     * @param list
     * @param clazz
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> List<K> BeanList(List<V> list,Class<K> clazz){
        return list.stream().map(o->copyBean(o,clazz)).collect(Collectors.toList());
    }
}
