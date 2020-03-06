package sample.Utils;


import sample.Enum.Status;

public class StatusUtils {

    public static Status fromInt(int status) {
        switch (status) {
            case 1: {
                return Status.SENT;
            }
            case 2: {
                return Status.PENDING;
            }
            case 3: {
                return Status.APPROVED;
            }
            case 4: {
                return Status.DELIVERED;
            }
            default:
                return Status.SENT;
        }
    }

    public static int fromStatus(Status sts) {
        switch (sts) {
            case SENT: return 1;
            case PENDING: return 2;
            case APPROVED: return 3;
            case DELIVERED: return 4;
            default: return -1;
        }
    }
}
