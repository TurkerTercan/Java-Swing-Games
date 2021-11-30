package com.ooad.hw2.engine;

/**
 * Static class to log everything throughout the gameplay.
 */
public class Logger {
    private static final Logger logger = new Logger();
    private static String first;
    private static String second;
    private static String third;
    private static String forth;

    /**
     * Returns the logged string
     * @return last logged string
     */
    public static String getFirstLog() {
        return first;
    }
    /**
     * Returns the logged string
     * @return second logged string
     */
    public static String getSecondLog() {
        return second;
    }
    /**
     * Returns the logged string
     * @return third logged string
     */
    public static String getThirdLog() {
        return third;
    }
    /**
     * Returns the logged string
     * @return forth logged string
     */
    public static String getForthLog() {
        return forth;
    }

    /**
     * Sets the last log and prints it
     * @param str new log
     */
    public static void setSingleLog(String str) {
        if (first == null)
            first = str;
        else if (second == null) {
            second = first;
            first = str;
        }
        else if (third == null) {
            third = second;
            second = first;
            first = str;
        }
        else {
            forth = third;
            third = second;
            second = first;
            first = str;
        }
    }

}
