package vn.edu.grenwich.m_expense.ui.diaglog;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import vn.edu.grenwich.m_expense.R;


public class CalendarFragment extends DialogFragment {
    CalendarView fmCalendar;

    public CalendarFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        fmCalendar = view.findViewById(R.id.fmCalender);
        fmCalendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            String date = "";

            ++month;

            date += day < 10 ? "0" + day : day;
            date += "/";
            date += month < 10 ? "0" + month : month;
            date += "/";
            date += year;

            FragmentListener listener = (FragmentListener) getParentFragment();
            listener.sendFromCalenderFragment(date);

            dismiss();
        });
        return view;
    }

    public interface FragmentListener{
        void sendFromCalenderFragment(String date);
    }
}