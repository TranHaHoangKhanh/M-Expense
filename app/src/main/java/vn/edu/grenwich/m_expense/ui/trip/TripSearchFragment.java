package vn.edu.grenwich.m_expense.ui.trip;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.grenwich.m_expense.R;
import vn.edu.grenwich.m_expense.models.Trip;
import vn.edu.grenwich.m_expense.ui.diaglog.CalendarFragment;


public class TripSearchFragment extends DialogFragment implements CalendarFragment.FragmentListener {

    protected EditText fmTripSearchName, fmTripSearchDestination, fmTripSearchDate;
    protected Button fmTripSearchButtonCancel, fmTripSearchButtonSearch;


    public TripSearchFragment() {}

    @Override
    public void onResume(){
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_search, container, false);

        fmTripSearchName = view.findViewById(R.id.fmTripSearchName);
        fmTripSearchDestination = view.findViewById(R.id.fmTripSearchDestination);
        fmTripSearchDate = view.findViewById(R.id.fmTripSearchDate);

        fmTripSearchButtonSearch = view.findViewById(R.id.fmTripSearchButtonSearch);
        fmTripSearchButtonCancel = view.findViewById(R.id.fmTripSearchButtonCancel);

        //Button
        fmTripSearchButtonSearch.setOnClickListener(v -> search());
        fmTripSearchButtonCancel.setOnClickListener(v -> dismiss());

        //Show Date
        fmTripSearchDate.setOnTouchListener((v, motionEvent) -> showCalender(motionEvent));

        return view;
    }

    protected void search(){
        Trip _trip = new Trip();

        String tripName = fmTripSearchName.getText().toString();
        String destination = fmTripSearchDestination.getText().toString();
        String date = fmTripSearchDate.getText().toString();

        if(tripName != null && !tripName.trim().isEmpty()){
            _trip.setTripName(tripName);
        }

        if(destination != null && !destination.trim().isEmpty()){
            _trip.setDestination(destination);
        }

        if (date != null && !date.trim().isEmpty()){
            _trip.setStartDate(date);
        }

        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromTripSearchFragment(_trip);

        dismiss();
    }

    protected boolean showCalender(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    @Override
    public void sendFromCalenderFragment(String date) {
        fmTripSearchDate.setText(date);
    }

    public interface FragmentListener{
        void sendFromTripSearchFragment(Trip trip);
    }
}