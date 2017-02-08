package com.example.markfernandez.pinpoint;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by AstroNuts on 1/12/2017.
 */
public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView description,authorname,datecreated;
    ImageView iv_Emoticon;


    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        iv_Emoticon = (ImageView) itemView.findViewById(R.id.iv_Emoticon);
        description = (TextView) itemView.findViewById(R.id.txtDescription);
        authorname = (TextView) itemView.findViewById(R.id.txtAuthorName);
        datecreated = (TextView) itemView.findViewById(R.id.txtDateCreated);
    }
}