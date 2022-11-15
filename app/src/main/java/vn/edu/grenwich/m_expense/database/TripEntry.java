package vn.edu.grenwich.m_expense.database;

public class TripEntry {
    public static final String TABLE_NAME = "trip";
    public static final String COL_ID = "id";
    public static final String COL_TRIPNAME = "tripName";
    public static final String COL_DESTINATION = "destination";
    public static final String COL_START_DATE = "start_date";
    public static final String COL_RISK = "risk";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_TRIPNAME + " TEXT NOT NULL, " +
                    COL_DESTINATION + " TEXT NOT NULL, " +
                    COL_START_DATE + " TEXT NOT NULL, "  +
                    COL_RISK + " INTEGER NULL)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
