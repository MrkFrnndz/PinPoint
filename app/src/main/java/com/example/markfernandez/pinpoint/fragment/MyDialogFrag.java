package com.example.markfernandez.pinpoint.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.markfernandez.pinpoint.LatLngEvent;
import com.example.markfernandez.pinpoint.R;
import com.example.markfernandez.pinpoint.model.UserPost;
import com.example.markfernandez.pinpoint.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AstroNuts on 1/19/2017.
 */
public class MyDialogFrag extends DialogFragment  {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    private View rootView;
    private Button btnPin;
    private ImageButton iv_smile,iv_sad,iv_love,iv_angry;
    private EditText etYourPost;
    private int selectedEmoticon;

    //Variables for full name
    double mLat,mLng;
    String mMapAdd;
    LatLngEvent latlngReceived = new LatLngEvent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        rootView = inflater.inflate(R.layout.activity_mydf, container, false);


        //UI CASTING
        iv_smile = (ImageButton) rootView.findViewById(R.id.iv_smile);
            iv_smile.setOnClickListener(emoticonSelection);
        iv_love = (ImageButton) rootView.findViewById(R.id.iv_love);
            iv_love.setOnClickListener(emoticonSelection);
        iv_sad = (ImageButton) rootView.findViewById(R.id.iv_sad);
            iv_sad.setOnClickListener(emoticonSelection);
        iv_angry = (ImageButton) rootView.findViewById(R.id.iv_angry);
            iv_angry.setOnClickListener(emoticonSelection);


        btnPin= (Button) rootView.findViewById(R.id.btnPin);
            btnPin.setOnClickListener(pinPushed);
        etYourPost = (EditText)rootView.findViewById(R.id.etYourPost);

        return rootView;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_mydf);
        return dialog;
    }

    //FOR EMOTICON SELECTION
    View.OnClickListener emoticonSelection = new View.OnClickListener(){
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.iv_smile:
                    iv_smile.setTag(R.drawable.ic_joy_36dp);
                    selectedEmoticon = getDrawableId(iv_smile);
                    break;
                case R.id.iv_love:
                    iv_love.setTag(R.drawable.ic_love_36dp);
                    selectedEmoticon = getDrawableId(iv_love);
                    break;
                case R.id.iv_sad:
                    iv_sad.setTag(R.drawable.ic_sad_36dp);
                    selectedEmoticon = getDrawableId(iv_sad);
                    break;
                case R.id.iv_angry:
                    iv_angry.setTag(R.drawable.ic_angry_36dp);
                    selectedEmoticon = getDrawableId(iv_angry);
                    break;
            }
        }
    };

    //Getting the ImageView id
    private int getDrawableId(ImageView iv) {
        return (Integer) iv.getTag();
    }

    View.OnClickListener pinPushed = new View.OnClickListener(){
        public void onClick(View v) {
            if (v == btnPin){
                postMethod();
            }
        }

    };

    private void postMethod(){
        mDatabase.child("users").child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                String authorimage = userProfile.getUserImage().toString().trim();
                String author = userProfile.getUserName().toString().trim();
                String uid = mUserId;
                int setemoticon = selectedEmoticon;
                String postData = etYourPost.getText().toString().trim();
                HashMap <String,Object> mDate = new HashMap<>();
                mDate.put("dateCreated", ServerValue.TIMESTAMP);

                mMapAdd = latlngReceived.getMapAddress();
                mLat = latlngReceived.getLat();
                mLng = latlngReceived.getLng();


                if(TextUtils.isEmpty(postData)){
                    Toast.makeText(getContext(),"EMPTY POST!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }

                if(selectedEmoticon == 0){
                    Toast.makeText(getContext(),"EMOJI MARKER REQUIRE!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }

                if(!TextUtils.isEmpty(postData) && selectedEmoticon != 0){

                    String key = mDatabase.child("post").push().getKey();
                    UserPost userPost = new UserPost(authorimage,author,uid,postData,setemoticon,mDate,mMapAdd,mLat,mLng);
                    Map<String, Object> postValues = userPost.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/post/" + key,postValues);
                    childUpdates.put("/user-post/" + uid + "/" + key, postValues);
                    mDatabase.updateChildren(childUpdates);

                    Toast.makeText(getContext(),"PIN POSTED!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

                etYourPost.setText("");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onEvent(LatLngEvent event) {
        latlngReceived.setMapAddress(event.getMapAddress());
        latlngReceived.setLat(event.getLat());
        latlngReceived.setLng(event.getLng());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
