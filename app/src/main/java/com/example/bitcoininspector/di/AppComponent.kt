package com.example.bitcoininspector.di

import android.content.Context
import com.example.bitcoininspector.di.modules.AppModule
import com.example.bitcoininspector.di.modules.ViewModelModule
import com.example.bitcoininspector.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
  @Component.Factory
  interface Factory {
    fun create(@BindsInstance context: Context): AppComponent
  }

  // TODO inject activities, fragments, starting points of graph in here
  fun inject(activity: MainActivity)
}
