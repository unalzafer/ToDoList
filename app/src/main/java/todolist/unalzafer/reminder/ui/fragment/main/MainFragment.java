package todolist.unalzafer.reminder.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseFragment;
import todolist.unalzafer.reminder.listener.CustomItemClickListener;
import todolist.unalzafer.reminder.model.NotesModel;
import todolist.unalzafer.reminder.ui.activity.adapter.NoteAdapter;
import todolist.unalzafer.reminder.ui.activity.login.LoginActivity;
import todolist.unalzafer.reminder.ui.activity.main.MainActivity;
import todolist.unalzafer.reminder.ui.fragment.addNote.AddNoteFragment;

public class MainFragment extends BaseFragment implements MainFragmentView.View {

    private FirebaseUser user;
    private FirebaseAuth auth;

    @BindView(R.id.ivAddNote)
    ImageView ivAddNote;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.tvAppBar)
    TextView tvAppBar;
    @BindView(R.id.tvSignOut)
    TextView tvSignOut;
    @BindView(R.id.nothingCell)
    ConstraintLayout nothingCell;

    NoteAdapter adapter;
    MainFragmentView.Presenter presenter;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter==null)
            presenter=new MainFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showProgress();
        ButterKnife.bind(this,view);
        tvAppBar.setText("Anasayfa");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        tvSignOut.setVisibility(View.VISIBLE);

        ivAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),AddNotes.class));

                ((MainActivity)getActivity()).addFragmentWithStack(AddNoteFragment.newInstance(), AddNoteFragment.class.getSimpleName());
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        presenter.getNoteList();
    }

    @Override
    public void loadNotes(final ArrayList<NotesModel> notesModels) {
        Collections.reverse(notesModels);
        adapter = new NoteAdapter(notesModels, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Toast.makeText(getApplicationContext(),"Tıklanan:"+" "+notesModelArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).addFragmentWithStack(AddNoteFragment.newInstance(notesModels.get(position).getIdNote()), AddNoteFragment.class.getSimpleName());

            }
        });
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        setAdapters(notesModels.size());
        hideProgress();
    }

    @OnClick(R.id.tvSignOut)
    void signOut(){
        if(FirebaseAuth.getInstance()!=null)
            FirebaseAuth.getInstance().signOut();
        //getFragmentManager().popBackStack();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    public void setAdapters(int size){
        if(size>0){
            nothingCell.setVisibility(View.GONE);
        }else {
            nothingCell.setVisibility(View.VISIBLE);
        }
    }
}
