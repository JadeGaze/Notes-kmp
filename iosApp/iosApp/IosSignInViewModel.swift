//
//  IosSignInViewModel.swift
//  iosApp
//
//  Created by Aliya Gabdullina on 14.06.2025.
//

import Foundation
import CommonKmp
import Combine

class IosSignInViewModel : ObservableObject {
    
    private let commonViewModel: SignInViewModel
    
    @Published
    private(set) var userId: String

    
    init(commonViewModel: SignInViewModel) {
        self.commonViewModel = commonViewModel
        
        (self.commonViewModel.userId.asPublisher() as AnyPublisher<Int, Never>).receive(on: RunLoop.main).assign(to: &$userId)

    }
    
    func handleEvents(event: SignInContract.Event) {
        self.commonViewModel.handleEvents(event: event)
    }
    
    
    
    deinit {
        commonViewModel.onCleared()
    }
    
}


