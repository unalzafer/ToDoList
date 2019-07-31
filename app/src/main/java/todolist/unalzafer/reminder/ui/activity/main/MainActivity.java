package todolist.unalzafer.reminder.ui.activity.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseActivity;
import todolist.unalzafer.reminder.listener.CustomItemClickListener;
import todolist.unalzafer.reminder.model.NotesModel;
import todolist.unalzafer.reminder.ui.activity.adapter.NoteAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import todolist.unalzafer.reminder.ui.fragment.addNote.AddNoteFragment;
import todolist.unalzafer.reminder.ui.fragment.main.MainFragment;

public class MainActivity extends BaseActivity implements MainView.View {

    private MainView.Presenter presenter;
    int container = R.id.mainframe;


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (presenter == null)
            presenter = new MainPresenter(this);

        ButterKnife.bind(this);


        loadMainFragment();

    }



    public void loadMainFragment() {
        addFragmentWithoutStack(MainFragment.newInstance());
    }

    private void addFragmentWithoutStack(MainFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(container, fragment).commitAllowingStateLoss();
    }
    public void addFragmentWithStack(Fragment fragment, String Tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(Tag);
        ft.replace(container, fragment).commitAllowingStateLoss();

    }


}
