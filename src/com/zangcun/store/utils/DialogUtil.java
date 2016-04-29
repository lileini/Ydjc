package com.zangcun.store.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zangcun.store.R;

public  class DialogUtil {
    public static void dialogUser(Context ctx, String message) {
        LayoutInflater inflaterDl = LayoutInflater.from(ctx);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(R.layout.showdialog, null);
        final Dialog dialog = new AlertDialog.Builder(ctx).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        TextView tv = (TextView) layout.findViewById(R.id.dialog_text);
        tv.setText(message);
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
