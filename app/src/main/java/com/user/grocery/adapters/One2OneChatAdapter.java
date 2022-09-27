package com.user.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.grocery.R;
import com.user.grocery.databinding.AdapterChatBinding;
import com.user.grocery.models.SuccessResGetChat;

import java.util.LinkedList;
import java.util.List;


public class One2OneChatAdapter extends RecyclerView.Adapter<One2OneChatAdapter.StoriesViewHolder> {

    private Context context;
    AdapterChatBinding binding;
    private List<SuccessResGetChat.Result> chatList = new LinkedList<>();
    String myId;

    public One2OneChatAdapter(Context context, List<SuccessResGetChat.Result> chatList, String myId)
    {
        this.context = context;
        this.chatList = chatList;
        this.myId = myId;
    }


    @NonNull
    @Override
    public StoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= AdapterChatBinding.inflate(LayoutInflater.from(context));
        return new StoriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesViewHolder holder, int position) {

        RelativeLayout chatLeftMsgLayout = holder.itemView.findViewById(R.id.chat_left_msg_layout);
        RelativeLayout chatRightMsgLayout = holder.itemView.findViewById(R.id.chat_right_msg_layout);


        TextView tvTimeRight = holder.itemView.findViewById(R.id.tv_time_right);
        TextView chatRightMsgTextView = holder.itemView.findViewById(R.id.chat_right_msg_text_view);

        TextView tvTimeLeft = holder.itemView.findViewById(R.id.tv_time_left);
        TextView chatLeftMsgTextView = holder.itemView.findViewById(R.id.chat_left_msg_text_view);

        if(myId.equalsIgnoreCase(chatList.get(position).getSenderId()))
        {
            chatLeftMsgLayout.setVisibility(View.GONE);
            chatRightMsgLayout.setVisibility(View.VISIBLE);
            tvTimeRight.setText(chatList.get(position).getTimeAgo());
            chatRightMsgTextView.setText(chatList.get(position).getChatMessage());
        }
        else
        {
            chatLeftMsgLayout.setVisibility(View.VISIBLE);
            chatRightMsgLayout.setVisibility(View.GONE);
            tvTimeLeft.setText(chatList.get(position).getTimeAgo());
            chatLeftMsgTextView.setText(chatList.get(position).getChatMessage());
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class StoriesViewHolder extends RecyclerView.ViewHolder {
        public StoriesViewHolder(AdapterChatBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
