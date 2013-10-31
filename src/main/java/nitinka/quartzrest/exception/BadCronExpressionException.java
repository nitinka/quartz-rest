package nitinka.quartzrest.exception;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 23/7/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BadCronExpressionException extends Exception{
    public BadCronExpressionException(String message) {
        super(message);
    }
}
