package todolist.unalzafer.reminder.listener;

import android.content.DialogInterface;

public interface DialogConfirmListener {
    void onConfirm(DialogInterface dialog);
    void onCancel(DialogInterface dialog);
}
