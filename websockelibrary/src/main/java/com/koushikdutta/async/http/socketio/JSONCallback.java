package com.koushikdutta.async.http.socketio;

import org.json.JSONObject;

public interface JSONCallback {
    void onJSON(JSONObject json, Acknowledge acknowledge);
}
    
