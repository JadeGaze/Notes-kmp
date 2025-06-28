import SwiftUI
import Combine
import CommonKmp
import Foundation

struct NotesScreen: View {
    @StateObject private var viewModel = IosNotesViewModel(KoinHelper().getNotes())

    var body: some View {
        NavigationView {
            VStack {
                if viewModel.state.isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                        .padding()
                } else if viewModel.state.isError {
                    VStack {
                        Text("Network Error")
                        Button("Retry") {
                            viewModel.handleEvents(event: NotesContract.EventRetry())
                        }
                    }
                } else {
                    NotesContent(
                        notes: viewModel.state.notesList!,
                        onItemClick: { noteId, folderId in
                            viewModel.handleEvents(event: NotesContract.EventOnNoteClicked(folderId: folderId, noteId: noteId))
                        }
                    )
                }
            }
            .toolbar {
                ToolbarItem(placement: .bottomBar) {
                    HStack {
                        Spacer()
                        Button(action: {
//                            viewModel.handleEvents(event: NotesContract.EventOnCreateNewNoteClicked(note: NoteItemUiModel(), isSync: true))
                        }) {
                            Image(systemName: "note.text.badge.plus")
                        }
                    }
                }
            }
            .onAppear {
                
            }
        }
    }
}

