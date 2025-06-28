import SwiftUI
import Combine
import CommonKmp

struct NewFolderSheet: View {
    @State private var folderName = ""
    @State private var isSync = false
    var onDismiss: () -> Void
    var onSave: (String, Bool) -> Void

    var body: some View {
        NavigationStack {
            VStack(spacing: 20) {
                TextField("Folder name", text: $folderName)
                    .padding()
                    .background(Color(.systemGray6))
                    .cornerRadius(8)

                HStack {
                    Text("Synchronization")
                    Spacer()
                    Toggle("", isOn: $isSync)
                        .labelsHidden()
                }
                .padding(.horizontal)

                Spacer()
            }
            .padding()
            .navigationTitle("New Folder")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Cancel") { onDismiss() }
                        .foregroundColor(.yellow)
                }
                ToolbarItem(placement: .confirmationAction) {
                    Button("Done") {
                        onSave(folderName, isSync)
                    }
                    .foregroundColor(.yellow)
                    .bold()
                    .disabled(folderName.isEmpty)
                }
            }
        }
    }
}
