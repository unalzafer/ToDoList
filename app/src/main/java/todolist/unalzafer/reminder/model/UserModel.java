package todolist.unalzafer.reminder.model;

import java.util.ArrayList;

public class UserModel {

    public String name;
    public String email;
    private ArrayList<NotesModel> List;

    public UserModel(){}

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public ArrayList<NotesModel> getList() {
        return List;
    }

    public void setList(ArrayList<NotesModel> list) {
        List = list;
    }
}
