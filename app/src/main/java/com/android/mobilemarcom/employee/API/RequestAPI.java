package com.android.mobilemarcom.employee.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RequestAPI {
    //method untuk get employee
//    @Headers("Authorization: MOGLK40NEYLUFKIORVFAFE5OCO60T4R140VTW35L9T72LRSRWKJIZXWTCD1HQKPZURKJPNYHIX0SO6SX672HASCKVAHPV6VHRXOKVV7KEQVZNETUBXRXM7CEKR5ZQJDA" )
    @GET("employee/name/{keyword}")
    Call<ExampleGet> getListEmployee(@Header("Content-Type") String contentType,
                                     @Header("Authorization") String tokenAuthorization,
                                     @Path("keyword") String keyword);

    //method untuk get user List
    @POST("employee/create")
    Call<Example> createNewEmployee(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String tokenAuthorization,
                                    @Body Example emp);

    @PUT("employee/update")
    Call<ExampleEdit> editEmployee(@Header("Content-Type") String contentType,
                                   @Body ExampleEdit emp);

    @GET("company/key/{keyword}")
    Call<ExampleAuto> autoComplete(@Header("Content-Type") String contentType,
                                   @Header("Authorization") String tokenAuthorization,
                                   @Path("keyword") String keyword);

    @PUT("employee/deactivate/{keyword}")
    Call<ExampleGet> deactivateEmployee(@Header("Content-Type") String contentType,
                                        @Header("Authorization") String tokenAuthorization,
                                        @Path("keyword") int id);

}
