package vn.edu.grenwich.m_expense.ui.expense.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.grenwich.m_expense.R;
import vn.edu.grenwich.m_expense.models.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    protected ArrayList<Expense> _list;

    public ExpenseAdapter(ArrayList<Expense> list){
        _list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_expense, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = _list.get(position);

        holder.listItemExpenseDate.setText(expense.getDate());
        holder.listItemExpenseTime.setText(expense.getTime());
        holder.listItemExpenseComment.setText(expense.getComment());
        holder.listItemExpenseType.setText(expense.getType());
        holder.listItemExpenseAmount.setText(String.valueOf(expense.getAmount()));
    }

    @Override
    public int getItemCount() {
        return _list == null ? 0 : _list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView listItemExpenseDate, listItemExpenseTime, listItemExpenseType, listItemExpenseAmount, listItemExpenseComment;

        public ViewHolder(View itemView){
            super(itemView);

            listItemExpenseDate = itemView.findViewById(R.id.listItemExpenseDate);
            listItemExpenseTime = itemView.findViewById(R.id.listItemExpenseTime);
            listItemExpenseComment = itemView.findViewById(R.id.listItemExpenseComment);
            listItemExpenseType = itemView.findViewById(R.id.listItemExpenseType);
            listItemExpenseAmount = itemView.findViewById(R.id.listItemExpenseAmount);
        }
    }
}
