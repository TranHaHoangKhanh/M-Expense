package vn.edu.grenwich.m_expense.models;

import java.io.Serializable;

public class Trip implements Serializable {
    protected long _id;
    protected String _tripName;
    protected String _destination;
    protected String _startDate;
    protected int _risk;

    public Trip() {
        _id = -1;
        _tripName = null;
        _destination = null;
        _startDate = null;
        _risk = -1;
    }
    public Trip(long id, String tripName, String destination, String startDate, int risk) {
        _id = id;
        _tripName = tripName;
        _destination = destination;
        _startDate = startDate;
        _risk = risk;
    }

    public long getId () {
        return _id;
    }
    public void setId ( long id){
        _id = id;
    }

    public String getTripName() {
        return _tripName;
    }
    public void setTripName (String tripName){
        _tripName = tripName;
    }

    public String getDestination(){
        return _destination;
    }
    public void setDestination(String destination){
        _destination = destination;
    }


    public String getStartDate(){
        return _startDate;
    }
    public void setStartDate( String startDate){
        _startDate = startDate;
    }

    public int getRisk(){
        return  _risk;
    }
    public void setRisk (int risk){
        _risk = risk;
    }

    public boolean isEmpty(){
        if ( -1 == _id && null == _tripName && null == _destination && null == _startDate && -1 == _risk)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return  "[" + _tripName + "][" + _destination + "] " + _startDate;
    }
}
