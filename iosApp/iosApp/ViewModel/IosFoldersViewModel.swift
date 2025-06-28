
import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

class IosFoldersViewModel : ObservableObject{
    let viewModel: FoldersViewModel
    
    @Published var state: FoldersContract.UiState
    @Published var effect: FoldersContract.Effect?
    private var cancellables = Set<AnyCancellable>()

    init(_ viewModel: FoldersViewModel) {
        self.viewModel = viewModel
        self.state = viewModel.viewState as! FoldersContract.UiState

        createPublisher(for: viewModel.effect).assertNoFailure()
            .sink { effect in
                self.effect = effect as? FoldersContract.Effect
//                print("lulu")
            }
            .store(in: &cancellables)
    }
    
    func handleEvents(event: FoldersContract.Event) {
        self.viewModel.handleEvents(event: event)
    }
    
    
    
    deinit {
        viewModel.onCleared()
    }
    
}
