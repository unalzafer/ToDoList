package todolist.unalzafer.reminder.base;

import android.support.v4.app.Fragment;

import java.text.SimpleDateFormat;

import todolist.unalzafer.reminder.ui.activity.main.MainActivity;
import todolist.unalzafer.reminder.ui.fragment.alertdialog.AlertDialogFragment;

public class BaseFragment extends Fragment {

    public void showProgress() {
        if (getActivity() != null)
            ((MainActivity) getActivity()).showProgress();
    }

    public void hideProgress() {
        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgress();
    }
    public  void showAlert( String title,String message){
        AlertDialogFragment.newInstance(title,message).show(getFragmentManager(),AlertDialogFragment.class.getSimpleName());
    }
    public String dateFormat(Object time){
        if(time!=null) {
            long date = Long.parseLong("" + time);
            return new SimpleDateFormat("dd/MM/yy HH:mm").format(date);
        }else
            return "0";
    }
}
