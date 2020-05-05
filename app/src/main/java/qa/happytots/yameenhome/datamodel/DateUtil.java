package qa.happytots.yameenhome.datamodel;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public String convertToNewFormat(String oldFormat, String newFormat, String date) {
        final SimpleDateFormat ymdFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
        Date oldDate = null;
        try {
            //ymdFormat.setTimeZone(TimeZone.getDefault());
            oldDate = ymdFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        final SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
        return newDateFormat.format(oldDate);
    }
}
