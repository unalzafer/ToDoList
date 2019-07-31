package todolist.unalzafer.reminder.ui.activity.register;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.ButterKnife;
import butterknife.OnClick;
import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseActivity;

import butterknife.BindView;
import todolist.unalzafer.reminder.ui.activity.main.MainActivity;

public class RegisterActivity extends BaseActivity implements RegisterView.View {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNameSurname)
    EditText etNameSurname;
    @BindView(R.id.etPassword2)
    EditText etPassword2;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.tvAppBar)
    TextView tvAppBar;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    private RegisterView.Presenter presenter;
    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        if(presenter==null)
            presenter=new RegisterPresenter(this);

        ButterKnife.bind(this);
        tvAppBar.setText("Üye Ol");
        //klavye ilk harf büyük olsun
        etNameSurname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @OnClick(R.id.btRegister)
    public void register(){
        showProgress();
        presenter.registerUser(etNameSurname.getText().toString(),etEmail.getText().toString().trim(),etPassword.getText().toString().trim(),etPassword2.getText().toString().trim());

    }

    @Override
    public void onRegister() {
        hideProgress();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onError(String message) {
        hideProgress();
        showAlert("Dikkat",message);
    }
}
