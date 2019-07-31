package todolist.unalzafer.reminder.ui.activity.main;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import todolist.unalzafer.reminder.api.FirebaseRequest;
import todolist.unalzafer.reminder.listener.NetworkResponse;
import todolist.unalzafer.reminder.model.NotesModel;

import java.util.ArrayList;

public class MainPresenter implements MainView.Presenter {
    private MainView.View mView;

    public MainPresenter(MainView.View mView){this.mView=mView;}


}
