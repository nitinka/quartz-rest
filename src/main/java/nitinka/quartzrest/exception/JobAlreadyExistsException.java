package nitinka.quartzrest.exception;

/**
 * Created with IntelliJ IDEA.
 * User: nitinka
 * Date: 21/7/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobAlreadyExistsException extends Exception {
    public JobAlreadyExistsException(String message) {
        super(message);
    }
}
