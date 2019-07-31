package todolist.unalzafer.reminder.ui.fragment.alertdialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseDialogFragment;
import todolist.unalzafer.reminder.listener.DialogConfirmListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertDialogFragment extends BaseDialogFragment {
    private String message;
    private String title;


    @BindView(R.id.dialogTitle)
    TextView dialogTitle;
    @BindView(R.id.dialogMessage)
    TextView dialogMessage;

    private DialogConfirmListener confirmListener;


    public static AlertDialogFragment newInstance(String title,String message) {
        Bundle args = new Bundle();
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        fragment.message= message;
        fragment.title= title;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = .6f;
        windowParams.flags|=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return  inflater.inflate(R.layout.fragment_alert_dialog,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        if(message!=null)
            dialogMessage.setText(message);
        if(title!=null)
            dialogTitle.setText(title);
    }
    @OnClick(R.id.btnOk)
    public void close(){
            dismiss();
            if (confirmListener != null)
                confirmListener.onConfirm(getDialog());

    }
    public AlertDialogFragment setConfirmListener(DialogConfirmListener confirmListener){
            this.confirmListener  = confirmListener;
        return this;
    }


}
