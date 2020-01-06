package com.holker.smart.di.modules

import com.holker.smart.functionalities.main.fragments.advertising.AdvertisingFragment
import com.holker.smart.functionalities.main.fragments.devices.DeviceFragment
import com.holker.smart.functionalities.main.fragments.profile.ProfileFragment
import com.holker.smart.functionalities.main.fragments.statistic.StatisticFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeAdvertisingFragment(): AdvertisingFragment

    @ContributesAndroidInjector
    abstract fun contributeDevicesFragment(): DeviceFragment

    @ContributesAndroidInjector
    abstract fun contributeStatisticFragment(): StatisticFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}