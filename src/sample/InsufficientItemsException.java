package sample;
public class InsufficientItemsException extends Exception {
    public InsufficientItemsException(String msg) {
        super(msg);
    }
}