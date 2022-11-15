package vn.edu.grenwich.m_expense.ui.diaglog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;

import vn.edu.grenwich.m_expense.R;

public class DeleteConfirmFragment extends DialogFragment {
    protected String _message;

    public DeleteConfirmFragment() {
        _message = "";
    }

    public DeleteConfirmFragment (String message){
        _message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( @Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.label_confirmation)
                .setMessage(_message)
                .setNegativeButton(R.string.label_cancel, (dialog, id) -> dismiss())
                .setPositiveButton(R.string.label_confirm, (dialog, id) -> delete())
                .create();
    }

    protected  void delete(){
        FragmentListener listener = (FragmentListener) getParentFragment();
        listener.sendFromDeleteConfirmFragment(1);

        dismiss();
    }
    public interface  FragmentListener {
        void sendFromDeleteConfirmFragment ( int status);
    }
}