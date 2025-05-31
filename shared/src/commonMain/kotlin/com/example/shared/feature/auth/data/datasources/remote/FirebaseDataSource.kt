package com.example.shared.feature.auth.data.datasources.remote

import com.example.shared.core.database.entity.UserEntity
import com.example.shared.feature.auth.data.datasources.mapper.toUserModel
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirebaseDataSource(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) : RemoteDataSource {
    override suspend fun signUp(name: String, password: String, email: String): UserEntity {
        val user = auth.createUserWithEmailAndPassword(email, password).user!!.toUserModel()
        saveUserToFireStore(user)
        return user
    }

    override suspend fun signIn(email: String, password: String): UserEntity {
        return auth.signInWithEmailAndPassword(email, password).user!!.toUserModel()
    }

    override suspend fun getCurrentUser(): UserEntity {
        return fireStore.collection(USERS_COLLECTION_PATH).document(auth.currentUser!!.uid).get()
            .toUserModel()
    }

    override suspend fun updateCurrentUser(user: UserEntity) {
        saveUserToFireStore(user)
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    private suspend fun saveUserToFireStore(user: UserEntity) {
        fireStore.collection(USERS_COLLECTION_PATH).document("${user.id}").set(user)
    }

    companion object {
        private const val USERS_COLLECTION_PATH = "users"
    }
}