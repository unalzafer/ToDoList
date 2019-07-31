package todolist.unalzafer.reminder.api;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import todolist.unalzafer.reminder.listener.NetworkResponse;
import todolist.unalzafer.reminder.model.NotesModel;

public class FirebaseRequest {
    private Context mContext;


    public FirebaseRequest(Context context) {
        this.mContext = context;
    }

    public static FirebaseRequest with(Context context) {
        return new FirebaseRequest(context);
    }
    public static FirebaseRequest with(Activity activity) {
        return new FirebaseRequest(activity);
    }

    public static FirebaseRequest with(Fragment fragment) {
        return new FirebaseRequest(fragment.getActivity());
    }

    public void getData(Query databaseReference, final NetworkResponse response){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                response.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                response.onError(""+databaseError.getMessage());
            }
        });
    }
    public void setNote(DatabaseReference databaseReference, NotesModel notesModel){
        databaseReference.push().setValue(notesModel);
    }
    public void removeData(DatabaseReference databaseReference){
        databaseReference.removeValue();
    }
    public void updateNote(DatabaseReference databaseReference, NotesModel notesModel){
        databaseReference.setValue(notesModel);
    }

}
