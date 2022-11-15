package vn.edu.grenwich.m_expense.ui.diaglog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

import vn.edu.grenwich.m_expense.R;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public DatePickerFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return  new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = "";

        ++month;

        date += day < 10 ? "0" + day : day ;
        date += "/";
        date += month < 10 ? "0" + month : month ;
        date += "/";
        date += year;

        DatePickerFragment.FragmentListener listener =  (DatePickerFragment.FragmentListener) getParentFragment();
        listener.sendFromDatePickerFragment(date);

        dismiss();
    }

    public interface FragmentListener {
        void sendFromDatePickerFragment(String date);
    }
}