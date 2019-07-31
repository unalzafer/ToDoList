package todolist.unalzafer.reminder.ui.activity.login;

import android.content.Context;

public class LoginView {
    interface LoginPresenter{
        void login(String email,String password);
    }
    interface View{
        Context getContext();
        void onSuccessLogin();
        void onLoginError(String message);
        void onError(String errMsg);
    }
}
