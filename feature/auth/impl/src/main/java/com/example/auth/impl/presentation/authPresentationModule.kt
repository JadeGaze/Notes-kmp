package com.example.auth.impl.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authPresentationModule = module {
    viewModel { SignInViewModelImpl(get(), get(), get()) }
    viewModel { SignUpViewModelImpl(get(), get(), get(), get()) }

}