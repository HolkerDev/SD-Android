package com.holker.smart.di

import javax.inject.Scope

/**
 * Dagger scope for Activities
 */
@Scope
@Target(
    AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityScope

/**
 * Dagger scope for Fragments
 */
@Scope
@Target(
    AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
annotation class FragmentScope
