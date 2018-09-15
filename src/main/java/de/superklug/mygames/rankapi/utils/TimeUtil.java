package de.superklug.mygames.rankapi.utils;

public class TimeUtil {
    
    public static long getTimeInMillis(String timeString) {
        long time = 0;
        if(timeString.endsWith("s")) {
            try {
                long time2 = Long.valueOf(timeString.replace("s", ""));
                time = time2 * 1000;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        if(timeString.endsWith("m")) {
            try {
                long time2 = Long.valueOf(timeString.replace("m", ""));
                time = time2 * 1000 * 60;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        if(timeString.endsWith("h")) {
            try {
                long time2 = Long.valueOf(timeString.replace("h", ""));
                time = time2 * 1000 * 60 * 60;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        if(timeString.endsWith("d")) {
            try {
                long time2 = Long.valueOf(timeString.replace("d", ""));
                time = time2 * 1000 * 60 * 60 * 24;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return time;
    }

}
