package com.example.shared.feature.auth

import com.example.auth.api.usecase.SignUpUseCase
import com.example.auth.api.usecase.validators.IsEmailValidUseCase
import com.example.auth.api.usecase.validators.IsNotEmptyStringUseCase
import com.example.shared.feature.auth.data.datasources.local.LocalDataSource
import com.example.shared.feature.auth.data.datasources.local.RoomDataSource
import com.example.shared.feature.auth.data.datasources.remote.FirebaseDataSource
import com.example.shared.feature.auth.data.datasources.remote.RemoteDataSource
import com.example.shared.feature.auth.data.repository.UserRepositoryImpl
import com.example.shared.feature.auth.data.usecase.IsAuthUseCaseImpl
import com.example.shared.feature.auth.data.usecase.SignInUseCaseImpl
import com.example.shared.feature.auth.data.usecase.SignOutUseCaseImpl
import com.example.shared.feature.auth.data.usecase.SignUpUseCaseImpl
import com.example.shared.feature.auth.data.usecase.validation.IsEmailValidUseCaseImpl
import com.example.shared.feature.auth.data.usecase.validation.IsNameValidUseCaseImpl
import com.example.shared.feature.auth.data.usecase.validation.IsNotEmptyStringUseCaseImpl
import com.example.shared.feature.auth.data.usecase.validation.IsPasswordValidUseCaseImpl
import com.example.shared.feature.auth.domain.repository.UserRepository
import com.example.shared.feature.auth.domain.usecase.IsAuthUseCase
import com.example.shared.feature.auth.domain.usecase.SignInUseCase
import com.example.shared.feature.auth.domain.usecase.SignOutUseCase
import com.example.shared.feature.auth.domain.usecase.validators.IsNameValidUseCase
import com.example.shared.feature.auth.domain.usecase.validators.IsPasswordValidUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val authModule = module {
    single<LocalDataSource> { RoomDataSource(get()) }
    single<RemoteDataSource> { FirebaseDataSource(get(), get()) }
//    single<PreferencesDataSource> { SharedPreferencesDataSource(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }

    single<FirebaseAuth> {
        Firebase.auth
    }
    single<FirebaseFirestore> {
        Firebase.firestore
    }

    factory<IsAuthUseCase> { IsAuthUseCaseImpl(get(), get()) }
    factory<SignInUseCase> { SignInUseCaseImpl(get(), get()) }
    factory<SignUpUseCase> { SignUpUseCaseImpl(get(), get()) }
    factory<SignOutUseCase> { SignOutUseCaseImpl(get(), get()) }

    factory<IsEmailValidUseCase> { IsEmailValidUseCaseImpl() }
    factory<IsNameValidUseCase> { IsNameValidUseCaseImpl() }
    factory<IsPasswordValidUseCase> { IsPasswordValidUseCaseImpl() }
    factory<IsNotEmptyStringUseCase> { IsNotEmptyStringUseCaseImpl() }
}