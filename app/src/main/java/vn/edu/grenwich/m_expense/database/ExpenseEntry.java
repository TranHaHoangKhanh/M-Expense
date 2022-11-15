package vn.edu.grenwich.m_expense.database;

public class ExpenseEntry {
    public static final String TABLE_NAME = "expense";
    public static final String COL_ID = "id";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_COMMENT = "comment";
    public static final String COL_TYPE = "type";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_TRIPID = "tripId";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_AMOUNT + " INTEGER NOT NULL," +
                    COL_COMMENT + " TEXT NOT NULL," +
                    COL_TYPE + " TEXT NOT NULL," +
                    COL_DATE + " TEXT NOT NULL," +
                    COL_TIME + " TEXT NOT NULL," +
                    COL_TRIPID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + COL_TRIPID + ") " +
                    "REFERENCES " + TripEntry.TABLE_NAME + "(" + TripEntry.COL_ID + "))";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
