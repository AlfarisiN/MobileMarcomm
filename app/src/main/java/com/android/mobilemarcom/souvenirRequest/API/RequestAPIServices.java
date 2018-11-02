package com.android.mobilemarcom.souvenirRequest.API;

import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseGetItemSouvenirStok;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestAPIServices {
    //ambil data login
//    @Headers("Content-Type: application/json")
//    @POST("login")
//    Call<DataListAddItem> dataListCall(@Field("username") String username, @Field("password") String password);
//
//    @Headers("Authorization: MOGLK40NEYLUFKIORVFAFE5OCO60T4R140VTW35L9T72LRSRWKJIZXWTCD1HQKPZURKJPNYHIX0SO6SX672HASCKVAHPV6VHRXOKVV7KEQVZNETUBXRXM7CEKR5ZQJDA")
//    @GET("employee/key/g")
//    Call<DataListEmploye> getDataListEmploye();
//
//    @Headers("Authorization: B3LFUXRHQQKM3Z7SXBAZG2J7ME9MT95ALTF7IFGMRIXNXOWYISEKIAEWVHCUVZLBNK7TXKNZRFC4E0QXV1BUSRGWVN2K9NGQNA6CUVDDBLYCRRGVPMJY8ORNVAIOTK4S")
//    @GET("user/name/ad")
//    Call<ExampleAdd> getDataListUser();
//
//    @Headers("Authorization: MOGLK40NEYLUFKIORVFAFE5OCO60T4R140VTW35L9T72LRSRWKJIZXWTCD1HQKPZURKJPNYHIX0SO6SX672HASCKVAHPV6VHRXOKVV7KEQVZNETUBXRXM7CEKR5ZQJDA")
//    @GET("user/key/g")
//    Call<ExampleAdd> getDataAutoComplte();

//    @POST("user/create")
//    Call<UserAdd> createUser(@Header("Content-Type") String contentType,
//                             @Header("Authorization") String tokenAuthorization,
//                             @Body RequestBody data);
//    @POST("login")
//    Call<Login> loginUser(@Header("Content-Type") String contentType,
//                          @Body RequestBody data);
//    @PUT("user/update")
//    Call<UserAdd> editUser(@Header("Content-Type") String contentType,
//                           @Header("Authorization") String tokenAuthorization,
//                           @Body RequestBody data);
//    @GET("user/name/{keyword}")
//    Call<ExampleAdd> getDataListUser(@Header("Content-Type") String contentType,
//                                  @Header("Authorization") String tokenAuthorization,
//                                  @Path("keyword") String keyword);
//
//    @PUT("user/deactivate/{keyword}")
//    Call<UserAdd> deactiveUser(@Header("Content-Type") String contentType,
//                               @Header("Authorization") String tokenAuthorization,
//                               @Path("keyword") int keyword);
//
//    @GET("employee/key/{keyword}")
//    Call<ExampleAutocomplete> roleautocomplete(@Header("Content-Type") String contentType,
//                                               @Header("Authorization") String tokenAuthorization,
//                                               @Path("keyword") String keyword);
    @GET("souvenirrequest/requestedby/{keyword}")
    Call<Example> souvenirReqGet(@Header("Content-Type") String contentType,
                                 @Header("Authorization") String token,
                                 @Path("keyword") String newText);

    @POST("souvenirrequest/create")
    Call<ExampleAdd> souvenirReqAdd(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String tokenAuthorization,
                                    @Body RequestBody data);

    @GET("souvenirrequest/key/{keyword}")
    Call<com.android.mobilemarcom.souvenirRequest.Example> souvenirReqAuto(
            @Header("Content-Type") String contentType,
            @Header("Authorization") String tokenAuthorization,
            @Path("keyword") String keyword);
//                                       @Header("Authorization") String tokenAuthorization);

    @GET("souvenirrequest/item/{keyword}")
    Call<com.android.mobilemarcom.souvenirRequest.d.Example> souvenirAddItem(@Header("Content-Type") String contentType,
                                                                             @Header("Authorization") String tokenAuthorization,
                                                                             @Path("keyword") String keyword);

    @POST("souvenirrequest/item/create/{keyword}")
    Call<ResponseGetItemSouvenirStok> souvenirCreateAdd(@Header("Content-Type") String contentType,
                                                        @Header("Authorization") String tokenAuthorization,
//                                             @Body RequestBody data,
                                                        @Path("keyword") int keyword);

    @DELETE("souvenirrequest/item/delete/{keyword}")
    Call<com.android.mobilemarcom.souvenirRequest.d.Example> deleteItem(@Header("Content-Type") String contentType,
                                                                        @Header("Authorization") String tokenAuthorization,
                                                                        @Path("keyword") int keyword);

}

