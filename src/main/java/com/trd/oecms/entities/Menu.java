package com.trd.oecms.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 前端dashboard的菜单栏
 * @author tanruidong
 * @date 2020-04-07 18:16
 */
@Data
@Accessors(chain = true)
public class Menu implements Serializable {
    /**
     * 模块名
     */
    private String name;
    /**
     * 模块路径
     */
    private String path;
    /**
     * 子模块
     */
    private List<Menu> children;

    public Menu(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Menu() {
    }
}
