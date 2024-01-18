package rs.readahead.washington.mobile.data.reports.remote

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import rs.readahead.washington.mobile.data.entity.reports.*
import rs.readahead.washington.mobile.data.reports.utils.ParamsNetwork.AUTHORIZATION_HEADER
import rs.readahead.washington.mobile.data.repository.SkippableMediaFileRequestBody

@JvmSuppressWildcards
interface ReportsApiService {

    companion object {
        //DUMMY URL
        const val BASE_URL = "https://www.hzontal.org/"
    }

    @POST
    fun login(
        @Body
        loginEntity: LoginEntity,
        @Url
        url: String
    ): Single<ReportsLoginResponse>

    @GET
    fun getProjectSlug(
        @Url
        url: String,
        @Header(AUTHORIZATION_HEADER) access_token: String
    ): Single<ProjectSlugResponse>

    @POST
    fun submitReport(
        @Url
        url: String,
        @Header(AUTHORIZATION_HEADER) access_token: String,
        @Body
        reportBodyEntity: ReportBodyEntity,
    ): Single<ReportPostResponse>

    @PUT
    fun putFile(
        @Body file: SkippableMediaFileRequestBody,
        @Url url: String,
        @Header(AUTHORIZATION_HEADER) access_token: String,
    ): Single<Response<Void>>

    @POST
    fun postFile(
        @Url url: String,
        @Header(AUTHORIZATION_HEADER) access_token: String
    ): Single<Response<Void>>

    @HEAD
    fun getStatus(
        @Url url: String,
        @Header(AUTHORIZATION_HEADER) access_token: String
    ): Single<Response<Void>>

    @GET
    fun getResources(
        @Url
        url: String,
        @Header(AUTHORIZATION_HEADER) access_token: String
    ): Single<ResourcesGetResponse>
}