package todolist.unalzafer.reminder.ui.fragment.main;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import todolist.unalzafer.reminder.api.FirebaseRequest;
import todolist.unalzafer.reminder.listener.NetworkResponse;
import todolist.unalzafer.reminder.model.NotesModel;

public class MainFragmentPresenter implements MainFragmentView.Presenter{

    private MainFragmentView.View mView;

    public MainFragmentPresenter(MainFragmentView.View mView){this.mView=mView;}
    @Override
    public void getNoteList() {
        final ArrayList<NotesModel> notesModels=new ArrayList<>();
        Query databaseReference= FirebaseDatabase.getInstance().getReference("Note").child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyNotes").orderByChild("date");

        FirebaseRequest.with(mView.getContext()).getData(databaseReference, new NetworkResponse() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    Log.d("firebase :","title->"+ds.toString());

                    NotesModel notes = ds.getValue(NotesModel.class);
                    notes.setIdNote(ds.getKey());
                    notesModels.add(notes);
                }
                mView.loadNotes(notesModels);
            }

            @Override
            public void onError(String message) {
                Log.w("firebase", "Failed to read value. "+message);
            }
        });


    }
}
