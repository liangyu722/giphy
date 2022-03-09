package com.android.giphy.di

import com.android.giphy.MainActivity
import com.android.giphy.di.networking.ServiceModule
import com.android.giphy.ui.detail.DetailFragment
import com.android.giphy.ui.home.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ServiceModule::class,
        RepositoryModule::class,
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(detailFragment: DetailFragment)
}
