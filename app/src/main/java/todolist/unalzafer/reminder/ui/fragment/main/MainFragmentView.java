package todolist.unalzafer.reminder.ui.fragment.main;

import android.content.Context;

import java.util.ArrayList;

import todolist.unalzafer.reminder.model.NotesModel;

public class MainFragmentView {

    interface Presenter{
        void getNoteList();
    }
    interface View{
        Context getContext();
        void loadNotes(ArrayList<NotesModel> notesModels);
    }
}
