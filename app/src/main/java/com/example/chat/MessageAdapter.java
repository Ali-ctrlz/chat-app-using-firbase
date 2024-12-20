package com.example.chat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {


    private ArrayList<Messages> messages;
    private String senderImg, receiverImg;
    private Context context;

    public MessageAdapter(ArrayList<Messages> messages, String senderImg, String receiverImg, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.receiverImg = receiverImg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.message_holder,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.txtMessage.setText(messages.get(position).getContent());

        ConstraintLayout constraintlayout=holder.ccll;

        if(messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            Glide.with(context).load(senderImg).error(R.drawable.account_img).into(holder.profImage);

            ConstraintSet constraintSet=new ConstraintSet();
            constraintSet.clone(constraintlayout);
            constraintSet.clear(R.id.profile_cardView,ConstraintSet.LEFT);
            constraintSet.clear(R.id.message_content,ConstraintSet.LEFT);
            constraintSet.connect(R.id.profile_cardView,ConstraintSet.RIGHT,R.id.ccLayout,ConstraintSet.RIGHT,0);
            constraintSet.connect(R.id.message_content,ConstraintSet.RIGHT,R.id.profile_cardView,ConstraintSet.LEFT,0);
            constraintSet.applyTo(constraintlayout);
        }else{
            Glide.with(context).load(receiverImg).error(R.drawable.account_img).into(holder.profImage);
            ConstraintSet constraintSet=new ConstraintSet();
            constraintSet.clone(constraintlayout);
            constraintSet.clear(R.id.profile_cardView,ConstraintSet.RIGHT);
            constraintSet.clear(R.id.message_content,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.profile_cardView,ConstraintSet.LEFT,R.id.ccLayout,ConstraintSet.LEFT,0);
            constraintSet.connect(R.id.message_content,ConstraintSet.LEFT,R.id.profile_cardView,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(constraintlayout);


        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{

        ConstraintLayout ccll;
        TextView txtMessage;
        ImageView profImage;
       public MessageHolder(View itemView){
           super(itemView);
           ccll=itemView.findViewById(R.id.ccLayout);
           profImage=itemView.findViewById(R.id.small_profile_img);
           txtMessage=itemView.findViewById(R.id.message_content);

   }

    }

}
