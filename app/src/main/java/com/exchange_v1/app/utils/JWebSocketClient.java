package com.exchange_v1.app.utils;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    private URI serverUri;

    public JWebSocketClient(URI serverUri) {
        super(serverUri,new Draft_6455());
        this.serverUri = serverUri;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e("JWebSocketClient", "onOpen()");
        Log.e("JWebSocketClient", "URI : "+serverUri);
    }

    @Override
    public void onMessage(String message) {
        Log.e("JWebSocketClient", "onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("JWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.e("JWebSocketClient", "onError()");
    }


}
