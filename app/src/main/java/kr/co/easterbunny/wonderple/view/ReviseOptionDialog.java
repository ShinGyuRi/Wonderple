package kr.co.easterbunny.wonderple.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.DlgReviseReportBinding;

/**
 * Created by scona on 2017-03-27.
 */

public class ReviseReportDialog extends ParentDialog {

    private DlgReviseReportBinding binding;

    Context context;
    String iid;
    String uid;

    public ReviseReportDialog(@NonNull Context context, String iid, String uid) {
        super(context);
        this.context = context;
        this.iid = iid;
        this. uid = uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dlg_revise_report, null, false);
        setContentView(binding.getRoot());
        binding.setRevise(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                cancel();
                break;

            case R.id.btn_return_report:
                sendReport(iid, uid, re);
                break;

            case R.id.btn_revise:
                break;
        }
    }
}
