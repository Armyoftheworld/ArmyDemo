package com.juziwl.autosmsinput;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/31
 * @description 应用程序Activity的模板类
 */

public class SmsObserver extends ContentObserver {
    private Context context;
    private Handler handler;
    private Uri inboxUri = Uri.parse("content://sms/inbox");
    public static final int SMSRECRIVERCODE = 1234;

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
        this.handler = handler;
    }

    public void registerContentObserver() {
        context.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, this);
    }

    public void unRegisterContentObserver() {
        context.getContentResolver().unregisterContentObserver(this);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        System.out.println("uri = " + uri.toString());
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Cursor cursor = context.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            handler.obtainMessage(SMSRECRIVERCODE, new String[]{address, body}).sendToTarget();
        }
    }
}
