package com.example.testnav.model.DI

import com.example.testnav.model.RegisterUser
import com.example.testnav.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModul {

    @Singleton
    @Provides
    fun provideRegisterUser(): RegisterUser{
        return RegisterUser()
    }

    @Singleton
    @Provides
    fun provideViewModel(): MainViewModel{
        return MainViewModel()
    }

}