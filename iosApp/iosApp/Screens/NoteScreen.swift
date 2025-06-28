import SwiftUI
import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

struct NoteScreen: View {
    @StateObject private var viewModel = IosNoteViewModel(KoinHelper().getNote())
    let onNavigationRequested: (NoteContract.Effect) -> Void
    
    @FocusState private var titleFocused: Bool
    
    @State private var title: String = ""
    @State private var text: String = ""
    
    var body: some View {
//        Group {
        VStack {
            if viewModel.state.isLoading {
                ProgressView()
            } else if viewModel.state.isError {
                VStack {
                    Text("Network error")
                    Button("Retry") {
                        viewModel.handleEvents(event: NoteContract.EventRetry())
                    }
                }
            } else {
                VStack(alignment: .leading, spacing: 16) {
                    TextField("Title", text: $title)
                        .font(.system(size: 24, weight: .bold))
                        .focused($titleFocused)
                        .onSubmit {
                            //туду
                        }
                    TextEditor(text: $text)
                        .frame(minHeight: 200)
                        .padding(.horizontal, 4)
                        .border(Color.gray.opacity(0.2))
                }
                .padding()
            }
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
                titleFocused = true
            }
            
            switch viewModel.effect  {
            case is NoteContract.EffectNoteWasLoaded:
                print("NOTE WAS LOADED")
            case is NoteContract.EffectDataLoadingError:
                print("NOTE_SCREEN_TAG: DataLoadingError")
            case is NoteContract.EffectNoteWasSave:
                print("NOTE_SCREEN_TAG: note was saved")
            case is NoteContract.EffectNavigationToPrevious:
//                NavigationLink {
//                    //туду
//                }
            case .none:
                //туду
            case .some(_):
                //туду
            }
        }
        .navigationBarBackButtonHidden(true)
//        .toolbar {
//            ToolbarItem(placement: .navigationBarLeading) {
//                Button(action: {
//                    viewModel.handleEvents(event: NoteContract.EventSaveNote(note: NoteItemUiModel(
//                            viewModel.note.id,
//                            title,
//                            text,
//                            viewModel.note.createDate,
//                            viewModel.note.editDate,
//                            viewModel.note.folder
//                            
//                    )))
//                    viewModel.handleEvents(event: NoteContract.EventOnBackClicked())
//                }) {
//                    Image(systemName: "chevron.left")
//                }
//            }
//        }
    }
}

