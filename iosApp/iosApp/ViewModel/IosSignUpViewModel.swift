import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

class IosSignUpViewModel : ObservableObject{
    let viewModel: SignUpViewModel
    
    @Published var state: SignUpContract.UiState
    @Published var effect: SignUpContract.Effect?
    private var cancellables = Set<AnyCancellable>()

    init(_ viewModel: SignUpViewModel) {
        self.viewModel = viewModel
        self.state = viewModel.viewState as! SignUpContract.UiState

        createPublisher(for: viewModel.effect).assertNoFailure()
            .sink { effect in
                self.effect = effect as? SignUpContract.Effect
//                print("lulu")
            }
            .store(in: &cancellables)
    }
    
    func handleEvents(event: SignUpContract.Event) {
        self.viewModel.handleEvents(event: event)
    }
    
    
    
    deinit {
        viewModel.onCleared()
    }
    
}


