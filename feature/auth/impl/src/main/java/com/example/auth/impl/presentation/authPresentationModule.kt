package com.example.auth.impl.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authPresentationModule = module {
    viewModel { SignInViewModel(get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get(), get(), get()) }

}