package com.holker.smart.di.modules

import com.holker.smart.di.FragmentScope
import com.holker.smart.functionalities.main.fragments.advertising.AdvertisingFragment
import com.holker.smart.functionalities.main.fragments.devices.DeviceFragment
import com.holker.smart.functionalities.main.fragments.profile.ProfileFragment
import com.holker.smart.functionalities.main.fragments.statistic.StatisticFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeAdvertisingFragment(): AdvertisingFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeDevicesFragment(): DeviceFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeStatisticFragment(): StatisticFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeProfileFragment(): ProfileFragment
}