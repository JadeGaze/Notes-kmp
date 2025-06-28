import SwiftUI
import Combine
import CommonKmp

struct FolderItemView: View {
    let data: FolderUiModel

    var body: some View {
        HStack {
            Image(systemName: data.name)
                .padding(.trailing, 8)
            Text(data.name)
                .frame(maxWidth: .infinity, alignment: .leading)
            Text("\(data.notesNumber)")
                .foregroundColor(.secondary)
            Image(systemName: "chevron.right")
                .padding(.leading, 8)
        }
        .padding(.vertical, 4)
        .background(Color.white)
    }
}
