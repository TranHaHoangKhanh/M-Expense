package vn.edu.grenwich.m_expense.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import vn.edu.grenwich.m_expense.R;
import vn.edu.grenwich.m_expense.database.TravelDAO;
import vn.edu.grenwich.m_expense.models.Trip;

public class TripRegisterConfirmFragment extends DialogFragment {
    protected TravelDAO _db;
    protected Trip _trip;
    protected Button fmTripRegisterConfirmButtonCancel, fmTripRegisterConfirmButtonConfirm;
    protected TextView fmTripRegisterConfirmName, fmTripRegisterConfirmDestination, fmTripRegisterConfirmStartDate, fmTripRegisterConfirmRisk;

    public TripRegisterConfirmFragment() {
        // Required empty public constructor
        _trip = new Trip();
    }

    public TripRegisterConfirmFragment(Trip trip){
        _trip = trip;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        _db = new TravelDAO(getContext());
    }

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

        View view = inflater.inflate(R.layout.fragment_trip_register_confirm, container, false);

        String TripName = getString(R.string.error_no_info);
        String Destination = getString(R.string.error_no_info);
        String StartDate = getString(R.string.error_no_info);
        String RiskType = getString(R.string.error_no_info);

        fmTripRegisterConfirmName = view.findViewById(R.id.fmTripRegisterConfirmName);
        fmTripRegisterConfirmDestination = view.findViewById(R.id.fmTripRegisterConfirmDestination);
        fmTripRegisterConfirmStartDate = view.findViewById(R.id.fmTripRegisterConfirmStartDate);
        fmTripRegisterConfirmRisk = view.findViewById(R.id.fmTripRegisterConfirmRisk);

        fmTripRegisterConfirmButtonCancel = view.findViewById(R.id.fmTripRegisterConfirmButtonCancel);
        fmTripRegisterConfirmButtonConfirm = view.findViewById(R.id.fmTripRegisterConfirmButtonConfirm);

        if(_trip.getRisk() != -1){
            RiskType = _trip.getRisk() == 1 ?  getString(R.string.label_choose_risk) : getString(R.string.label_choose_non_risk);
        }

        if(_trip.getTripName() != null && !_trip.getTripName().trim().isEmpty()){
            TripName = _trip.getTripName();
        }

        if(_trip.getDestination() != null && !_trip.getDestination().trim().isEmpty()){
            Destination = _trip.getDestination();
        }

        if(_trip.getStartDate() != null && !_trip.getStartDate().trim().isEmpty()){
            StartDate = _trip.getStartDate();
        }

        fmTripRegisterConfirmName.setText(TripName);
        fmTripRegisterConfirmDestination.setText(Destination);
        fmTripRegisterConfirmStartDate.setText(StartDate);
        fmTripRegisterConfirmRisk.setText(RiskType);

        fmTripRegisterConfirmButtonCancel.setOnClickListener(v -> dismiss());
        fmTripRegisterConfirmButtonConfirm.setOnClickListener(v -> confirm());

        return view;
    }

    protected void confirm(){
        long status = _db.insertTrip(_trip);
        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromTripRegisterConfirmFragment(status);

        dismiss();
    }

    public interface FragmentListener{
        void sendFromTripRegisterConfirmFragment(long status);
    }


}