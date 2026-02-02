package kevin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    // Accepts: 2026-11-01 1600
    public static final DateTimeFormatter INPUT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static final DateTimeFormatter OUTPUT =
            DateTimeFormatter.ofPattern("d MMM yyyy h a");

    public static LocalDateTime parse(String s) {
        return LocalDateTime.parse(s, INPUT);
    }

    public static String format(LocalDateTime dt) {
        // Convert AM/PM to am/pm to match your desired format
        return dt.format(OUTPUT).replace("AM", "am").replace("PM", "pm");
    }
}
