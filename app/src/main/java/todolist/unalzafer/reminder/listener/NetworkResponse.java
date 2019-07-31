package todolist.unalzafer.reminder.listener;

import com.google.firebase.database.DataSnapshot;

public interface NetworkResponse {
    void onSuccess(DataSnapshot dataSnapshot);
    void onError(String message);
}
