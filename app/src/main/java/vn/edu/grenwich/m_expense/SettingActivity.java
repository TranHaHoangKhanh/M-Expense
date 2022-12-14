package vn.edu.grenwich.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

import vn.edu.grenwich.m_expense.database.BackupEntry;
import vn.edu.grenwich.m_expense.database.TravelDAO;
import vn.edu.grenwich.m_expense.models.Backup;
import vn.edu.grenwich.m_expense.models.Expense;
import vn.edu.grenwich.m_expense.models.Trip;

public class SettingActivity extends AppCompatActivity {

    protected TravelDAO _db;
    protected Button settingResetDatabase, settingBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.label_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _db = new TravelDAO(this);

        settingBackup = findViewById(R.id.settingBackup);
        settingResetDatabase = findViewById(R.id.settingResetDatabase);

        settingBackup.setOnClickListener(v -> backup());
        settingResetDatabase.setOnClickListener(v -> resetDatabase());

    }

    protected void backup(){
        ArrayList<Trip> trips = _db.getTripList(null, null, false);
        ArrayList<Expense> expenses = _db.getExpenseList(null, null, false);

        if(trips != null && trips.size() > 0 && expenses != null && expenses.size() > 0){
            String deviceName = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            Backup backup = new Backup(new Date(), deviceName, trips, expenses);

            FirebaseFirestore.getInstance().collection(BackupEntry.TABLE_NAME)
                    .add(backup)
                    .addOnSuccessListener(document ->{
                        Toast.makeText(this, R.string.notification_backup_success, Toast.LENGTH_SHORT).show();
                        Log.d(getResources().getString(R.string.label_backup_firestore), document.getId());
                    })
                    .addOnFailureListener(e ->{
                       Toast.makeText(this, R.string.notification_backup_fail, Toast.LENGTH_SHORT).show();
                       e.printStackTrace();
                    });
        } else
        {
            Toast.makeText(this, R.string.error_empty_list, Toast.LENGTH_SHORT).show();
        }
    }

    public void resetDatabase(){
        _db.reset();

        Toast.makeText(this, R.string.label_reset_database, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return false;
    }
}