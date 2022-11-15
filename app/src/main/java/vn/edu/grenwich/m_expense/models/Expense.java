package vn.edu.grenwich.m_expense.models;
import java.io.Serializable;

public class Expense implements Serializable{
    protected long _id;
    protected long _amount;
    protected String _type;
    protected String _date;
    protected String _time;
    protected String _comment;
    protected long  _tripId;

    public Expense() {
        _id = -1;
        _amount  = -1;
        _type = null;
        _date = null;
        _time = null;
        _comment = null;
        _tripId = -1;
    }

    public Expense ( long id, long amount, String type, String date, String time, String comment, long tripId) {
        _id = id;
        _amount = amount;
        _type = type;
        _date = date;
        _time = time;
        _comment = comment;
        _tripId = tripId;
    }

    public long getId (){
        return _id;
    }
    public void setId (long id){
        _id = id;
    }


    public long getAmount (){
        return  _amount;
    }
    public void setAmount( long amount){
        _amount = amount;
    }

    public String getType(){
        return _type;
    }
    public void setType( String type){
        _type = type;
    }

    public String getDate(){
        return  _date;
    }
    public void setDate (String date){
        _date = date;
    }

    public String getTime(){
        return _time;
    }
    public void setTime (String time){
        _time = time;
    }

    public String getComment(){
        return _comment;
    }
    public void setComment( String comment){
        _comment = comment;
    }

    public long getTripId(){
        return _tripId;
    }
    public void setTripId( long tripId){
        _tripId = tripId;
    }

}
