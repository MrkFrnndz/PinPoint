//package com.example.markfernandez.pinpoint;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.markfernandez.pinpoint.model.UserPost;
////import com.example.markfernandez.pinpoint.model.UserProfile;
//
//import java.text.SimpleDateFormat;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by AstroNuts on 1/12/2017.
// */
//public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder>{
//
//    @Override
//    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(View_Holder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
////    List<UserPost> list;
////    Context context;
////
////    public Recycler_View_Adapter(List<UserPost> list, Context context) {
////        this.list = list;
////        this.context = context;
////    }
////
////    @Override
////    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
////        //Inflate the layout, initialize the View Holder
////        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
////        View_Holder holder = new View_Holder(v);
////        return holder;
////    }
////
////
////
////    @Override
////    public void onBindViewHolder(View_Holder holder, int position) {
//////        holder.title.setText(list.get(position).title);
//////        holder.description.setText(list.get(position).description);
//////        holder.imageView.setImageResource(list.get(position).imageId);
////        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
////        UserPost current =  list.get(position);
////        holder.iv_Emoticon.setImageResource(current.getPostEmotion());
////        holder.description.setText(current.getPostDescription());
////        holder.authorname.setText(current.getAuthorName());
////
//////        long number = current.getDateCreatedLong();
//////        String numberAsString = Long.toString(number);
//////        holder.datecreated.setText(numberAsString);
////
////
//////        UserPost upDate = new UserPost();
//////        long date = upDate.getDateCreatedLong();
//////
//////        Log.e("MARK log", "dateCreatedLong is " + date);
////        //animate(holder);
////    }
////
////    @Override
////    public int getItemCount() {
////        //returns the number of elements the RecyclerView will display
////        return list.size();
////    }
////
////    @Override
////    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
////        super.onAttachedToRecyclerView(recyclerView);
////    }
////
////    // Insert a new item to the RecyclerView on a predefined position
////    public void insert(int position, UserPost user) {
////        list.add(position, user);
////        notifyItemInserted(position);
////    }
////
////    // Remove a RecyclerView item containing a specified Data object
////    public void remove(UserPost user) {
////        int position = list.indexOf(user);
////        list.remove(position);
//////        notifyItemRemoved(position);
////    }
//
//
//}
