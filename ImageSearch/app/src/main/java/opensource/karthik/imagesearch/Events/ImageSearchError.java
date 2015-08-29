package opensource.karthik.imagesearch.Events;

public class ImageSearchError {
    private Exception exception;

    public ImageSearchError(Exception ex) {
        exception = ex;
    }

    public Exception getException() {
        return  exception;
    }
}
