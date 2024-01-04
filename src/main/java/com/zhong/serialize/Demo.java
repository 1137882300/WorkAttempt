package com.zhong.serialize;

import java.io.*;

/**
 * @author: juzi
 * @date: 2024/1/4
 * @desc: 序列化：
 * Externalizable 接口提供了更高的序列化控制能力，可以在序列化和反序列化过程中对对象进行自定义的处理，如对一些敏感信息进行加密和解密。
 */
public class Demo {

    static class ClazzA implements Serializable {
        private String name;
        private int age;
    }

    static class ClazzB implements Externalizable {
        private String name;
        private int age;

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(name);
            out.writeInt(age);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            age = in.readInt();
        }
    }

}
