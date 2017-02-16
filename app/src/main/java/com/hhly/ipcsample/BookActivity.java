package com.hhly.ipcsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * @创建者 frank
 * @时间 2017/2/15 20:58
 * @描述：${TODO}
 */

public class BookActivity extends AppCompatActivity {

    private static final String TAG = "BookActivity";
    private IBookManager mIBookManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBookManager = IBookManager.Stub.asInterface(service);
            List<Book> list = null;

            try {
                list = mIBookManager.getBookList();
                Log.i(TAG, "query book list, list type:"
                        + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list:" + list.toString());
                Book newBook = new Book(3, "Android进阶");
                mIBookManager.addBook(newBook);
                Log.i(TAG, "add book:" + newBook);
                List<Book> newList = mIBookManager.getBookList();
                Log.i(TAG, "query book list:" + newList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, BookService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mIBookManager = null;
    }
}
