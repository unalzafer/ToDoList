package todolist.unalzafer.reminder.ui.fragment.addNote;

import android.content.Context;
import todolist.unalzafer.reminder.model.NotesModel;

public class AddNoteView {
    interface  Presenter{
        void addNewNote(String title,String text,String color,Object selectDate);
        void getDetailNote(String id);
        void removeNote(String id);
        void updateNote(String id,String title,String text,String color,Object selectDate,Object date,String state);

    }
    interface View{
        Context getContext();
        void onDetailNote(NotesModel notesModel);
        void removeNoteSuccess();
    }
}
