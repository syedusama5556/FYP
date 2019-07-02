package Fragments;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import usama.utech.firebasepractice.Notification.MyResponse;
import usama.utech.firebasepractice.Notification.Sender;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAj3U0XXo:APA91bESONwM9Ef5qwjAww-R4aQARAkvYkgKlACa-lo8mhVDKJwzwx98SIXLkPW0drvdHCRk532MeBSIgTA6W_mg8751nmqMN4dSe1Gq-1NuUpiSzorJ3V9Z9e7l4-nOwsSWuU9vJy6w"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
