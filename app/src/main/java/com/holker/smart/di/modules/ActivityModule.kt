package com.holker.smart.di.modules

import com.holker.smart.di.ActivityScope
import com.holker.smart.functionalities.start.StartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeStartActivity(): StartActivity
}