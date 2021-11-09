package com.example.kts_android_09_2021

import android.app.Application
import androidx.room.Room
import com.example.kts_android_09_2021.db.AppDatabase
import com.example.kts_android_09_2021.db.repositories_impl.*
import com.example.kts_android_09_2021.fragments.editorial_fragment.EditorialViewModel
import com.example.kts_android_09_2021.fragments.login_fragment.LoginFragmentViewModel
import com.example.kts_android_09_2021.fragments.main_fragment.MainFragmentViewModel
import com.example.kts_android_09_2021.fragments.on_boarding_fragments.on_boarding_fragment.OnBoardingViewModel
import com.example.kts_android_09_2021.fragments.on_boarding_fragments.screen_fragment.OnBoardingFragmentScreenViewModel
import com.example.kts_android_09_2021.fragments.profile_fragment.ProfileFragmentViewModel
import com.example.kts_android_09_2021.fragments.profile_fragment.liked_photos_fragment.LikedPhotosViewModel
import com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment.UserPhotosFragmentViewModel
import com.example.kts_android_09_2021.key_value.DatastoreRepository
import com.example.kts_android_09_2021.network.data.Networking
import com.example.kts_android_09_2021.network.data.NetworkingInterceptor
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.utils.NetworkingChecker
import kotlinx.coroutines.*
import net.openid.appauth.AuthorizationService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    interceptorModule,
                    networkingModule,
                    daosModule,
                    repositoriesModelsModule,
                    viewModelsModule
                )
            )
        }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }
        single { DatastoreRepository(this@MainApplication) }
    }

    private val interceptorModule = module {
        single { NetworkingInterceptor(get()) }
    }

    private val networkingModule = module {
        single { Networking(get()) }
    }

    private val daosModule = module {
        factory { get<AppDatabase>().editorialDao() }
        factory { get<AppDatabase>().likedPhotosDao() }
        factory { get<AppDatabase>().userDao() }
        factory { get<AppDatabase>().userPhotosDao() }
        single { get<Networking>().unsplashApi }
    }

    private val repositoriesModelsModule = module {
        factory { EditorialRepositoryImpl(get()) }
        factory { LikedPhotosRepositoryImpl(get()) }
        factory { UserPhotosRepositoryImpl(get()) }
        factory { UserRepositoryImpl(get()) }
        factory { NetworkingChecker(this@MainApplication) }
        factory { NetworkingRepository(get()) }
    }

    private val viewModelsModule = module {
        viewModel {
            LoginFragmentViewModel(
                AuthorizationService(this@MainApplication),
                get(),
                get(),
                get(),
                get()
            )
        }
        viewModel { EditorialViewModel(get(), get(), get()) }
        viewModel { MainFragmentViewModel(get()) }
        viewModel { OnBoardingViewModel(get()) }
        viewModel { OnBoardingFragmentScreenViewModel() }
        viewModel { LikedPhotosViewModel(get(), get(), get(), get()) }
        viewModel { UserPhotosFragmentViewModel(get(), get(), get(), get()) }
        viewModel { ProfileFragmentViewModel(get(), get(), get(), get(), get(), get(), get()) }
    }
}