package com.koushikdutta.async.http.socketio;

public interface ConnectCallback {
    void onConnectCompleted(Exception ex, SocketIOClient client);
}