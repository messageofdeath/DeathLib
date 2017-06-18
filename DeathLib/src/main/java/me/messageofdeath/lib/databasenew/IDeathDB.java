package me.messageofdeath.lib.databasenew;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public interface IDeathDB {

    /**
     * Get a String
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @return whatever is there or if not, returns fallback
     */
    String getString(String where, String fallback);

    /**
     * Get a Byte
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    byte getByte(String where, byte fallback);

    /**
     * Get a Short
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    short getShort(String where, short fallback);

    /**
     * Get a Integer
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    int getInteger(String where, int fallback);

    /**
     * Get a Long
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    long getLong(String where, long fallback);

    /**
     * Get a Float
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    float getFloat(String where, float fallback);

    /**
     * Get a Double
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    double getDouble(String where, double fallback);

    /**
     * Get a Boolean
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    boolean getBoolean(String where, boolean fallback);

    /**
     * Get a Object
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    Object getObject(String where, Object fallback);

    /**
     * Get a Time
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    Time getTime(String where, Time fallback);

    /**
     * Get a Date
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    Date getDate(String where, Date fallback);

    /**
     * Get a Object List
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    ArrayList<Object> getObjectArray(String where, ArrayList<Object> fallback);

    /**
     * Get a String List
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @param fallback - Default fallback if it doesn't exist
     * @return whatever is there or if not, returns fallback
     */
    ArrayList<String> getStringArray(String where, ArrayList<String> fallback);

    /**
     * Get the amount of occurences of 'where'
     * @param where - YAML format - location in file
     *        where - MySQL format - Use the column needed
     * @return whatever is there or if not, returns fallback
     */
    int getOccurrences(String where);
}
