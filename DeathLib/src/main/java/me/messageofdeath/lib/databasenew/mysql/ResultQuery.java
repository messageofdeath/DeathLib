package me.messageofdeath.lib.databasenew.mysql;

import me.messageofdeath.lib.databasenew.IDeathDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ResultQuery implements IDeathDB {

    private transient ResultSet rs;
    private transient DeathMySQLDB db;

    ResultQuery(ResultSet rs, DeathMySQLDB db) {
        this.rs = rs;
        this.db = db;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    public boolean has() {
        try {
            return !rs.wasNull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasNext() {
        try {
            boolean saved = rs.next();
            rs.previous();
            return saved;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean next() {
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            rs.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString(String column, String fallback) {
        try {
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
            if(rs != null) {
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
