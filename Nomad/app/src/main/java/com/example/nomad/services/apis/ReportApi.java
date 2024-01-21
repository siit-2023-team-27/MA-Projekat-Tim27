package com.example.nomad.services.apis;

import com.example.nomad.dto.ReportDTO;
import com.example.nomad.helper.Consts;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportApi {

    String BASE_URL = Consts.BASEURL+"/api/";

    @GET("reports/accommodation/{hostId}/{accommodationId}/{year}")
    public Call<ArrayList<ReportDTO>> getReportForAccommodation(@Path("hostId") Long hostId,
                                                                @Path("accommodationId") Long accommodationId,
                                                                @Path("year") int year,
                                                                @Header("Authorization") String authHeader);

    @GET("reports/date-range/{hostId}")
    public Call<ArrayList<ReportDTO>> getReportForDateRange(@Path("hostId") Long hostId,
                                                            @Query("from") String fromDate,
                                                            @Query("to") String toDate,
                                                            @Header("Authorization") String authHeader);
}
