package com.trd.oecms.entities;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanruidong
 * @date 2020-03-31 11:43
 */
@Data
@Accessors(chain = true)
public class Student {
    private Integer id;
    private String name;
    private Integer number;
    private String className;
}
