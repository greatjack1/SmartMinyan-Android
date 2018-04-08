package com.wyre.smartminyan.Networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * This class encapsulates all network requests
 * Created by yaakov on 4/3/18.
 */

public class VolleyLab {
    private static VolleyLab mVolleyLab;
    private RequestQueue mQueve;
    private Context mContext;
    private VolleyLab(Context context) {
mContext = context.getApplicationContext();
mQueve = Volley.newRequestQueue(mContext);
    }
    public static VolleyLab getVolleyLab(Context context){
        if(mVolleyLab==null){
            mVolleyLab = new VolleyLab(context);
        }
        return mVolleyLab;
    }

    /**
     * Helper method to send volley requests
     * @param request the request to send
     * @param <T> The type parameter of the request
     */
    public <T> void addRequest(Request<T> request){
        mQueve.add(request);
    }

}
