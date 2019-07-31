package todolist.unalzafer.reminder.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseActivity;
import todolist.unalzafer.reminder.ui.activity.main.MainActivity;
import todolist.unalzafer.reminder.ui.activity.register.RegisterActivity;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements LoginView.View {

    LoginView.LoginPresenter presenter;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.tvAppBar)
    TextView tvAppBar;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    FirebaseUser user;



    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        if (presenter == null) presenter = new LoginPresenter(this);

        ButterKnife.bind(this);
        tvAppBar.setText("Oturum Aç");
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                presenter.login(etEmail.getText().toString().trim(),etPassword.getText().toString().trim());
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onSuccessLogin() {
        hideProgress();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onLoginError(String message) {
        hideProgress();
        showAlert("Giriş Başarısız",message);
    }

    @Override
    public void onError(String errMsg) {
        hideProgress();
        showAlert("Hata",errMsg);
    }
}
