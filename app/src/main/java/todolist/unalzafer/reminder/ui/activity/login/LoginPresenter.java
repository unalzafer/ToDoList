package todolist.unalzafer.reminder.ui.activity.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginView.LoginPresenter {
    private LoginView.View mView;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public LoginPresenter(LoginView.View mView){this.mView=mView;}

    @Override
    public void login(String email, String password) {
        auth=FirebaseAuth.getInstance();

        //örnek olabilecek giriş senaryoları kontrolü biz basit bişeyler yaptık
        if(TextUtils.isEmpty(email)){
            mView.onLoginError("Lütfen emailinizi giriniz.");
            return;
        }
        else if (!email.contains("@")) {
            mView.onLoginError("Lütfen geçerli bir mail adresi giriniz.");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            mView.onLoginError("Lütfen parolanızı giriniz.");
            return;
        }
        else if (password.length()<6){
            mView.onLoginError("Parola en az 6 haneli olmalıdır.");
            return;
        }else {
            //Firebase ile bağlantıyı kurup mail ve şifre doğrulaması yapılır ve sonra giriş yapılır veya yapılmaz
            //Eğer işlem başarılı olursa task.isSuccessful true

            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //İşlem başarılı ise MainActivity'e gider
                            if (task.isSuccessful()) {
                                mView.onSuccessLogin();
                            }
                            else {
                                mView.onLoginError("Bilgilerinizi Kontrol Ediniz."+task.getException());
                            }

                        }
                    });

        }
    }

}
