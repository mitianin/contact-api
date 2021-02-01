import action.Action;
import lombok.Data;
import action.ActionWithNoToken;
import action.ActionWithToken;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

    private List<ActionWithToken> actionWithToken = new ArrayList<>();
    private List<ActionWithNoToken> actionWithNoToken = new ArrayList<>();


   public void addToMenuActionsWithToken(ActionWithToken action){
       actionWithToken.add(action);
   }

   public void addToActionWithNoToken (ActionWithNoToken action){
       actionWithNoToken.add(action);
   }

   public void showAction(List list){
       if (list.isEmpty()) {
           System.out.println("No actions");
           return;
       }

       System.out.println("Choose an action:");
       for (int i = 0; i < list.size(); i++) {
           System.out.println(i + 1 + " " + list.get(i));
       }
   }

   public void execute1(int optionIndex, List<ActionWithNoToken> list) {
       if (optionIndex < 1 || optionIndex > list.size())
           System.out.println("Invalid index. Valid index is from 1 to " + list.size());
       else System.out.println(list.get(optionIndex - 1).doIt());
   }

    public void execute2(int optionIndex, List<ActionWithToken> list) {
        if (optionIndex < 1 || optionIndex > list.size())
            System.out.println("Invalid index. Valid index is from 1 to " + list.size());
        else System.out.println(list.get(optionIndex - 1).doIt());
    }

}
