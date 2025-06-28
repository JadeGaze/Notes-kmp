import SwiftUI
import Combine
import CommonKmp

struct NetworkErrorView: View {
    let onRetry: () -> Void
    var body: some View {
        VStack(spacing: 16) {
            Text("Network Error")
            Button("Retry", action: onRetry)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
