## 🔍 Summary

It is an Android note-taking app designed for reliability and productivity, featuring:

- 📂Organized Workflow: Create and manage notes within customizable folders
- 🌐Offline-First Design: Full functionality without internet (Room DB persistence)
- 🔄Sync: Automatic Firestore synchronization
- ⚡Auto-Save Protection: Never lose work with periodic draft preservation

## 🛠 Tech Stack

#### Core:

- Kotlin (1.9.0) + Coroutines
- Jetpack Compose (2025.01.00) + Material 3
- Koin (3.4.0)
- Navigation Component (2.8.5)

#### Data:

- Room DB (2.6.1)
- Firebase Firestore (25.1.1) (for cloud sync)
- Kotlinx Serialization (1.6.0) (JSON parsing)

#### Architecture:

- Modularization (`:shared:commonAndroid`, `:shared:commonMain`; android: `:app`, `:core:designsystem`, `:core:navigation`, `:feature:notes:impl`, `:feature:note:impl`, `:feature:folders:api`, `:feature:folders:impl`, `:feature:auth:impl`;)
- MVI
- DI (Koin)

#### Unit Testing:

- JUnit (4.13.2)
- MockK (1.12.0)

#### List of unit tests:

1. CreateFolderUseCaseTest
2. IsEmailValidUseCaseTest
3. IsAuthUseCaseTest
4. IsPasswordValidUseCaseTest
5. SignInUseCaseImplTest
6. CreateFolderUseCaseTest
7. UpdateNoteUseCaseTest
8. GetNotesByFolderIdUseCaseTest

#### Infrastructure:

- Firebase Platform:
    - Auth (via Email)
    - Crashlytics

#### DevOps & Code Quality:

- Performance: Firebase Performance Monitoring
