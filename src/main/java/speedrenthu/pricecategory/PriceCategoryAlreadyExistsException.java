package speedrenthu.pricecategory;

public class PriceCategoryAlreadyExistsException extends RuntimeException {

    public PriceCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
