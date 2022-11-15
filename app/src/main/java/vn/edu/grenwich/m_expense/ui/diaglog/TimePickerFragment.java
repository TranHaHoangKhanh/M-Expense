package vn.edu.grenwich.m_expense.ui.diaglog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

import vn.edu.grenwich.m_expense.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public TimePickerFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        String time = "";

        time += hour < 10 ? "0" + hour : hour;
        time += ":";
        time += minute < 10 ? "0" + minute : minute;

        TimePickerFragment.FragmentListener listener = (TimePickerFragment.FragmentListener) getParentFragment();
        listener.sendFromTimePickerFragment(time);

        dismiss();
    }

    public interface FragmentListener {
        void sendFromTimePickerFragment(String time);
    }
}