package todolist.unalzafer.reminder.base;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import todolist.unalzafer.reminder.ui.activity.main.MainActivity;

public abstract class BaseDialogFragment extends DialogFragment {
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }
    }
    public void showProgress(){
        if(getActivity()!=null){
            if(getActivity() instanceof MainActivity){
                ((MainActivity)getActivity()).showProgress();
            }
        }
    }

    public void hideProgress(){
        if(getActivity()!=null)
        {
            if(getActivity() instanceof MainActivity){
                ((MainActivity)getActivity()).hideProgress();
            }
        }
    }


}
