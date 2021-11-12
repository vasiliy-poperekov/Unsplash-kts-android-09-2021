package com.example.kts_android_09_2021.presentation

import android.app.Application
import androidx.room.Room
import com.example.kts_android_09_2021.BuildConfig
import com.example.kts_android_09_2021.data.db.AppDatabase
import com.example.kts_android_09_2021.presentation.fragments.editorial_fragment.EditorialViewModel
import com.example.kts_android_09_2021.presentation.fragments.login_fragment.LoginFragmentViewModel
import com.example.kts_android_09_2021.presentation.fragments.main_fragment.MainFragmentViewModel
import com.example.kts_android_09_2021.presentation.fragments.on_boarding_fragments.on_boarding_fragment.OnBoardingViewModel
import com.example.kts_android_09_2021.presentation.fragments.on_boarding_fragments.screen_fragment.OnBoardingFragmentScreenViewModel
import com.example.kts_android_09_2021.presentation.fragments.profile_fragment.ProfileFragmentViewModel
import com.example.kts_android_09_2021.presentation.fragments.profile_fragment.liked_photos_fragment.LikedPhotosViewModel
import com.example.kts_android_09_2021.presentation.fragments.profile_fragment.user_photos_fragment.UserPhotosFragmentViewModel
import com.example.kts_android_09_2021.data.DatastoreRepository
import com.example.kts_android_09_2021.data.api.NetworkingInterceptor
import com.example.kts_android_09_2021.data.api.NetworkingModule
import com.example.kts_android_09_2021.data.api.NetworkingRepository
import com.example.kts_android_09_2021.data.repositories.EditorialRepository
import com.example.kts_android_09_2021.data.repositories.LikedPhotosRepository
import com.example.kts_android_09_2021.data.repositories.UserPhotosRepository
import com.example.kts_android_09_2021.data.repositories.UserRepository
import com.example.kts_android_09_2021.presentation.utils.NetworkingChecker
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
        single { NetworkingModule(get()) }
    }

    private val daosModule = module {
        factory { get<AppDatabase>().editorialDao() }
        factory { get<AppDatabase>().likedPhotosDao() }
        factory { get<AppDatabase>().userDao() }
        factory { get<AppDatabase>().userPhotosDao() }
        single { get<NetworkingModule>().unsplashApi }
    }

    private val repositoriesModelsModule = module {
        factory { EditorialRepository(get()) }
        factory { LikedPhotosRepository(get()) }
        factory { UserPhotosRepository(get()) }
        factory { UserRepository(get()) }
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