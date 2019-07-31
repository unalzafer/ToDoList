package todolist.unalzafer.reminder.ui.activity.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import todolist.unalzafer.reminder.R;
import todolist.unalzafer.reminder.base.BaseActivity;
import todolist.unalzafer.reminder.listener.CustomItemClickListener;
import todolist.unalzafer.reminder.model.NotesModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView text;
        public TextView tvDate;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.tvTitle);
            text = (TextView)view.findViewById(R.id.tvText);
            cardView=(CardView)view.findViewById(R.id.card_view);
            tvDate = (TextView)view.findViewById(R.id.tvDate);

        }
    }

    ArrayList<NotesModel> notesModelArrayList;
    CustomItemClickListener listener;

    public NoteAdapter(ArrayList<NotesModel> notesModelArrayList, CustomItemClickListener listener) {

        this.notesModelArrayList = notesModelArrayList;
        this.listener = listener;
    }


    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_adapter, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);

        Log.d("firebase :","title->"+notesModelArrayList.size());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(v, view_holder.getPosition());
            }
        });




        return view_holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text.setText(notesModelArrayList.get(position).getText());
        holder.title.setText(notesModelArrayList.get(position).getTitle());
        if(notesModelArrayList.get(position).getSelectDate()!=null)
            holder.tvDate.setText( new SimpleDateFormat("dd/MM/yy").format(notesModelArrayList.get(position).getSelectDate()));
        if(notesModelArrayList.get(position).getState()!=null && notesModelArrayList.get(position).getState().equals("Tamamlandı"))
            holder.tvDate.setText( notesModelArrayList.get(position).getState());

        holder.text.setMaxLines(6);


        int color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom1);
        switch (notesModelArrayList.get(position).getColor()){
            case "blue":
                color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom1);
                break;
            case "purble":
                color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom2);
                break;
            case "green":
                color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom3);
                break;
            case "orange":
                color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom4);
                break;
            case "red":
                color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom5);
                break;
            case "pink":
                color = holder.cardView.getContext().getResources().getColor(R.color.colorRandom6);
                break;
        }
        holder.cardView.setCardBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return notesModelArrayList.size();
    }

}