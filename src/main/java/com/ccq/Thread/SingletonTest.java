package com.ccq.Thread;

/**
 * @author Chaoqun Cheng
 * @date 2021-07-2021/7/10-16:37
 */

public class SingletonTest {

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        t1.start();
        t2.start();
        t3.start();
    }
}

class MyObject{
    private static MyObject myObject;
    private MyObject(){}
    static{
        myObject = new MyObject();
    }
    public static MyObject getInstance(){
        try{
            if(myObject==null){
                Thread.sleep(2000);
                // 用类做锁
                synchronized (MyObject.class){
                    // DCL双检查机制
                    if(myObject==null){
                        myObject = new MyObject();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return myObject;
    }

}


class MyThread extends Thread{

    @Override
    public void run(){
        System.out.println(MyObject.getInstance().hashCode());
    }
}


