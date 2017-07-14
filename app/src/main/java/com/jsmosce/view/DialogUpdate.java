package com.jsmosce.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jsmosce.R;

public class DialogUpdate extends AlertDialog implements View.OnClickListener {

    private Context context;

    protected DialogUpdate(@NonNull Context context) {
        super(context);
    }

    public DialogUpdate(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected DialogUpdate(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_updata, null);
        Button updataNo = (Button) layout.findViewById(R.id.updata_no);
        Button updataYes = (Button) layout.findViewById(R.id.updata_yes);
        updataNo.setOnClickListener(this);
        updataYes.setOnClickListener(this);
        this.setContentView(layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updata_no:
                dismiss();
                break;

            case R.id.updata_yes:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://qiniu-app.pgyer.com/0743d3e9031fada4f9d1f7d4db73ef66.apk?e=1498466140&attname=app-release.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:tLzVAkv1SLOSQZxTBKPFHb0oIEc=&sign=4641b8e7ca057807349b4c01f4a1c34c&t=5950c75c");
                intent.setData(content_url);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
        }
    }

    public interface getString {
        public void onClick(String name, String score);
    }


}
