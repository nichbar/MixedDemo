package work.nich.mixeddemo.github.repository;

public class NetworkState {

    @Status
    private int mStatus;

    private String mMessage;

    private NetworkState(int status, String message) {
        mStatus = status;
        mMessage = message;
    }

    private NetworkState(int status) {
        mStatus = status;
    }

    public static NetworkState LOADED = new NetworkState(Status.SUCCESS);
    public static NetworkState LOADING = new NetworkState(Status.RUNNING);

    public static NetworkState error(String message) {
        return new NetworkState(Status.FAILED, message == null ? "unknown error" : message);
    }

    public int getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }
}
