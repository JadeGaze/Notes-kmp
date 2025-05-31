package com.example.feature.folders.impl

import com.example.shared.feature.folders.data.usecase.CreateFolderUseCaseImpl
import com.example.shared.feature.folders.domain.repository.FoldersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateFolderUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository: FoldersRepository = mockk()
    private lateinit var createFolderUseCase: CreateFolderUseCaseImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        createFolderUseCase = CreateFolderUseCaseImpl(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `createFolder should return success result when repository call is successful`() = runTest {
        // Arrange
        val folderName = "New Folder"
        val expectedFolderId = "123L"
        coEvery { mockRepository.createFolder(folderName, false) } returns expectedFolderId

        // Act
        val result = createFolderUseCase.invoke(folderName, false)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expectedFolderId, result.getOrNull())
    }

    @Test
    fun `createFolder should return failure result when repository call fails`() = runTest {
        // Arrange
        val folderName = "New Folder"
        val expectedException = RuntimeException("Failed to create folder")
        coEvery { mockRepository.createFolder(folderName, true) } throws expectedException

        // Act
        val result = createFolderUseCase.invoke(folderName, true)

        // Assert
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertEquals("Failed to create folder", exception?.message)
    }
}