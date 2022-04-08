package com.zhong.thread;

/**
 * @date 2022/4/8 16:42
 */
public class ThreadLocal_test4 {

    class Message {
        private String note;

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote() {
            return this.note;
        }
    }

    class MessageConsumer {
        public void print(Message msg) {
            System.out.println(Thread.currentThread().getName() + msg.getNote());
        }
    }


    public void test() {

        new Thread(() -> {
            Message msgA = new Message();
            msgA.setNote("中国矿业大学北京");
            new MessageConsumer().print(msgA);
        }, "学生A").start();

        new Thread(() -> {
            Message msgB = new Message();
            msgB.setNote("清华大学");
            new MessageConsumer().print(msgB);
        }, "学生B").start();
    }

    public static void main(String[] args) {
        ThreadLocal_test4 test4 = new ThreadLocal_test4();
        test4.test();
    }


}
