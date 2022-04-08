package com.zhong.thread;

/**
 * @date 2022/4/8 16:51
 */
public class ThreadLocal_test5 {

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
        public void print() {
            System.out.println(Thread.currentThread().getName() + MyUtil.get().getNote());
        }
    }

    static class MyUtil {
        private static ThreadLocal<Message> threadLocal = new ThreadLocal<>();

        public static void set(Message msg) {
            threadLocal.set(msg);
        }

        public static Message get() {
            return threadLocal.get();
        }
    }

    public void test() {

        new Thread(() -> {
            Message msgA = new Message();
            msgA.setNote("中国矿业大学");
            MyUtil.set(msgA);
            new MessageConsumer().print();
        }, "学生A").start();

        new Thread(() -> {
            Message msgB = new Message();
            msgB.setNote("清华大学");
            MyUtil.set(msgB);
            new MessageConsumer().print();
        }, "学生B").start();

    }

    public static void main(String[] args) {
        ThreadLocal_test5 test5 = new ThreadLocal_test5();
        test5.test();
    }


}
