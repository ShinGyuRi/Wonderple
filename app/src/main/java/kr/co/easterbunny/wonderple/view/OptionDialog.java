package kr.co.easterbunny.wonderple.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.google.gson.JsonObject;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.DlgFollowReportBinding;
import kr.co.easterbunny.wonderple.library.util.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by scona on 2017-03-23.
 */

public class FollowReportDialog extends Dialog {

    private DlgFollowReportBinding binding;

    private Context context;

    String uid = null;
    String auid = null;

    public FollowReportDialog(@NonNull Context context, String uid, String auid) {
        super(context);
        this.context = context;
        this.uid = uid;
        this.auid = auid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dlg_follow_report, null, false);
        setContentView(binding.getRoot());
        binding.setFollowReport(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_follow:
                follow();
                dismiss();
                break;

            case R.id.btn_report:
                displayReport();
                break;
        }
    }

    private void follow() {
        Call<JsonObject> jsonObjectCall = NetworkUtil.getInstace().followApply(uid, auid);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String message = jsonObject.get("message").toString().replace("\"", "");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void displayReport() {

    }
}
