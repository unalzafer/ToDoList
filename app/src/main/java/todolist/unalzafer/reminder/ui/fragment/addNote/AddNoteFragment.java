package todolist.unalzafer.reminder.ui.fragment.addNote;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseFragment;
import todolist.unalzafer.reminder.model.NotesModel;

import static android.support.constraint.Constraints.TAG;

public class AddNoteFragment extends BaseFragment implements AddNoteView.View {

    private AddNoteView.Presenter presenter;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etText)
    EditText etText;
    @BindView(R.id.etDate)
    EditText etDate;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.tvAppBar)
    TextView tvAppBar;
    @BindView(R.id.btSaveNote)
    Button btSaveNote;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    String color= String.valueOf(R.color.colorRandom1);
    String pushDatabaseId;
    @BindView(R.id.ibColorBlue)  ImageButton ibColorBlue;
    @BindView(R.id.ibColorPurble) ImageButton ibColorPurble;
    @BindView(R.id.ibColorGreen) ImageButton ibColorGreen;
    @BindView(R.id.ibColorOrange) ImageButton ibColorOrange;
    @BindView(R.id.ibColorRed) ImageButton ibColorRed;
    @BindView(R.id.ibColorPink) ImageButton ibColorPink;

    Calendar dateTime;
    //Detail
    @BindView(R.id.ivDelete) ImageView ivDelete;
    @BindView(R.id.ivUpdate)ImageView ivUpdate;
    @BindView(R.id.ivSync)ImageView ivSync;
    private static final String bNote = "putNote";

    private String noteID;
    private Object date,selectDate;

    @BindView(R.id.ll_colors) LinearLayout ll_colors;
    @BindView(R.id.linearEdit) LinearLayout linearEdit;

    public AddNoteFragment() {
        // Required empty public constructor
    }
    public static AddNoteFragment newInstance() {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public static AddNoteFragment newInstance(String noteID) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString(bNote, noteID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter==null)
            presenter=new AddNotePresenter(this);

        if (getArguments() != null) {
            noteID = getArguments().getString(bNote);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        ibColorBlue.setOnClickListener(new setOnClickColorList());
        ibColorPurble.setOnClickListener(new setOnClickColorList());
        ibColorGreen.setOnClickListener(new setOnClickColorList());
        ibColorOrange.setOnClickListener(new setOnClickColorList());
        ibColorRed.setOnClickListener(new setOnClickColorList());
        ibColorPink.setOnClickListener(new setOnClickColorList());

        if(noteID!=null){
            presenter.getDetailNote(noteID);
            etTitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            etText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            etText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

            etTitle.setEnabled(false);
            etText.setEnabled(false);
            etDate.setEnabled(false);
            ll_colors.setVisibility(View.GONE);
            btSaveNote.setVisibility(View.GONE);
            linearEdit.setVisibility(View.VISIBLE);
            tvState.setVisibility(View.GONE);
            tvAppBar.setText("Detay");

        }else {
            btSaveNote.setVisibility(View.VISIBLE);
            linearEdit.setVisibility(View.GONE);
            ibColorBlue.performClick();
            tvState.setVisibility(View.GONE);
            tvAppBar.setText("Ekle");
        }

    }

    @OnClick(R.id.btSaveNote)
    void saveNote(){
        showProgress();
        if (!etTitle.getText().toString().equals("") && !etText.getText().toString().equals("") && !etDate.getText().toString().equals("")) {
            presenter.addNewNote(etTitle.getText().toString(), etText.getText().toString(), color,selectDate);
            getFragmentManager().popBackStack();
            hideProgress();
        } else {
            showAlert(getString(R.string.alert),getString(R.string.noteNone));
            hideProgress();
        }
    }
    @OnClick(R.id.ivDelete)
    void delete() {
        presenter.removeNote(noteID);
    }
    @OnClick(R.id.ivUpdate)
    void update(){
        //Güncelleme için edittext'leri düznelenebilir yap
        etTitle.setEnabled(true);
        etText.setEnabled(true);
        etDate.setEnabled(true);

        ivUpdate.setVisibility(View.GONE);
        ivSync.setVisibility(View.VISIBLE);
        ll_colors.setVisibility(View.VISIBLE);
        selectColor(color);
        tvState.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivSync)
    void saveSync(){
        //Güncelleme işelemi başlat
        etTitle.setEnabled(false);
        etText.setEnabled(false);
        etDate.setEnabled(false);

        ivSync.setVisibility(View.GONE);
        ll_colors.setVisibility(View.GONE);
        tvState.setVisibility(View.GONE);
        ivUpdate.setVisibility(View.VISIBLE);
        presenter.updateNote(noteID,etTitle.getText().toString(), etText.getText().toString(), color,selectDate,date,tvState.getText().toString());

    }

    @Override
    public void onDetailNote(NotesModel notesModel) {
        if(notesModel!=null) {
            etTitle.setText(notesModel.getTitle());
            etText.setText(notesModel.getText());
            color = notesModel.getColor();
            date=notesModel.getDate();
            selectDate=notesModel.getSelectDate();
            etDate.setText(dateFormat(selectDate));
            if(notesModel.getState()!=null&&!notesModel.getState().equals("")){
                tvState.setText(notesModel.getState());
            }else
                tvState.setText("Tamamlanmadı");


        }
    }

    @Override
    public void removeNoteSuccess() {
        getFragmentManager().popBackStack();
    }

    private class setOnClickColorList implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id=view.getId();
            ibColorBlue.setImageResource(0);
            ibColorPurble.setImageResource(0);
            ibColorGreen.setImageResource(0);
            ibColorOrange.setImageResource(0);
            ibColorRed.setImageResource(0);
            ibColorPink.setImageResource(0);
            switch (id){
                case R.id.ibColorBlue:
                    ibColorBlue.setImageResource(R.drawable.ic_checked);
                    color="blue";
                    break;
                case R.id.ibColorPurble:
                    ibColorPurble.setImageResource(R.drawable.ic_checked);
                    color="purble";
                    break;
                case R.id.ibColorGreen:
                    ibColorGreen.setImageResource(R.drawable.ic_checked);
                    color= "green";
                    break;
                case R.id.ibColorOrange:
                    ibColorOrange.setImageResource(R.drawable.ic_checked);
                    color= "orange";
                    break;
                case R.id.ibColorRed:
                    ibColorRed.setImageResource(R.drawable.ic_checked);
                    color= "red";
                    break;
                case R.id.ibColorPink:
                    ibColorPink.setImageResource(R.drawable.ic_checked);
                    color= "pink";
                    break;
            }
        }
    }

    private void selectColor(String colorName){
        switch (colorName){
            case "blue":
                ibColorBlue.performClick();
                break;
            case "purble":
                ibColorPurble.performClick();
                break;
            case "green":
                ibColorGreen.performClick();
                break;
            case "orange":
                ibColorOrange.performClick();
                break;
            case "red":
                ibColorRed.performClick();
                break;
            case "pink":
                ibColorPink.performClick();
                break;
        }
    }


    @OnClick(R.id.etDate)
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        dateTime = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTime.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dateTime.set(Calendar.MINUTE, minute);
                        Log.v("datetime", dateTime.getTimeInMillis()+ "---- " + dateTime.getTime());
                        etDate.setText(dateFormat(dateTime.getTimeInMillis()));
                        selectDate=dateTime.getTimeInMillis();

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    @OnClick(R.id.tvState)
    void state(){
        if(tvState.getText().toString()=="Tamamlanmadı")
            tvState.setText("Tamamlandı");
        else
            tvState.setText("Tamamlanmadı");
    }

    @OnClick(R.id.ivShare)
    public void shareMore(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, etTitle.getText().toString()+"\n\n"+etText.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
