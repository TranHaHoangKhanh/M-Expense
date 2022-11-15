package vn.edu.grenwich.m_expense.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

import vn.edu.grenwich.m_expense.R;
import vn.edu.grenwich.m_expense.database.TravelDAO;
import vn.edu.grenwich.m_expense.models.Trip;
import vn.edu.grenwich.m_expense.ui.diaglog.CalendarFragment;

public class TripRegisterFragment extends Fragment implements TripRegisterConfirmFragment.FragmentListener, CalendarFragment.FragmentListener {

    public static final String ARG_PARAM_TRIP = "trip";

    protected EditText fmTripRegisterTripName, fmTripRegisterDestination,fmTripRegisterStartDate;
    protected LinearLayout fmTripRegisterLinearLayout;
    protected SwitchMaterial fmTripRegisterRiskAssesment;
    protected TextView fmTripRegisterError;
    protected Button fmTripRegisterButton;

    protected TravelDAO _db;

    public TripRegisterFragment() {}

    @Override
    public void onAttach (@NonNull Context context){
        super.onAttach(context);
        _db = new TravelDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_register, container, false);

        fmTripRegisterError = view.findViewById(R.id.fmTripRegisterError);
        fmTripRegisterTripName = view.findViewById(R.id.fmTripRegisterTripName);
        fmTripRegisterStartDate = view.findViewById(R.id.fmTripRegisterStartDate);
        fmTripRegisterDestination = view.findViewById(R.id.fmTripRegisterDestination);
        fmTripRegisterRiskAssesment = view.findViewById(R.id.fmTripRegisterRiskAssesment);

        fmTripRegisterButton = view.findViewById(R.id.fmTripRegisterButton);
        fmTripRegisterLinearLayout = view.findViewById(R.id.fmTripRegisterLinearLayout);

        //Show Calendar for choosing a date
        fmTripRegisterStartDate.setOnTouchListener((v, motionEvent) -> showCalendar(motionEvent));

        //Update current Trip
        if (getArguments() != null){
            Trip trip  = (Trip) getArguments().getSerializable(ARG_PARAM_TRIP);

            fmTripRegisterTripName.setText(trip.getTripName());
            fmTripRegisterDestination.setText(trip.getDestination());
            fmTripRegisterStartDate.setText(trip.getStartDate());
            fmTripRegisterRiskAssesment.setChecked(trip.getRisk() == 1? true: false);


            fmTripRegisterButton.setText(R.string.label_update);
            fmTripRegisterButton.setOnClickListener(v -> update(trip.getId()));

            return view;
        }

        //Create new trip
        fmTripRegisterButton.setOnClickListener(v -> register());

        return view;
    }

    protected void register() {
        if (isValidForm()){
            Trip trip = getTripFromInput(-1);

            new TripRegisterConfirmFragment(trip).show(getChildFragmentManager(), null);

            return;
        }

        moveButton();
    }

    protected void update(long id) {
        if(isValidForm()) {
            Trip trip = getTripFromInput(id);

            long status = _db.updateTrip(trip);

            FragmentListener listener = (FragmentListener) getParentFragment();
            listener.sendFromTripRegisterFragment(status);

            return;
        }

        moveButton();
    }

    protected boolean showCalendar(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return  false;
    }

    protected Trip getTripFromInput(long id) {
        String tripname = fmTripRegisterTripName.getText().toString();
        String destination = fmTripRegisterDestination.getText().toString();
        String startDate = fmTripRegisterStartDate.getText().toString();
        int riskassesment = fmTripRegisterRiskAssesment.isChecked() ? 1 : 0;

        return new Trip(id, tripname, destination, startDate, riskassesment);
    }

    protected boolean isValidForm() {
        boolean isValid = true;

        String error = "";
        String tripname = fmTripRegisterTripName.getText().toString();
        String destination = fmTripRegisterDestination.getText().toString();
        String startDate = fmTripRegisterStartDate.getText().toString();

        if(tripname == null || tripname.trim().isEmpty()){
            error += "* " + getString(R.string.error_blank_tripname) + "\n";
            isValid = false;
        }

        if(destination == null || destination.trim().isEmpty()){
            error  += "* " + getString(R.string.error_blank_destination) + "\n";
            isValid = false;
        }

        if (startDate == null || startDate.trim().isEmpty()){
            error += "* " + getString(R.string.error_blank_start_date) + "\n";
            isValid = false;
        }

        fmTripRegisterError.setText(error);

        return isValid;
    }

    protected void moveButton() {
        LinearLayout.LayoutParams btnParams = (LinearLayout.LayoutParams) fmTripRegisterButton.getLayoutParams();

        int linearLayoutPaddingLeft = fmTripRegisterLinearLayout.getPaddingLeft();
        int linearLayoutPaddingRight = fmTripRegisterLinearLayout.getPaddingRight();
        int linearLayoutWidth = fmTripRegisterLinearLayout.getWidth() - linearLayoutPaddingLeft - linearLayoutPaddingRight;

        btnParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        btnParams.topMargin += fmTripRegisterButton.getHeight();
        btnParams.leftMargin = btnParams.leftMargin == 0 ? linearLayoutWidth - fmTripRegisterButton.getWidth() : 0;

        fmTripRegisterButton.setLayoutParams(btnParams);
    }

    @Override
    public void sendFromTripRegisterConfirmFragment(long status) {
        switch ((int) status){
            case -1:
                Toast.makeText(getContext(), R.string.notification_create_fail, Toast.LENGTH_SHORT).show();
                return;

            default:
                Toast.makeText(getContext(), R.string.notification_create_success, Toast.LENGTH_SHORT).show();

                fmTripRegisterTripName.setText("");
                fmTripRegisterDestination.setText("");
                fmTripRegisterStartDate.setText("");

                fmTripRegisterTripName.requestFocus();

        }
    }

    @Override
    public void sendFromCalenderFragment(String date) {
        fmTripRegisterStartDate.setText(date);
    }



    public interface FragmentListener{
        void sendFromTripRegisterFragment(long status);
    }
}