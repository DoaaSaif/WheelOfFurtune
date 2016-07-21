package com.doaasaif.wheelfortune;


import android.content.Context;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.doaasaif.wheelfortune.app.AppController;
import com.doaasaif.wheelfortune.listener.VolleyListener;
import com.doaasaif.wheelfortune.model.WheelOperation;
import com.doaasaif.wheelfortune.server.ServerOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.pivotX;
import static android.R.attr.pivotY;

/**
 * Created by shabegt8040 on 7/18/2016.
 */

public class WheelScreen extends AppCompatActivity implements VolleyListener, View.OnClickListener {

    ServerOperations serverOperations = new ServerOperations();

    //--------------Views-------------
    ImageView wheelImage;
    ImageButton play_btn;

    //---------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_screen_activity);
        bindViews();


    }

    private void bindViews() {
        wheelImage = (ImageView) findViewById(R.id.wheel_image_view);

        play_btn = (ImageButton) findViewById(R.id.play_btn);
        play_btn.setOnClickListener(this);
    }


    @Override
    public void onRemoteCallComplete(ArrayList<Parcelable> result) {

    }

    //called when api executeed
    @Override
    public void onRemoteCallComplete(Parcelable result) {
        WheelOperation operation = (WheelOperation) result;
        String prizeId;
        String prizeName;

        if (operation.getMsg().equals(WheelOperation.msgSuccessTag)) {
            wheelImage.clearAnimation();
            //157.5=-180+22.5  to rotate the prize to 180 degree + 22.5 to be at the middle of the specified part
            double angle = (((operation.getWinningPrizeId() + 1) * 45) + 45) - 157.5;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                wheelImage.setRotation(0);
                wheelImage.setRotation(((float) angle));

            }
            prizeId = getResources().getString(R.string.prize_id) + " " + Integer.toString(operation.getWinningPrizeId());
            prizeName = getResources().getString(R.string.prize_name) + " " + operation.getAvailablePrizes()[operation.getWinningPrizeId()];
            Toast.makeText(WheelScreen.this, prizeId + " " + prizeName, Toast.LENGTH_LONG).show();
            Log.e("id", Integer.toString(operation.getWinningPrizeId()));
        } else {
            Toast.makeText(WheelScreen.this, getResources().getString(R.string.response_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.play_btn):
                if (networkConnected()) {
                    animateWheel();
                    serverOperations.moveWheel(WheelScreen.this);
                } else
                    Toast.makeText(WheelScreen.this, getResources().getString(R.string.network_connection_fail), Toast.LENGTH_LONG).show();

                break;


        }
    }

    private void animateWheel() {


        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
        wheelImage.startAnimation(startRotateAnimation);

    }

    private boolean networkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) WheelScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
