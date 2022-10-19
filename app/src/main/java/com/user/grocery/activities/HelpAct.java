package com.user.grocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.user.grocery.R;
import com.user.grocery.adapters.FaqsAdapter;
import com.user.grocery.databinding.ActivityHelp2Binding;
import com.user.grocery.databinding.ActivityHelpBinding;
import com.user.grocery.models.SuccessResGetHelp;
import com.user.grocery.retrofit.ApiClient;
import com.user.grocery.retrofit.GroceryInterface;
import com.user.grocery.utility.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.grocery.retrofit.Constant.showToast;

public class HelpAct extends AppCompatActivity {

    ActivityHelp2Binding binding;

    private GroceryInterface apiInterface;

    private ArrayList<SuccessResGetHelp.Result> faqsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_help2);
       apiInterface = ApiClient.getClient().create(GroceryInterface.class);
       binding.header.imgHeader.setOnClickListener(v -> finish());
       binding.header.tvtitle.setText(getString(R.string.help));
       getFaqs();
    }

    public void getFaqs()
    {
        DataManager.getInstance().showProgressMessage(HelpAct.this, getString(R.string.please_wait));

        Map<String, String> map = new HashMap<>();
        Call<SuccessResGetHelp> call = apiInterface.getHelp(map);
        call.enqueue(new Callback<SuccessResGetHelp>() {
            @Override
            public void onResponse(Call<SuccessResGetHelp> call, Response<SuccessResGetHelp> response) {
                DataManager.getInstance().hideProgressMessage();
                try {

                    SuccessResGetHelp data = response.body();
                    if (data.success==1) {

                        faqsList.clear();
                        faqsList.addAll(data.getResult());

                        binding.rvScheduleTime.setHasFixedSize(true);
                        binding.rvScheduleTime.setLayoutManager(new LinearLayoutManager(HelpAct.this));
                        binding.rvScheduleTime.setAdapter(new FaqsAdapter(HelpAct.this,faqsList));

                    } else {
                        showToast(HelpAct.this, data.message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SuccessResGetHelp> call, Throwable t) {

                call.cancel();
                DataManager.getInstance().hideProgressMessage();

            }
        });

    }


}