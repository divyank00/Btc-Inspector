package com.example.bitcoininspector.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bitcoininspector.ui.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.example.bitcoininspector.di.utils.ViewModelFactory
import com.example.bitcoininspector.di.utils.ViewModelKey

@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(MainActivityViewModel::class)
  internal abstract fun bindsMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}
