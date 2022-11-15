package vn.edu.grenwich.m_expense.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.edu.grenwich.m_expense.models.Expense;
import vn.edu.grenwich.m_expense.models.Trip;

public class TravelDAO {
    protected TravelDbHelper travelDbHelper;
    protected SQLiteDatabase dbWrite, dbRead;

    public TravelDAO (Context context){
        travelDbHelper = new TravelDbHelper(context);

        dbRead = travelDbHelper.getReadableDatabase();
        dbWrite = travelDbHelper.getWritableDatabase();
    }

    public void close(){
        dbRead.close();
        dbWrite.close();
    }

    public void reset(){
        travelDbHelper.onUpgrade(dbWrite, 0, 0);
    }

    // Trip

    public long insertTrip (Trip trip){
        ContentValues values = getTripValues(trip);

        return dbWrite.insert(TripEntry.TABLE_NAME , null, values);
    }

    public ArrayList<Trip> getTripList(Trip trip , String orderByColumn, boolean isDesc){
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if (trip != null){
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if(trip.getTripName() != null && !trip.getTripName().trim().isEmpty() ){
                selection += " AND " + TripEntry.COL_TRIPNAME + " LIKE ?";
                conditionList.add("%" + trip.getTripName() + "%");
            }

            if(trip.getDestination() != null && !trip.getDestination().trim().isEmpty()){
                selection += " AND " + TripEntry.COL_DESTINATION + " = ?";
                conditionList.add(trip.getDestination());
            }

            if(trip.getStartDate() != null && !trip.getStartDate().trim().isEmpty()){
                selection += " AND " + TripEntry.COL_START_DATE + " = ?";
                conditionList.add((trip.getStartDate()));
            }

            if(trip.getRisk() != -1){
                selection += " AND " + TripEntry.COL_RISK + " = ?";
                conditionList.add(String.valueOf(trip.getRisk()));
            }

            if(!selection.trim().isEmpty()){
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray( new String[conditionList.size()]);
        }

        return getTripFromDB (null, selection, selectionArgs, null, null, orderBy);
    }

    public Trip getTripById (long id ){
        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return  getTripFromDB( null, selection, selectionArgs, null, null, null).get(0);
    }

    public long updateTrip (Trip trip){
        ContentValues values = getTripValues(trip);

        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(trip.getId())};

        return dbWrite.update(TripEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public long deleteTrip (long id){
        String selection = TripEntry.COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return dbWrite.delete(TripEntry.TABLE_NAME, selection, selectionArgs);
    }

    protected String getOrderByString(String orderByColumn, boolean isDesc) {
        if  (orderByColumn == null || orderByColumn.trim().isEmpty())
            return  null;
        if (isDesc)
            return  orderByColumn.trim() + " DESC";

        return  orderByColumn.trim();
    }

    protected  ContentValues getTripValues(Trip trip){
        ContentValues values = new ContentValues();

        values.put(TripEntry.COL_TRIPNAME, trip.getTripName());
        values.put(TripEntry.COL_DESTINATION, trip.getDestination());
        values.put(TripEntry.COL_START_DATE, trip.getStartDate());
        values.put(TripEntry.COL_RISK, trip.getRisk());

        return values;
    }

    protected ArrayList<Trip> getTripFromDB( String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        ArrayList<Trip> list = new ArrayList<>();

        Cursor cursor = dbRead.query(TripEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()) {
            Trip tripItems = new Trip();

            tripItems.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TripEntry.COL_ID)));
            tripItems.setTripName(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_TRIPNAME)));
            tripItems.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_DESTINATION)));
            tripItems.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(TripEntry.COL_START_DATE)));
            tripItems.setRisk(cursor.getInt(cursor.getColumnIndexOrThrow(TripEntry.COL_RISK)));

            list.add(tripItems);
        }
        cursor.close();

        return list;
    }

    //Expense
    public long insertExpense(Expense expense){
        ContentValues values = getExpenseValues(expense);

        return dbWrite.insert(ExpenseEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Expense> getExpenseList (Expense expense, String orderByColumn, boolean isDesc){
        String orderBy = getOrderByString(orderByColumn, isDesc);

        String selection = null;
        String[] selectionArgs = null;

        if(expense != null){
            selection = "";
            ArrayList<String> conditionList = new ArrayList<String>();

            if(expense.getComment() != null && expense.getComment().trim().isEmpty()){
                selection += " AND " + ExpenseEntry.COL_COMMENT + " LIKE ?";
                conditionList.add("%" + expense.getComment() + "%");
            }

            if(expense.getAmount() != -1){
                selection += " AND " + ExpenseEntry.COL_AMOUNT + " = ?";
                conditionList.add(String.valueOf(expense.getAmount()));
            }

            if(expense.getDate() != null && expense.getDate().trim().isEmpty()){
                selection += " AND " + ExpenseEntry.COL_DATE + " = ?";
                conditionList.add(expense.getDate());
            }

            if(expense.getTime() != null && expense.getTime().trim().isEmpty()) {
                selection += " AND " + ExpenseEntry.COL_TYPE + " = ?";
                conditionList.add(expense.getTime());
            }

            if(expense.getType() != null && expense.getType().trim().isEmpty()){
                selection += " AND " + ExpenseEntry.COL_TYPE + " = ?";
                conditionList.add(expense.getType());
            }

            if(expense.getTripId() != -1){
                selection += " AND " + ExpenseEntry.COL_TRIPID + " = ?";
                conditionList.add(String.valueOf(expense.getTripId()));
            }

            if(!selection.trim().isEmpty()){
                selection = selection.substring(5);
            }

            selectionArgs = conditionList.toArray(new String[conditionList.size()]);
        }

        return  getExpenseFromDB(null, selection, selectionArgs, null, null, orderBy);
    }



//    public Expense getExpenseById(long id){
//        String selection = ExpenseEntry.COL_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(id)};
//
//        return getExpenseFromDB(null, selection, selectionArgs, null, null, null).get(0);
//    }
//
//    public long updateExpense (Expense expense){
//        ContentValues values = getExpenseValues(expense);
//
//        String selection = ExpenseEntry.COL_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(expense.getId())};
//
//        return dbWrite.update(ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
//    }
//
//    public long deleteExpense(long id ){
//        String selection = ExpenseEntry.COL_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(id)};
//
//        return dbWrite.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
//    }

    protected ContentValues getExpenseValues(Expense expense){
        ContentValues values = new ContentValues();

        values.put(ExpenseEntry.COL_AMOUNT, expense.getAmount());
        values.put(ExpenseEntry.COL_COMMENT, expense.getComment());
        values.put(ExpenseEntry.COL_DATE, expense.getDate());
        values.put(ExpenseEntry.COL_TIME, expense.getTime());
        values.put(ExpenseEntry.COL_TYPE, expense.getType());
        values.put(ExpenseEntry.COL_TRIPID, expense.getTripId());

        return values;
    }


    protected  ArrayList<Expense> getExpenseFromDB(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        ArrayList<Expense> list = new ArrayList<>();

        Cursor cursor= dbRead.query(ExpenseEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        while (cursor.moveToNext()){
            Expense expenseItem = new Expense();

            expenseItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_ID)));
            expenseItem.setComment(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_COMMENT)));
            expenseItem.setAmount(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_AMOUNT)));
            expenseItem.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_DATE)));
            expenseItem.setTime(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TIME)));
            expenseItem.setType(cursor.getString(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TYPE)));
            expenseItem.setTripId(cursor.getLong(cursor.getColumnIndexOrThrow(ExpenseEntry.COL_TRIPID)));

            list.add(expenseItem);

        }

        cursor.close();

        return list;
    }



}
