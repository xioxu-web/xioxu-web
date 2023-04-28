package com.example.demo.DesignPatterns.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author xiaoxu123
 */
public class Personnel implements Observer {

    private String PersonnelName;

    public Personnel(String PersonnelName){
      this.PersonnelName=PersonnelName;
    }

    @Override
    public void update(Observable o, Object arg) {
       DevelopTask task=(DevelopTask)o;
       Question question=(Question)arg;

        System.out.println("员工"+PersonnelName+task.getDevelopTaskName()+"需求接受到一个"+question.getUserName()+"提交了一个 aone");


    }
}
