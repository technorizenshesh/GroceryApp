package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.google.gson.Gson;
import com.user.grocery.R;
import com.user.grocery.adapters.One2OneChatAdapter;
import com.user.grocery.databinding.ActivityOne2OneChatBinding;
import com.user.grocery.models.SuccessResGetChat;
import com.user.grocery.models.SuccessResInsertChat;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;
import com.user.grocery.utility.SharedPreferenceUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.USER_ID;
import static com.user.grocery.retrofit.Constant.showToast;

public class One2OneChatAct extends AppCompatActivity {

    ActivityOne2OneChatBinding binding;

    private GroceryInterface apiInterface;

    One2OneChatAdapter chatAdapter;

    private String name = "",id = "", image ="",strChatMessage = "";

    List<SuccessResGetChat.Result> chatList = new LinkedList<>();

    Timer timer = new Timer();

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_one2_one_chat);

       apiInterface = ApiClient.getClient().create(GroceryInterface.class);

       binding.header.tvtitle.setText(getString(R.string.chat_details));
       binding.header.imgHeader.setOnClickListener(v -> finish());

       userId = SharedPreferenceUtility.getInstance(this).getString(USER_ID);

       getChat(true);

       timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(isLastVisible()){
                    getChat(false);
                }
            }
        },0,5000);

       binding.ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strChatMessage = binding.etChat.getText().toString();
                if(!strChatMessage.equals(""))
                {
                    insertChat();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private boolean isLastVisible() {
        if (chatList != null && chatList.size() != 0) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvChat.getLayoutManager());
            int pos = layoutManager.findLastCompletelyVisibleItemPosition();
            int numItems = binding.rvChat.getAdapter().getItemCount();
            return (pos >= numItems - 1);
        }
        return false;
    }

    private void getChat(boolean show) {

        String userId = SharedPreferenceUtility.getInstance(One2OneChatAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(One2OneChatAct.this, getString(R.string.please_wait));
        Log.d("datatest",userId);
        Map<String,String> map = new HashMap<>();
        map.put("sender_id","01");
        map.put("receiver_id",userId);

        Call<SuccessResGetChat> call = apiInterface.getChat(map);
        call.enqueue(new Callback<SuccessResGetChat>() {
            @Override
            public void onResponse(Call<SuccessResGetChat> call, Response<SuccessResGetChat> response) {

                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResGetChat data = response.body();
                    Log.e("data",data.success+"");
                    if (data.success==1) {
                        String dataResponse = new Gson().toJson(response.body());
                        chatList.clear();
                        chatList.addAll(data.getResult());

                        binding.rvChat.setLayoutManager(new LinearLayoutManager(One2OneChatAct.this));
                        binding.rvChat.setAdapter(new One2OneChatAdapter(One2OneChatAct.this,chatList,userId));
                        binding.rvChat.scrollToPosition(chatList.size()-1);

                    } else if (data.success==0) {
                        showToast(One2OneChatAct.this, data.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<SuccessResGetChat> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    public void insertChat() {
        binding.etChat.setText("");
        String userId = SharedPreferenceUtility.getInstance(One2OneChatAct.this).getString(USER_ID);
        DataManager.getInstance().showProgressMessage(this, getString(R.string.please_wait));
        Map<String,String> map = new HashMap<>();
        map.put("sender_id",userId);
        map.put("receiver_id","01");
        map.put("chat_message",strChatMessage);

        Call<SuccessResInsertChat> call = apiInterface.insertChat(map);
        call.enqueue(new Callback<SuccessResInsertChat>() {
            @Override
            public void onResponse(Call<SuccessResInsertChat> call, Response<SuccessResInsertChat> response) {
                DataManager.getInstance().hideProgressMessage();
                try {
                    SuccessResInsertChat data = response.body();
                    Log.e("data",data.status);
                    String dataResponse = new Gson().toJson(response.body());
                    if (data.status.equals("1")) {
                        getChat(false);
//                        SessionManager.writeString(RegisterAct.this, Constant.driver_id,data.result.id);
//                        App.showToast(RegisterAct.this, data.message, Toast.LENGTH_SHORT);
                    } else if (data.status.equals("0")) {
                        showToast(One2OneChatAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessResInsertChat> call, Throwable t) {
                call.cancel();
                DataManager.getInstance().hideProgressMessage();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}