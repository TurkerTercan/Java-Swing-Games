package com.ooad.hw2.engine;

/**
 * Static class to log everything throughout the gameplay.
 */
public class Logger {
    private static final Logger logger = new Logger();
    private static String singleLog;

    /**
     * Returns the logged string
     * @return last logged string
     */
    public static String getSingleLog() {
        return singleLog;
    }

    /**
     * Sets the last log and prints it
     * @param str new log
     */
    public static void setSingleLog(String str) {
        singleLog = str;
        System.out.println(singleLog);
    }

}
