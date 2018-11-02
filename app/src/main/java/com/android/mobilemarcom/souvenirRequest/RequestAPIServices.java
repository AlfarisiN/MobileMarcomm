package com.android.mobilemarcom.souvenirRequest;

import com.android.mobilemarcom.model.Login;
import com.android.mobilemarcom.model.ModelSouvenir.DataListSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseSouvenirCreate;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseSouvenirEdit;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseGetItemSouvenirStok;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStockAddItem;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStockAutoComplete;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStockItem;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStokCreate;
import com.android.mobilemarcom.model.modelevent.ModelEventRetrofit;
import com.android.mobilemarcom.model.souvenir_request.ApprovalSouvenirRequest;
import com.android.mobilemarcom.model.souvenir_request.CloseSouvenirRequest;
import com.android.mobilemarcom.model.souvenir_request.ExampleSouvenirRequest;
import com.android.mobilemarcom.model.user.Example;
import com.android.mobilemarcom.model.user.ExampleAutoCompleteEmploye;
import com.android.mobilemarcom.model.user.ExampleAutoCompleteRole;
import com.android.mobilemarcom.model.user.UserAdd;
import com.android.mobilemarcom.unit.modelunit.DataList;
import com.android.mobilemarcom.unit.modelunit.ModelUnitRetrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RequestAPIServices {

    @POST("user/create")
    Call<UserAdd> createUser(@Header("Content-Type") String contentType,
                             @Header("Authorization") String tokenAuthorization,
                             @Body RequestBody data);
    @POST("login")
    Call<Login> loginUser(@Header("Content-Type") String contentType,
                          @Body RequestBody data);
    @PUT("user/update")
    Call<UserAdd> editUser(@Header("Content-Type") String contentType,
                           @Header("Authorization") String tokenAuthorization,
                           @Body RequestBody data);
    @GET("user/name/{keyword}")
    Call<Example> getDataListUser(@Header("Content-Type") String contentType,
                                  @Header("Authorization") String tokenAuthorization,
                                  @Path("keyword") String keyword);

    @PUT("user/deactivate/{keyword}")
    Call<UserAdd> deactiveUser(@Header("Content-Type") String contentType,
                               @Header("Authorization") String tokenAuthorization,
                               @Path("keyword") int keyword);

    @GET("user/name/{keyword}")
    Call<Example> roleautocomplete(@Header("Content-Type") String contentType,
                                   @Header("Authorization") String tokenAuthorization,
                                   @Path("keyword") String keyword);

    //roleautocomple
    @GET("role/key/{keyword}")
    Call<ExampleAutoCompleteRole> roleautocompletee(@Header("Content-Type") String contentType,
                                                    @Header("Authorization") String tokenAuthorization,
                                                    @Path("keyword") String keyword);

    @GET("employee/key/{keyword}")
    Call<ExampleAutoCompleteEmploye> employeautocompletee(@Header("Content-Type") String contentType,
                                                          @Header("Authorization") String tokenAuthorization,
                                                          @Path("keyword") String keyword);

    //souvenir request
    @GET("souvenirrequest/requestedby/{keyword}")
    Call<com.android.mobilemarcom.model.souvenir_request.Example> souvenirReqGet(@Header("Content-Type") String contentType,
                                                                                 @Header("Authorization") String token,
                                                                                 @Path("keyword") String newText);
    //souvenir get item request
    @GET("souvenirrequest/item/{keyword}")
    Call<ExampleSouvenirRequest> souvenirReqGetItemReguest(@Header("Content-Type") String contentType,
                                                           @Header("Authorization") String token,
                                                           @Path("keyword") int newText);

    //approve souvenir get item request
    @PUT("souvenirrequest/approve/{keyword}")
    Call<ApprovalSouvenirRequest> souvenirReqGetItemReguestApprove(@Header("Content-Type") String contentType,
                                                                   @Header("Authorization") String token,
                                                                   @Path("keyword") int newText);
    //reject souvenir get item request
    @PUT("souvenirrequest/reject/{angka}/{keyword}")
    Call<ApprovalSouvenirRequest> souvenirReqGetItemReguestReject(@Header("Content-Type") String contentType,
                                                                  @Header("Authorization") String token,
                                                                  @Path("angka") int newText,
                                                                  @Path("keyword") String keyword);
    //close souvenir get item request
    @PUT("souvenirrequest/close/{keyword}")
    Call<CloseSouvenirRequest> souvenirReqGetItemReguestClose(@Header("Content-Type") String contentType,
                                                              @Header("Authorization") String token,
                                                              @Path("keyword") int newText);

    /* Souvenir by Denis */
    //Get Data Search RequestSouvenirItemId
    @GET("souvenir/name/{keyword}")
    Call<DataListSouvenir> searchSouvenirName(@Header("Content-Type") String contentType,
                                              @Header("Authorization") String tokenAuthorization,
                                              @Path("keyword") String keyword);

    //Get Data AutoComplete RequestGetItemSouvenirStokSouvenir
    @GET("souvenir/key/{keyword}")
    Call<ResponseAutoCompleteSouvenir> autocompleteSouvenir(@Header("Content-Type") String contentType,
                                                            @Header("Authorization") String tokenAuthorization,
                                                            @Path("keyword") String keyword);

    //Post Data RequestSouvenir ItemId
    @POST("souvenir/create")
    Call<ResponseSouvenirCreate> createSouvenir(@Header("Content-Type") String contentType,
                                                @Header("Authorization") String tokenAuthorization,
                                                @Body RequestBody data);

    //PUT EDIT SOUVENIR
    @PUT("souvenir/update")
    Call<ResponseSouvenirEdit> updateSouvenir(@Header("Content-Type") String contentType,
                                              @Header("Authorization") String tokenAuthorization,
                                              @Body RequestBody data);

    //PUT deactivate
    @PUT("souvenir/deactivate/{keyword}")
    Call<DataListSouvenir> deactivateSouvenir(@Header("Content-Type") String contentType,
                                              @Header("Authorization") String authorization,
                                              @Path("keyword") int keyword);


    //POST Create RequestSouvenirStockItemId Stok
    @POST("souvenirstock/create")
    Call<ResponseSouvenirStokCreate> createSouvenirStok(@Header("Content-Type") String contentType,
                                                        @Header("Authorization") String tokenAuthorization,
                                                        @Body RequestBody data);
    //POST Add Item Souvenir Stok
    @POST("souvenirstock/item/create/{keyword}")
    Call<ResponseSouvenirStockItem> createSouvenirStockItem(@Header("Content-Type") String contentType,
                                                            @Header("Authorization") String tokenAuthorization,
                                                            @Body RequestBody data,
                                                            @Path("keyword") int keyword);

    //Get Data RequestGetItemSouvenirStokSouvenir Stok
    @GET("souvenirstock/receivedby/{keyword}")
    Call<ResponseSouvenirStockAddItem> getSouvenirStock(@Header("Content-Type") String contentType,
                                                        @Header("Authorization") String tokenAuthorization,
                                                        @Path("keyword") String keyword);

    //GET Data RequestGetItemSouvenir Stock Souvenir Stock AutoComplete
    @GET("souvenirstock/key/{keyword}")
    Call<ResponseSouvenirStockAutoComplete> autocompleteSouvenirStok(@Header("Authorization") String tokenAuthorization,
                                                                     @Path("keyword") String keyword);

    //GET Data AddItem Souvenir Stock
    @GET("souvenirstock/item/{keyword}")
    Call<ResponseGetItemSouvenirStok> getSouvenirStockAddItem(@Header("Content-Type") String contentType,
                                                              @Header("Authorization") String tokenAuthorization,
                                                              @Path("keyword") int keyword);

    //DEL Delete data add Item Souvenir Stok
    @DELETE("souvenirstock/item/delete/{keyword}")
    Call<ResponseGetItemSouvenirStok> delSouvenirStockAddItem(@Header("Content-Type") String contentType,
                                                              @Header("Authorization") String tokenAuthorization,
                                                              @Path("keyword") String keyword);

    @POST("unit/create")
    Call<ModelUnitRetrofit> createUnit(@Header("Content-Type") String head,
                                       @Body DataList dataList);

    @PUT("unit/update")
    Call<ModelUnitRetrofit> editUnit(@Header("Content-Type") String head,
                                     @Header("Authorization") String auth,
                                     @Body DataList dataList);

    @PUT("unit/deactivate/{id}")
    Call<ModelUnitRetrofit> deactiveUnit(@Header("Content-Type") String head,
                                         @Header("Authorization") String auth,
                                         @Path("id") String dataList);

    @GET("unit/name/{path}")
    Call<ModelUnitRetrofit> searchUnit(@Header("Authorization") String auth,
                                       @Path("path") String id);

    @POST("event/create")
    Call<ModelEventRetrofit> createEvent(@Header("Content-Type") String head,
                                         @Header("Authorization") String auth,
                                         @Body com.android.mobilemarcom.model.modelevent.DataList eventModul);

    @GET("event/code/{pathID}")
    Call<ModelEventRetrofit> searchEvent(@Header("Authorization") String auth,
                                         @Path("pathID") String id);

    @PUT("unit/update")
    Call<ModelEventRetrofit> editEvent(@Header("Content-Type") String head,
                                       @Header("Authorization") String auth,
                                       @Body com.android.mobilemarcom.model.modelevent.DataList dataList);

    @GET("event/key/{pathID}")
    Call<ModelEventRetrofit> autoCompleteEvent(@Header("Authorization") String auth,
                                               @Path("pathID") String id);

    @PUT("event/reject/{pathID}/{reason}")
    Call<ModelEventRetrofit> rejectEvent(@Header("Content-Type") String head,
                                         @Header("Authorization") String auth,
                                         @Path("pathID") String pathID,
                                         @Path("reason") String reason);

    @PUT("event/close/{pathID}")
    Call<ModelEventRetrofit> closeEvent(@Header("Content-Type") String head,
                                        @Header("Authorization") String auth,
                                        @Path("pathID") String pathID);

    @PUT("event/approve/{pathID1}/{pathID2}")
    Call<ModelEventRetrofit> approvalEvent(@Header("Content-Type") String head,
                                           @Header("Authorization") String auth,
                                           @Path("pathID1") String path1,
                                           @Path("pathID2") String path2);
}

