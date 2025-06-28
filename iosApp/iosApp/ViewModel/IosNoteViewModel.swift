
import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

class IosNoteViewModel : ObservableObject{
    let viewModel: NoteViewModel
    
    @Published var state: NoteContract.UiState
    @Published var effect: NoteContract.Effect?
    private var cancellables = Set<AnyCancellable>()

    init(_ viewModel: NoteViewModel) {
        self.viewModel = viewModel
        self.state = viewModel.viewState as! NoteContract.UiState

        createPublisher(for: viewModel.effect).assertNoFailure()
            .sink { effect in
                self.effect = effect as? NoteContract.Effect
//                print("lulu")
            }
            .store(in: &cancellables)
    }
    
    func handleEvents(event: NoteContract.Event) {
        self.viewModel.handleEvents(event: event)
    }
    
    
    
    deinit {
        viewModel.onCleared()
    }
    
}
