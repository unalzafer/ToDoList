package todolist.unalzafer.reminder.ui.activity.register;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import todolist.unalzafer.reminder.model.UserModel;

public class RegisterPresenter implements RegisterView.Presenter {
    private RegisterView.View mView;
    private FirebaseAuth auth;
    public RegisterPresenter(RegisterView.View mView){this.mView=mView;}

    @Override
    public void registerUser(final String name, final String email, String password, String password2) {
        //FirebaseAuth sınıfının nesnelerini kullanmak için getInstance kullanıyoruz.
        auth=FirebaseAuth.getInstance();
        //örnek olabilecek giriş senaryoları kontrolü biz basit bişeyler yaptık
        if(TextUtils.isEmpty(email)){
            mView.onError("Lütfen emailinizi giriniz");
            return;
        }
        else if (!email.contains("@")) {
            mView.onError("Lütfen geçerli bir mail adresi giriniz"); return;
        }
        else if(TextUtils.isEmpty(password)){
            mView.onError("Lütfen parolanızı giriniz"); return;
        }
        else if(!password.equals(password2)){
            mView.onError("Parolalarınız Eşleşmiyor."); return;
        }
        else if (password.length()<6){
            mView.onError("Parola en az 6 haneli olmalıdır"); return;
        }else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                UserModel userModel = new UserModel(name, email);
                                FirebaseDatabase.getInstance().getReference("Note").child("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mView.onRegister();
                                        } else {

                                        }
                                    }
                                });

                            } else {
                                mView.onError("Hata Oluştu Tekrar Deneyiniz." + task.getException());
                            }
                        }
                    });
        }
    }
}
