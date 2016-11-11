package com.campbellapps.christiancampbell.peoplemonv1.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by christiancampbell on 10/31/16.
 */

public class SessionRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (com.campbellapps.christiancampbell.peoplemonv1.Network.UserStore.getInstance().getToken() != null){
        Request.Builder builder = request.newBuilder();
            builder.header("Authorization", "Bearer " + com.campbellapps.christiancampbell.peoplemonv1.Network.UserStore.getInstance().getToken()); // grants authorization, space after bearer is always needed
            request = builder.build();
        }
        return chain.proceed(request);
    }
}
