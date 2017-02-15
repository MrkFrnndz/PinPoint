//package com.example.markfernandez.pinpoint;
//
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.squareup.picasso.Picasso;
//
///**
// * Created by AstroNuts on 1/12/2017.
// */
//public class View_Holder extends RecyclerView.ViewHolder {
//    View mView;
//    CardView cv;
////    TextView description,authorname,datecreated;
////    ImageView iv_Emoticon;
//
//
//    View_Holder(View itemView) {
//        super(itemView);
//        itemView = mView;
//        cv = (CardView) itemView.findViewById(R.id.cardView);
////        iv_Emoticon = (ImageView) itemView.findViewById(R.id.iv_Emoticon);
////        description = (TextView) itemView.findViewById(R.id.txtDescription);
////        authorname = (TextView) itemView.findViewById(R.id.txtAuthorName);
////        datecreated = (TextView) itemView.findViewById(R.id.txtDateCreated);
//    }
//
//    public void setAuthorImage(String authorImage){
//        ImageView post_authorimage = (ImageView)itemView.findViewById(R.id.iv_userImage);
//        Picasso.with().load().into(post_authorimage);
//
//    }
//
//    public void setAuthorName(String authorName){
//        TextView post_authorname = (TextView) itemView.findViewById(R.id.txtAuthorName);
//        post_authorname.setText(authorName);
//    }
//
//    public void setDateCreated(String dateCreatedLong){
//        TextView post_date = (TextView) itemView.findViewById(R.id.txtAuthorName);
//        post_date.setText(dateCreatedLong);
//    }
//
//    public void setPostDescription(String postDescription){
//        TextView post_description = (TextView) itemView.findViewById(R.id.txtAuthorName);
//        post_description.setText(postDescription);
//    }
//}