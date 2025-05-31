package com.example.impl

import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.note.data.usecase.UpdateNoteUseCaseImpl
import com.example.shared.feature.note.domain.repository.NoteRepository
import com.example.shared.feature.notes.domain.model.NoteDomainModel
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
class UpdateNoteUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository: NoteRepository = mockk()
    private lateinit var updateNoteUseCase: UpdateNoteUseCaseImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        updateNoteUseCase = UpdateNoteUseCaseImpl(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateNote should return success result when repository call is successful`() =
        runTest {
            // Arrange
            val note = NoteDomainModel(
                id = "1913",
                title = "cu",
                text = "dictas",
                createDate = 1547,
                editDate = 9385,
                folder = FolderModel(id = "6853", name = "Merlin Hill")
            )
            coEvery { mockRepository.updateNote(note) } returns Unit

            // Act
            val result = updateNoteUseCase.invoke(note)

            // Assert
            assertTrue(result.isSuccess)
        }

    @Test
    fun `updateNote should return failure result when repository call fails`() = runTest {
        // Arrange
        val note = NoteDomainModel(
            id = "7564",
            title = "proin",
            text = "sed",
            createDate = 9682,
            editDate = 4305,
            folder = FolderModel(id = "8362", name = "Edmund Roach")
        )
        val expectedException = RuntimeException("Failed to update note")
        coEvery { mockRepository.updateNote(note) } throws expectedException

        // Act
        val result = updateNoteUseCase.invoke(note)

        // Assert
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertEquals("Failed to update note", exception?.message)
    }
}