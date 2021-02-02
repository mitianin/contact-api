package action;

public class Close implements Action {
    @Override
    public String doIt() {
        return "Close action";
    }

    @Override
    public String toString() {
        return "CLOSE";
    }
}
