package com.example.notes.impl

import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.notes.data.usecase.GetNotesByFolderIdUseCaseImpl
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import com.example.shared.feature.notes.domain.repository.NotesRepository
import com.example.shared.feature.notes.domain.usecase.GetNotesByFolderIdUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetNotesByFolderIdUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository: NotesRepository = mockk()
    private lateinit var getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getNotesByFolderIdUseCase = GetNotesByFolderIdUseCaseImpl(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getNotes should return success result when repository call is successful`() =
        runTest {
            // Arrange
            val folderId = "1L"
            val expectedNotes = listOf(
                NoteDomainModel(
                    id = "1820",
                    title = "priv",
                    text = "et",
                    createDate = 9431,
                    editDate = 5094,
                    folder = FolderModel(id = "6629", name = "Efrain Gallagher")
                )
            )
            coEvery { mockRepository.getNotesByFolderId(folderId) } returns expectedNotes

            // Act
            val result = getNotesByFolderIdUseCase.invoke(folderId)

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(expectedNotes, result.getOrNull())
        }

    @Test
    fun `updateNote should return failure result when repository call fails`() = runTest {
        // Arrange
        val folderId = "-1L"
        val expectedException = RuntimeException("Folder is not exist")
        coEvery { mockRepository.getNotesByFolderId(folderId) } throws expectedException

        // Act
        val result = getNotesByFolderIdUseCase.invoke(folderId)

        // Assert
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertEquals("Folder is not exist", exception?.message)
    }

}