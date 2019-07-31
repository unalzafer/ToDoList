package todolist.unalzafer.reminder.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.ui.fragment.alertdialog.AlertDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity {
    private Dialog progress;

    public abstract int getContentView();

    public abstract void initView();

    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
        if(!checkConnection()){
            showAlert("Uyarı","İnternet bağlantınız aktif değil.");
        }
    }

    public void showProgress() {

        progress = new Dialog(getContext());
        progress.setContentView(R.layout.progress);
        Objects.requireNonNull(progress.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress.setCancelable(false);
        progress.show();

    }

    public void hideProgress() {
        if (progress != null && progress.isShowing())
            progress.dismiss();
    }

    public void setNoStatusBar(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //İnternet iznini kontrol edebilmek için
    public boolean checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    public  void showAlert( String title,String message){
        AlertDialogFragment.newInstance(title,message).show(getSupportFragmentManager(),AlertDialogFragment.class.getSimpleName());
    }

    public String dateFormat(Object time){
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(time);
    }
}
