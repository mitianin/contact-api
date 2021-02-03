package com.company;

import com.company.action.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

    private List<Action> actionWithToken = new ArrayList<>();
    private List<Action> actionWithNoToken = new ArrayList<>();


   public void addAction (Action action){
       if (action.toString().equals("CLOSE")) {
           actionWithToken.add(action);
           actionWithNoToken.add(action);
           return;
       }

       if (action.needToken()) {
           actionWithToken.add(action);
       } else {
           actionWithNoToken.add(action);
       }
   }

   public void showAction(List<Action> list){
       if (list.isEmpty()) {
           System.out.println("No actions");
           return;
       }

       System.out.println("Choose action:");
       for (int i = 0; i < list.size(); i++) {
           System.out.println(i + 1 + " " + list.get(i));
       }
   }

   public void execute(int optionIndex, List<Action> list) {
       if (optionIndex < 1 || optionIndex > list.size())
           System.out.println("Invalid index. Valid index is from 1 to " + list.size());
       else System.out.println(list.get(optionIndex - 1).doIt());
   }


}
