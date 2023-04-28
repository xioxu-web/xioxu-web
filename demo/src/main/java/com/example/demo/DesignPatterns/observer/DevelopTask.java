package com.example.demo.DesignPatterns.observer;

import lombok.Getter;

import java.util.Observable;

/**
 * @author xiaoxu123
 */
@Getter
public class DevelopTask extends Observable {

    private String DevelopTaskName;

    public DevelopTask(String DevelopTaskName) {
        this.DevelopTaskName = DevelopTaskName;
    }
     
    public void productQuery(DevelopTask task,Question question){
        System.out.println(question.getUserName()+"在"+task.getDevelopTaskName()+"提交了一个"+"aone");
        setChanged();
        notifyObservers(question);
    }
}
