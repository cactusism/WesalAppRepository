package com.shaden.wesal;

import com.shaden.wesal.chatNotifications.MyResponse;
import com.shaden.wesal.chatNotifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAATy8NzuY:APA91bFUhvZjYOIwSjVb4b71P97-WwxCoaKdd5KZSeuXCsyKJWZW_4-hnBZjOEcCnggEpXjiygE4lFrCMuSDBaLqFdizRiNdW0hbdA2xCYXgCPuFzEEQigoj97RmXP4Af1lNf6WVDmc3"

            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);


}
