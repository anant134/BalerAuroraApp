package com.smartech.tour.gigantestourbooking;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.whty.smartpos.gigantestourbooking.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zengyahui on 2017-10-24.
 */

public class ResultFragment extends Fragment {

    private TextView title, showResult;
    private Button clear;
    private ScrollView scrollView;
    private Handler uiHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        uiHandler = new UIHandler();
        title = (TextView) view.findViewById(R.id.title);
        showResult = (TextView) view.findViewById(R.id.show_result);
        clear = (Button) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult.setText("");
            }
        });
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        return view;
    }

    public void appendLog(String result) {
        uiHandler.obtainMessage(0, result).sendToTarget();
    }

    public void showTitle(String title) {
        uiHandler.obtainMessage(1, title).sendToTarget();
    }

    class UIHandler extends Handler {

        private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    showResult.append(sdf.format(new Date()) + "\n" + msg.obj + "\n");
                    scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            scrollView.post(new Runnable() {
                                public void run() {
                                    scrollView.fullScroll(View.FOCUS_DOWN);
                                }
                            });
                        }
                    });
                    break;
                case 1:
                    title.setText((String) msg.obj);
                    break;
            }
        }
    }
}
