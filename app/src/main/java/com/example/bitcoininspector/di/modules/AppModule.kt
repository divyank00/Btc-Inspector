package com.example.bitcoininspector.di.modules

import com.example.bitcoininspector.util.WebSocket
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

  // TODO provide App level dependencies in here using @Provides
  @Provides
  @Singleton
  fun providesWebSocket(): WebSocket = WebSocket()
}
