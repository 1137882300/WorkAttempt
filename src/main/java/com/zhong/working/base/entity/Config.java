package com.zhong.working.base.entity;

/**
 * @author: juzi
 * @date: 2023/4/14
 * @desc:
 */
public class Config {

    //参数类型
    enum ParameterType {
        baseType(1),
        complexType(2),
        baseList(3),
        complexList(4),

        ;
        final int value;

        ParameterType(int value) {
            this.value = value;
        }
    }


    //具体类型
    enum ConcreteType {
        String(1),
        Int(2),
        Long(3),
        Float(4),
        Double(5),
        Boolean(6),


        Object(7),
        Map(9),
        ;
        final int value;

        ConcreteType(int value) {
            this.value = value;
        }
    }

}
