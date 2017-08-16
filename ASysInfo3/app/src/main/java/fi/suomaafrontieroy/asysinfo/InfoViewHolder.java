package fi.suomaafrontieroy.asysinfo;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public abstract class InfoViewHolder extends RecyclerView.ViewHolder {


    public TextView title;


    public InfoViewHolder(View view) {
        super(view);


        this.title = (TextView) view.findViewById(R.id.cardTitle);

    }


}