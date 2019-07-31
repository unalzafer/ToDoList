package todolist.unalzafer.reminder.ui.activity.register;

import android.content.Context;

public class RegisterView {
    interface Presenter{
        void registerUser(String name,String email,String password,String password2);

    }
    interface View{
        Context getContext();
        void onRegister();
        void onError(String message);
    }
}
