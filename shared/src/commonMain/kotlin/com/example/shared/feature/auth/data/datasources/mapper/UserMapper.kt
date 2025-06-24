package com.example.shared.feature.auth.data.datasources.mapper

import com.example.shared.core.database.entity.UserEntity
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.DocumentSnapshot

fun FirebaseUser.toUserModel(): UserEntity =
    UserEntity(id = uid, email = "", name = "", password = "")


fun DocumentSnapshot.toUserModel(): UserEntity =
    UserEntity(
        id = id,
        email = get<String>("email"),
        name = get<String>("name"),
        password = ""
    )