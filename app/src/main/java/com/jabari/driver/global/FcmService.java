package com.jabari.driver.global;

import com.google.firebase.messaging.RemoteMessage;

import me.cheshmak.android.sdk.core.push.CheshmakFirebaseMessagingService;

public class FcmService extends CheshmakFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (isCheshmakMessage(remoteMessage)) {
            super.onMessageReceived(remoteMessage);
        } else {
            // process your message. It's not for Cheshmak
        }

    }
}