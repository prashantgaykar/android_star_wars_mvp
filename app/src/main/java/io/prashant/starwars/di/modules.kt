package io.prashant.starwars.di

import android.app.Activity
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.prashant.starwars.contract.CharactersContract
import io.prashant.starwars.data.remote.ApiConstants
import io.prashant.starwars.data.remote.Networking
import io.prashant.starwars.presenter.CharactersPresenter
import io.prashant.starwars.ui.characters.CharacterActivity
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideNetworkService(
        @ApplicationContext applicationContext: Context
    ) =
        Networking.create(
            ApiConstants.BASE_URL,
            applicationContext.cacheDir,
            5 * 1024 * 1024, // 5MB
        )
}


@InstallIn(ActivityComponent::class)
@Module
abstract class CharacterModule {

    @Binds
    abstract fun bindActivity(activity: CharacterActivity): CharactersContract.View

    @Binds
    abstract fun bindPresenter(presenter: CharactersPresenter): CharactersContract.Presenter
}

@InstallIn(ActivityComponent::class)
@Module
object CharacterActivityModule {

    @Provides
    fun bindActivity(activity: Activity): CharacterActivity {
        return activity as CharacterActivity
    }
}
