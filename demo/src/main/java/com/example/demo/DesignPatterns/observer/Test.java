package com.example.demo.DesignPatterns.observer;

/**
 * @author xiaoxu123
 */
public class Test {

    public static void main(String[] args) {

        DevelopTask task = new DevelopTask( "指纹查询证书密钥功能" );
        Personnel personnel1 = new Personnel( "WUZHAO" );
        Personnel personnel2= new Personnel( "YANGCHAOJIE" );

        task.addObserver(personnel1);
        task.addObserver(personnel2);

        //业务逻辑代码
        Question question = new Question();
        question.setUserName("lihelong");
        question.setQuestionContent("证书密钥运维升级");

        task.productQuery(task,question);

    }
}
