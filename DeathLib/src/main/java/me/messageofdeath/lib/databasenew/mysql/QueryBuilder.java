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
    public Select select() {
        bui.append("SELECT");
        return new Select(bui, table);
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

    public class Select {

        private String table;
        private StringBuilder bui;
        private transient boolean first;

        Select(StringBuilder bui, String table) {
            first = true;
            this.table = table;
            this.bui = bui;
        }

        public Select column(String column) {
            if(!first) {
                bui.append(", ").append(column);
            }else{
                bui.append(" ").append(column);
                first = false;
            }
            return this;
        }

        public String limit(int rows) {
            return bui.append(" LIMIT ").append(rows).toString();
        }

        public Where toWhere() {
            if(first) {
                throw new IllegalStateException("You need a minimum of at least 1 column to select.");
            }
            return new Where(bui.append(" FROM ").append(table));
        }

        public Where allColumns() {
            if(!first) {
                throw new IllegalStateException("You cannot specify columns, then request all columns.");
            }
            return new Where(bui.append(" * FROM ").append(table));
        }

        public String build() {
            if(first) {
                throw new IllegalStateException("You need a minimum of at least 1 column to select.");
            }
            return bui.append(" FROM ").append(table).toString();
        }

        public String buildLimit(int rows) {
            if(first) {
                throw new IllegalStateException("You need a minimum of at least 1 column to select.");
            }
            return bui.append(" FROM ").append(table).append(" LIMIT ").append(rows).toString();
        }

        public String buildAllColumns() {
            if (!first) {
                throw new IllegalStateException("You cannot specify columns, then request all columns.");
            }
            return bui.append(" * FROM ").append(table).toString();
        }

        public String buildAllColumnsLimit(int rows) {
            if (!first) {
                throw new IllegalStateException("You cannot specify columns, then request all columns.");
            }
            return bui.append(" * FROM ").append(table).append(" LIMIT ").append(rows).toString();
        }
    }

    public class CreateTable {

        private StringBuilder bui;
        private transient boolean first;

        CreateTable(StringBuilder bui) {
            this.bui = bui;
            bui.append(" (");
            first = true;
        }

        public CreateTable column(Column column) {
            if(!first) {
                bui.append(", ").append(column.getColumn());
            }else{
                bui.append(column.getColumn());
                first = false;
            }
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

        public Where and(String column, Object value) {
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

        public String limit(int rows) {
            return bui.append(" LIMIT ").append(rows).toString();
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

        public Set and(String column, Object value) {
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

        private StringBuilder bui;
        private transient boolean first;
        private transient StringBuilder columns, values;

        Insert(StringBuilder bui) {
            this.bui = bui;
            columns = new StringBuilder();
            values = new StringBuilder();
            first = true;
        }

        public Insert insert(String column, Object value) {
            if(!first) {
                columns.append(", ").append(column);
                values.append(", ");
            }else{
                columns.append(column);
                first = false;
            }
            if(value instanceof String) {
                values.append("'").append(value).append("'");
            }else{
                values.append(value);
            }
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
