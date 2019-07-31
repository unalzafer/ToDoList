package todolist.unalzafer.reminder.ui.fragment.addNote;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import todolist.unalzafer.reminder.api.FirebaseRequest;
import todolist.unalzafer.reminder.listener.NetworkResponse;
import todolist.unalzafer.reminder.model.NotesModel;

import static todolist.unalzafer.reminder.data.Constants.cMYNOTES;
import static todolist.unalzafer.reminder.data.Constants.cNOTE;
import static todolist.unalzafer.reminder.data.Constants.cUSER;

public class AddNotePresenter implements AddNoteView.Presenter {
    private AddNoteView.View mView;
    private FirebaseUser user;
    private FirebaseAuth auth;
    long currentTime;

    public AddNotePresenter(AddNoteView.View mView){
        this.mView=mView;
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        currentTime = Calendar.getInstance().getTimeInMillis();
    }


    @Override
    public void addNewNote(String title, String text, String color, Object selectDate) {

        //Database notları bölümüne referance yap
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(cNOTE).child(cUSER).child(user.getUid()).child(cMYNOTES);
        NotesModel notesModel = new NotesModel(title,text,color,currentTime);
        notesModel.setSelectDate(selectDate);
        FirebaseRequest.with(mView.getContext()).setNote(databaseReference,notesModel);
    }

    @Override
    public void getDetailNote(String id) {
        Query dr=FirebaseDatabase.getInstance().getReference("Note").child("User").child(user.getUid()).child("MyNotes").child(id);
        FirebaseRequest.with(mView.getContext()).getData(dr, new NetworkResponse() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null) { //snapshot boş değilse
                    NotesModel notes = dataSnapshot.getValue(NotesModel.class);
                    mView.onDetailNote(notes);
                }
            }

            @Override
            public void onError(String message) {

            }
        });

    }

    @Override
    public void removeNote(String id) {
        DatabaseReference  dr=FirebaseDatabase.getInstance().getReference("Note").child("User").child(user.getUid()).child("MyNotes").child(id);
        FirebaseRequest.with(mView.getContext()).removeData(dr);
        mView.removeNoteSuccess();
    }

    @Override
    public void updateNote(String id, String title, String text, String color,Object selectDate,Object date,String state) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(cNOTE).child(cUSER).child(user.getUid()).child(cMYNOTES).child(id);
        NotesModel notesModel = new NotesModel(title,text,color,date);
        notesModel.setUpdateDate(currentTime);
        notesModel.setSelectDate(selectDate);
        notesModel.setState(state);
        FirebaseRequest.with(mView.getContext()).updateNote(databaseReference,notesModel);
    }
}
