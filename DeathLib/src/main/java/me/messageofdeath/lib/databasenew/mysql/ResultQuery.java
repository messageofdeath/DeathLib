package me.messageofdeath.lib.databasenew.mysql;

import me.messageofdeath.lib.databasenew.IDeathDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ResultQuery implements IDeathDB {

    private transient ResultSet rs;

    ResultQuery(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public boolean contains(String ignore) {
        try {
            return rs != null && rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getString(String column, String fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getString(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public byte getByte(String column, byte fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getByte(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public short getShort(String column, short fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getShort(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public int getInteger(String column, int fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getInt(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public long getLong(String column, long fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getLong(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public float getFloat(String column, float fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getFloat(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public double getDouble(String column, double fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getDouble(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public boolean getBoolean(String column, boolean fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getBoolean(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public Object getObject(String column, Object fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getObject(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public Time getTime(String column, Time fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getTime(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public Date getDate(String column, Date fallback) {
        try {
            if(rs != null && rs.first()) {
                return rs.getDate(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public ArrayList<Object> getObjectArray(String column, ArrayList<Object> fallback) {
        try {
            if(rs != null && rs.first()) {
                ArrayList<Object> list = new ArrayList<>();
                while(rs.next()) {
                    list.add(rs.getObject(column));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public ArrayList<String> getStringArray(String column, ArrayList<String> fallback) {
        try {
            if(rs != null && rs.first()) {
                ArrayList<String> list = new ArrayList<>();
                while(rs.next()) {
                    list.add(rs.getString(column));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fallback;
    }

    @Override
    public int getOccurrences(String column) {
        try {
            if(rs != null && rs.first()) {
                int i = 0;
                while(rs.next()) {
                    i++;
                }
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
