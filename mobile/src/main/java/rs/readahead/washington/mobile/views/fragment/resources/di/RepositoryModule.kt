package rs.readahead.washington.mobile.views.fragment.resources.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.readahead.washington.mobile.MyApplication
import rs.readahead.washington.mobile.data.database.DataSource
import rs.readahead.washington.mobile.data.resources.remote.ResourcesApiService
import rs.readahead.washington.mobile.data.resources.repository.ResourcesRepositoryImp
import rs.readahead.washington.mobile.domain.repository.ITellaUploadServersRepository
import rs.readahead.washington.mobile.domain.repository.resources.ITellaResourcesRepository
import rs.readahead.washington.mobile.domain.repository.resources.ResourcesRepository
import rs.readahead.washington.mobile.util.StatusProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideResourcesRepository(
        service: ResourcesApiService,
        dataSource: DataSource
    ): ResourcesRepository {
        return ResourcesRepositoryImp(service, dataSource)
    }

    @Provides
    @Singleton
    fun provideResourcesDataSource(): ITellaResourcesRepository {
        return MyApplication.getKeyDataSource().dataSource.blockingFirst()
    }
}