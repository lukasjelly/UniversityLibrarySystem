/**
 * Custom exception class for when ID is not found in library
 * (probably not required but was created for learning experience)
 */
public class IdException extends Exception{
    public IdException(String message){
        super(message);
    }
}
