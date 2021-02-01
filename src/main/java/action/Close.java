package action;

public class Close implements ActionWithToken, ActionWithNoToken {
    @Override
    public String doIt() {
        return "Close action";
    }

    @Override
    public String toString() {
        return "CLOSE";
    }
}
