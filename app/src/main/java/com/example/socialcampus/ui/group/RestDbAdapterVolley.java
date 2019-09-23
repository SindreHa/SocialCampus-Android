package com.example.socialcampus.ui.group;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RestDbAdapterVolley {
    // Må forandres (får vondt i huet av database)
    static final String endpoint = "https://itfag.usn.no/~146005/api.php";

    private Context ctx;
    private RequestQueue queue;

    public RestDbAdapterVolley(Context ctx) {
        this.ctx = ctx;
        queue = Volley.newRequestQueue(ctx);
    }

    private boolean updatedOK = false;

    public boolean isRestCallOK() {
        return updatedOK;
    }

    public void insertPostCard(PostCard nyPost) {
        String post_URL = endpoint + "/post";
        JSONObject jsonPost = nyPost.toJSONObject();
        addJSONRequest(Request.Method.POST, post_URL, jsonPost);
    }

    public void updatePost(String postNr, PostCard postCard) {
        String vare_URL = endpoint + "/post/" + postNr;
        JSONObject jsonVare = postCard.toJSONObject();
        addJSONRequest(Request.Method.PUT, vare_URL, jsonVare);
    }

    // Slett en vare med angitt varenummer
    public void deletePost(String postNr) {
        String vare_URL = endpoint + "/post/" + postNr;
        addJSONRequest(Request.Method.DELETE, vare_URL, null);
    }

    private void addJSONRequest(int RequestMethod, String URL, JSONObject data) {
        if (isOnline()) {
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(RequestMethod, URL, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Foreløpig ingen sjekk av responsen for å se om lagring er vellykket  !!!
                            updatedOK = true;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            updatedOK = false;
                        }
                    });
            queue.add(jsonObjRequest);
        }
    }


    // Fant ikke en u-depricata versjon av samme koden...
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
