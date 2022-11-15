package vn.edu.grenwich.m_expense.ui.trip;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.edu.grenwich.m_expense.R;
import vn.edu.grenwich.m_expense.models.Trip;


public class TripUpdateFragment extends Fragment implements TripRegisterFragment.FragmentListener {

    public static final String ARG_PARAM_TRIP = "trip";
    public TripUpdateFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_update, container, false);

        if(getArguments() != null){
            Trip trip = (Trip) getArguments().getSerializable(ARG_PARAM_TRIP);

            Bundle bundle = new Bundle();
            bundle.putSerializable(TripRegisterFragment.ARG_PARAM_TRIP, trip );

            //Send argument (trip info) to TripRegisterFragment
            getChildFragmentManager().getFragments().get(0).setArguments(bundle);
        }

        return view;
    }

    @Override
    public void sendFromTripRegisterFragment(long status) {
        switch ((int) status){
            case 0:
                Toast.makeText(getContext(), R.string.notification_update_fail, Toast.LENGTH_SHORT).show();
                return;
            default:
                Toast.makeText(getContext(), R.string.notification_update_success, Toast.LENGTH_SHORT).show();
                return;
        }
    }
}