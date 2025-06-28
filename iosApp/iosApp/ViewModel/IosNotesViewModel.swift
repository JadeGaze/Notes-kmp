import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

class IosNotesViewModel : ObservableObject{
    let viewModel: NotesViewModel
    
    @Published var state: NotesContract.UiState
    @Published var effect: NotesContract.Effect?
    private var cancellables = Set<AnyCancellable>()

    init(_ viewModel: NotesViewModel) {
        self.viewModel = viewModel
        self.state = viewModel.viewState as! NotesContract.UiState

        createPublisher(for: viewModel.effect).assertNoFailure()
            .sink { effect in
                self.effect = effect as? NotesContract.Effect
//                print("lulu")
            }
            .store(in: &cancellables)
    }
    
    func handleEvents(event: NotesContract.Event) {
        self.viewModel.handleEvents(event: event)
    }
    
    
    
    deinit {
        viewModel.onCleared()
    }
    
}

