import SwiftUI
import Combine
import CommonKmp

struct NotesContent: View {
    let notes: [NoteItemUiModel]
    let onItemClick: (String, String) -> Void

    var body: some View {
        List {
//            ForEach(notes) { note in
//                NoteItem(note: note) {
//                    onItemClick(note.id, note.folder.id)
//                }
//            }
        }
        .listStyle(.plain)
    }
}

struct NoteItem: View {
    let note: NoteItemUiModel
    let onItemClick: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(note.title)
                .fontWeight(.bold)
                .font(.body)
            HStack {
                Text(note.createDate)
                    .font(.caption)
                Text(note.text)
                    .font(.caption)
                    .lineLimit(1)
            }
            HStack {
                Image(systemName: "folder")
                Text(note.folder.name)
                    .font(.caption)
                    .lineLimit(1)
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(12)
        .onTapGesture {
            onItemClick()
        }
    }
}

