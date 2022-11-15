package vn.edu.grenwich.m_expense.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;

import vn.edu.grenwich.m_expense.R;
import vn.edu.grenwich.m_expense.database.TravelDAO;
import vn.edu.grenwich.m_expense.models.Expense;
import vn.edu.grenwich.m_expense.models.Trip;
import vn.edu.grenwich.m_expense.ui.diaglog.DeleteConfirmFragment;
import vn.edu.grenwich.m_expense.ui.expense.ExpenseCreateFragment;
import vn.edu.grenwich.m_expense.ui.expense.list.ExpenseListFragment;
import vn.edu.grenwich.m_expense.ui.trip.list.TripListFragment;


public class TripDetailFragment extends Fragment implements DeleteConfirmFragment.FragmentListener, ExpenseCreateFragment.FragmentListener {
    public static final String ARG_PARAM_TRIP = "trip";

    protected TravelDAO _db;
    protected Trip _trip;
    protected Button fmTripDetailExpenseButton;
    protected BottomAppBar fmTripDetailBottomAppBar;
    protected FragmentContainerView fmTripDetailExpenseList;
    protected TextView fmTripDetailTripName, fmTripDetailDestination, fmTripDetailStartDate, fmTripDetailTypeRisk;

    public TripDetailFragment() {}

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        _db = new TravelDAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        fmTripDetailTripName = view.findViewById(R.id.fmTripDetailTripName);
        fmTripDetailDestination = view.findViewById(R.id.fmTripDetailDestination);
        fmTripDetailStartDate = view.findViewById(R.id.fmTripDetailStartDate);
        fmTripDetailTypeRisk = view.findViewById(R.id.fmTripDetailTypeRisk);

        fmTripDetailBottomAppBar = view.findViewById(R.id.fmTripDetailBottomAppBar);
        fmTripDetailExpenseButton = view.findViewById(R.id.fmTripDetailExpenseButton);
        fmTripDetailExpenseList = view.findViewById(R.id.fmTripDetailExpenseList);

        fmTripDetailBottomAppBar.setOnMenuItemClickListener(item -> menuItemSelected(item));
        fmTripDetailExpenseButton.setOnClickListener(v -> showAddExpenseFragment());

        showDetails();
        showExpenseList();

        return view;
    }
    protected  void showDetails(){
        String tripName = getString(R.string.error_not_found);
        String destination = getString(R.string.error_not_found);
        String startDate = getString(R.string.error_not_found);
        String Risk = getString(R.string.error_not_found);

        if(getArguments() != null){
            _trip = (Trip) getArguments().getSerializable(ARG_PARAM_TRIP);
            _trip = _db.getTripById(_trip.getId()); //Retrieve data from Database

            tripName = _trip.getTripName();
            destination = _trip.getDestination();
            startDate = _trip.getStartDate();
            Risk = _trip.getRisk() == 1 ? getString(R.string.label_choose_risk): getString(R.string.label_choose_non_risk);
        }

        fmTripDetailTripName.setText(tripName);
        fmTripDetailDestination.setText(destination);
        fmTripDetailStartDate.setText(startDate);
        fmTripDetailTypeRisk.setText(Risk);
    }

    //Connect TripDetailFragment with ExpenseListFragment
    protected void showExpenseList(){
        if(getArguments() != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(ExpenseListFragment.ARG_PARAM_TRIP_ID, _trip.getId());

            //Send arguments (Trip id) to ExpenseListFragment
            getChildFragmentManager().getFragments().get(0).setArguments(bundle);

        }
    }
    protected  boolean menuItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.tripUpdateFragment:
                showUpdateFragment();
                return true;

            case R.id.tripDeleteFragment:
                showDeleteFragment();
                return true;
        }


        return true;
    }

    protected void showUpdateFragment(){
        Bundle bundle = null;

        if(_trip != null){
            bundle = new Bundle();
            bundle.putSerializable(TripUpdateFragment.ARG_PARAM_TRIP, _trip);
        }

        // Update code after create Navigation
        Navigation.findNavController(getView()).navigate(R.id.tripUpdateFragment, bundle);
    }

    protected void showDeleteFragment(){
        new DeleteConfirmFragment(getString(R.string.notification_delete_confirm)).show(getChildFragmentManager(), null);
    }

    //Update code after create ExpenseCreateFragment
    protected void showAddExpenseFragment(){
        new ExpenseCreateFragment(_trip.getId()).show(getChildFragmentManager(), null);
    }

    // Update code after create Navigation
    @Override
    public void sendFromDeleteConfirmFragment(int status) {
        if(status ==1 && _trip != null){
            long numOfDeletedRows = _db.deleteTrip(_trip.getId());

            if(numOfDeletedRows >0){
                Toast.makeText(getContext(), R.string.notification_delete_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();

                return;
            }
        }

        Toast.makeText(getContext(), R.string.notification_delete_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFromExpenseCreateFragment(Expense expense){
        if(expense != null){
            expense.setTripId(_trip.getId());

            long id = _db.insertExpense(expense);

            Toast.makeText(getContext(), id == -1 ? R.string.notification_create_fail : R.string.notification_create_success, Toast.LENGTH_SHORT).show();

            reloadExpenseList();
        }
    }

    protected void reloadExpenseList(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpenseListFragment.ARG_PARAM_TRIP_ID, _trip.getId());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fmTripDetailExpenseList, ExpenseListFragment.class, bundle)
                .commit();
    }

}