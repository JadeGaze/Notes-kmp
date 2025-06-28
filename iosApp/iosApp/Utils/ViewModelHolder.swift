import Foundation
import CommonKmp
import Combine

class ViewModelHolder<T: AnyObject>: ObservableObject {
    @Published var viewModel: T
    
    init(_ viewModel: T) {
        self.viewModel = viewModel
    }
}
