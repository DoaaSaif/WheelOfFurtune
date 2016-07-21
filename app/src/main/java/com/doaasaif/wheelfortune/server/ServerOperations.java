package com.doaasaif.wheelfortune.server;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.doaasaif.wheelfortune.R;
import com.doaasaif.wheelfortune.app.AppController;
import com.doaasaif.wheelfortune.listener.VolleyListener;
import com.doaasaif.wheelfortune.model.WheelOperation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shabegt8040 on 7/18/2016.
 */

public class ServerOperations {


    private String moveWheelURL = "http://research.ngageu.com/prizewheel/api/example/prizes";
    Gson gson;
    String operationTag = "OPERATION";

    WheelOperation wheelOperation;

    public void moveWheel(final VolleyListener volleyListener) {

        final int prizeId;
        gson = new Gson();



        StringRequest strReq = new StringRequest(Request.Method.GET, moveWheelURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject result;


                        wheelOperation = gson.fromJson(response, WheelOperation.class);
                        volleyListener.onRemoteCallComplete(wheelOperation);
                        Log.e("HttpClient", "success! response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, operationTag);

    }

}
