package id.beetechmedia.opmsimpatika.api;

import id.beetechmedia.opmsimpatika.model.ModelRespon;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sabiqul on 11/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public interface APIRequestData {

 //   @FormUrlEncoded
 //   @POST("login.php")
//    Call<Login> loginResponse(
//            @Field("username") String username,
 //           @Field("password") String password
//    );

//    @FormUrlEncoded
//    @POST("register.php")
//    Call<Register> registerResponse(
 //           @Field("username") String username,
 //           @Field("password") String password,
//            @Field("name") String name
//    );

    @FormUrlEncoded
    @POST("api_deletewisata.php")
    Call<ModelRespon> ardDelete(
            @Field("NO") String no
    );
    @FormUrlEncoded
    @POST("raopm_createsimpatika.php")
    Call<ModelRespon> ardCreateRA(
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("JENJANG") String JENJANG,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );
    @GET("raopm_datasimpatika.php")
    Call<ModelRespon> ardRetriveOPMRA();

    @FormUrlEncoded
    @POST("raopm_editsimpatika.php")
    Call<ModelRespon> ardEditOPMRA(
            @Field("NO") String NO,
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );
    @FormUrlEncoded
    @POST("raopm_carisimpatika.php")
    Call<ModelRespon> cariOPMRA(
            @Field("search") String search);

    @FormUrlEncoded
    @POST("miopm_carisimpatika.php")
    Call<ModelRespon> cariOPMMI(
            @Field("search") String search);

    @FormUrlEncoded
    @POST("miopm_createsimpatika.php")
    Call<ModelRespon> ardCreateMI(
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("JENJANG") String JENJANG,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );
    @GET("miopm_datasimpatika.php")
    Call<ModelRespon> ardRetriveOPMMI();

    @FormUrlEncoded
    @POST("miopm_editsimpatika.php")
    Call<ModelRespon> ardEditOPMMI(
            @Field("NO") String NO,
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );

    @FormUrlEncoded
    @POST("maopm_carisimpatika.php")
    Call<ModelRespon> cariOPMMA(
            @Field("search") String search);

    @FormUrlEncoded
    @POST("maopm_createsimpatika.php")
    Call<ModelRespon> ardCreateMA(
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("JENJANG") String JENJANG,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );
    @GET("maopm_datasimpatika.php")
    Call<ModelRespon> ardRetriveOPMMA();

    @FormUrlEncoded
    @POST("maopm_editsimpatika.php")
    Call<ModelRespon> ardEditOPMMA(
            @Field("NO") String NO,
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );
    @FormUrlEncoded
    @POST("api_carimtsopmsimpatika.php")
    Call<ModelRespon> search(
            @Field("search") String search);
    @GET("api_datamtsopmsimpatika.php")
    Call<ModelRespon> ardRetriveOPM();

    @FormUrlEncoded
    @POST("api_deletemtsopmsimpatika.php")
    Call<ModelRespon> ardDeleteMTs(
            @Field("NO") String NO
    );
    @FormUrlEncoded
    @POST("api_createmtsopmsimpatika.php")
    Call<ModelRespon> ardCreateOPM(
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("JENJANG") String JENJANG,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );

    @FormUrlEncoded
    @POST("api_editmtsopmsimpatika.php")
    Call<ModelRespon> ardUpdateOPM(
            @Field("NO") String NO,
            @Field("NSM") String NSM,
            @Field("MADRASAH") String MADRASAH,
            @Field("KEC") String KEC,
            @Field("DESA") String DESA,
            @Field("NAMAOPM") String NAMAOPM,
            @Field("WAOPM") String WAOPM
    );
}