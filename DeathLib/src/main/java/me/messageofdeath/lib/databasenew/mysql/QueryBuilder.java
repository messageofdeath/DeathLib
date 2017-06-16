package me.messageofdeath.lib.databasenew.mysql;

public class QueryBuilder {

    private String table;
    private StringBuilder bui;

    public QueryBuilder(String table) {
        this.table = table;
        bui = new StringBuilder();
    }

    /**
     * Requires @value to be Table name
     * @return Where - To set parameters of getting values
     */
    public Where selectWhere() {
        bui.append("SELECT * FROM ").append(table);
        return new Where(bui);
    }

    /**
     * Requires @value to be Table name
     * @return finished query of selecting all values in table
     */
    public String selectAll() {
        bui.append("SELECT * FROM ").append(table);
        return bui.toString();
    }

    /**
     * Requires @value to be Table Name
     * @return Set - To set the column to the value
     */
    public Set update() {
        bui.append("UPDATE ").append(table);
        return new Set(bui);
    }

    /**
     * Requires @value to be Table Name
     * @return Where - To define what to delete
     */
    public Where delete() {
        bui.append("DELETE FROM ").append(table);
        return new Where(bui);
    }

    /**
     * Requires @value to be Table Name
     * @return Insert - Helper for inserting column/values into table
     */
    public Insert insert() {
        bui.append("INSERT INTO ").append(table);
        return new Insert(bui);
    }

    /**
     * Requires @value to be Table Name
     * @return Table - Helper for creating table
     */
    public CreateTable createTable() {
        bui.append("CREATE TABLE ").append(table);
        return new CreateTable(bui);
    }

    /**
     * Requires @value to be Table Name
     * @return AlterTable - Helper for altering various thing about the table
     */
    public AlterTable alterTable() {
        bui.append("ALTER TABLE ").append(table);
        return new AlterTable(bui);
    }

    public class CreateTable {

        private int i;
        private StringBuilder bui;

        CreateTable(StringBuilder bui) {
            this.bui = bui;
            bui.append(" (");
            i = 0;
        }

        public CreateTable column(Column column) {
            if(i > 0) {
                bui.append(", ").append(column.getColumn());
            }else{
                bui.append(column.getColumn());
            }
            i++;
            return this;
        }

        public String build() {
            return bui.append(")").toString();
        }
    }

    public class Where {

        private StringBuilder bui;

        Where(StringBuilder bui) {
            this.bui = bui;
            this.bui.append(" WHERE");
        }

        public Where andWhere(String column, Object value) {
            bui.append(" AND");
            return where(column, value);
        }

        public Where where(String column, Object value) {
            bui.append(" ").append(column);
            if(value instanceof String) {
                bui.append(" = '").append(value).append("'");
            }else{
                bui.append(" = ").append(value);
            }
            return this;
        }

        public String build() {
            return bui.toString();
        }
    }

    public class Set {

        private StringBuilder bui;

        Set(StringBuilder bui) {
            this.bui = bui;
            this.bui.append(" SET");
        }

        public Set andSet(String column, Object value) {
            bui.append(" AND");
            return set(column, value);
        }

        public Set set(String column, Object value) {
            bui.append(" ").append(column);
            if(value instanceof String) {
                bui.append(" = '").append(value).append("'");
            }else{
                bui.append(" = ").append(value);
            }
            return this;
        }

        public Where toWhere() {
            return new Where(bui);
        }
    }

    public class Insert {

        private int i;
        private StringBuilder bui, columns, values;

        Insert(StringBuilder bui) {
            this.bui = bui;
            columns = new StringBuilder();
            values = new StringBuilder();
            i = 0;
        }

        public Insert insert(String column, Object value) {
            if(i > 0) {
                columns.append(", ").append(column);
                values.append(", ");
            }else{
                columns.append(column);
            }
            if(value instanceof String) {
                values.append("'").append(value).append("'");
            }else{
                values.append(value);
            }
            i++;
            return this;
        }

        public String build() {
            return bui.append(" (").append(columns.toString()).append(") VALUES (").append(values.toString()).append(")").toString();
        }
    }

    public class AlterTable {

        private StringBuilder bui;

        AlterTable(StringBuilder bui) {
            this.bui = bui;
        }

        /**
         * Query for adding a column
         * @param column - Requires a dataType
         * @return a query for adding a column
         */
        public String addColumn(Column column) {
            return bui.append(" ADD ").append(column.getColumn()).toString();
        }

        /**
         * Query for dropping a column
         * @param column - No dataType required
         * @return a query for dropping a column
         */
        public String dropColumn(String column) {
            return bui.append(" DROP COLUMN ").append(column).toString();
        }

        /**
         * Query for modifying the datatype of a column
         * @param column - Requires a dataType
         * @return a query for modifying the datatype of a column
         */
        public String modifyColumnDataType(Column column) {
            return bui.append(" MODIFY COLUMN ").append(column.getColumn()).toString();
        }
    }
}
