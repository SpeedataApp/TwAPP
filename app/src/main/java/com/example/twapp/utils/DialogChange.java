package com.example.twapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.db.TwBody;

public class DialogChange {
    private static EditText pNnm, runNum, name, age, gender, bednum, result;
    private static DBUitl dbUitl = new DBUitl();
    private static String RuningNumber ;
    public static void showCustomizeDialog(final Context context, String flag) {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_change_layout, null);
        pNnm = dialogView.findViewById(R.id.people_num);
        runNum = dialogView.findViewById(R.id.run_num);
        name = dialogView.findViewById(R.id.edittext_name);
        age = dialogView.findViewById(R.id.edittext_age);
        gender = dialogView.findViewById(R.id.edittext_gender);
        bednum = dialogView.findViewById(R.id.edittext_bed_num);
        result = dialogView.findViewById(R.id.edittext_result);
        customizeDialog.setTitle("修改数据");
        customizeDialog.setView(dialogView);

        setData(flag);
        customizeDialog.setPositiveButton("确定修改",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changDatas(context);
                        Intent intent=new Intent();
                        intent.setAction("com.update");
                        context.sendBroadcast(intent);
                    }
                });
        customizeDialog.show();

    }

    public static void setData(String flag) {

        TwBody body = dbUitl.queryTwBody(flag);
        RuningNumber=body.getRunningNumber();
        pNnm.setText(body.getPeopleNun());
        name.setText(body.getPName());
        age.setText(body.getPaAge());
        gender.setText(body.getPGender());
        bednum.setText(body.getPBedNumber());
        runNum.setText(body.getRunningNumber());

    }

    public static void changDatas(Context context) {
        String pnum = String.valueOf(pNnm.getText());
        String runnum = String.valueOf(runNum.getText());
        String names = String.valueOf(name.getText());
        String ages = String.valueOf(age.getText());
        String genders = String.valueOf(gender.getText());
        String bednums = String.valueOf(bednum.getText());

        if (!pnum.equals("") && !runnum.equals("") && !names.equals("") && !ages.equals("")
                && !genders.equals("") && !bednums.equals("")) {
            dbUitl.dialogUpData(RuningNumber,runnum, pnum, names, ages, genders, bednums);
            Toast.makeText(context, "修改成功！", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "信息不能为空！", Toast.LENGTH_SHORT).show();
        }
    }
}
