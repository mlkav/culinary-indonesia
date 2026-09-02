package com.dicoding.rnlkav_jetpack.di

import com.dicoding.rnlkav_jetpack.data.CulinaryRepository

object Injection {
    fun provideRepository(): CulinaryRepository {
        return CulinaryRepository.getInstance()
    }
}
