package sample.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Date date = new Date();

    static public String getDate() {
        return sdf.format(date);
    }
}
