import SwiftUI
import Combine
import CommonKmp

struct SectionHeaderView: View {
    let title: String
    let isExpanded: Bool
    let onTap: () -> Void

    var body: some View {
        HStack {
            Text(title)
                .font(.headline)
            Spacer()
            Image(systemName: "chevron.right")
                .rotationEffect(.degrees(isExpanded ? 90 : 0))
                .animation(.easeInOut, value: isExpanded)
        }
        .contentShape(Rectangle())
        .onTapGesture { onTap() }
        .padding(.vertical, 8)
        .background(Color.white)
    }
}
