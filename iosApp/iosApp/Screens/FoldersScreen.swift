import SwiftUI
import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

struct FoldersScreen: View {
    @StateObject private var viewModel = IosFoldersViewModel(KoinHelper().getFolders())

    @State private var showNewFolderSheet = false
    @State private var expandedSections: Set<String> = []
    
    var body: some View {
//        NavigationStack {
//            VStack {
//                if viewModel.state.isLoading {
//                    ProgressView()
//                        .frame(maxWidth: .infinity, maxHeight: .infinity)
//                } else if viewModel.state.isError {
//                    NetworkErrorView {
//                        viewModel.handleEvents(event: FoldersContract.EventRetry())
//                    }
//                } else {
//                    List {
//                        ForEach(viewModel.state.sectionData) { section in
//                            Section(header: SectionHeaderView(
//                                title: section.header,
//                                isExpanded: expandedSections.contains(section.id),
//                                onTap: {
//                                    if expandedSections.contains(section.id) {
//                                        expandedSections.remove(section.id)
//                                    } else {
//                                        expandedSections.insert(section.id)
//                                    }
//                                }
//                            )) {
//                                if expandedSections.contains(section.id) {
//                                    ForEach(section.items) { folder in
//                                        FolderItemView(data: folder)
//                                            .onTapGesture {
//                                                viewModel.handleEvents(event: FoldersContract.EventOnFolderClicked())
//                                            }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    .listStyle(.plain)
//                }
//            }
//            .navigationTitle("Folders")
//            .toolbar {
//                ToolbarItem(placement: .navigationBarTrailing) {
//                    Button {
//                        showNewFolderSheet = true
//                    } label: {
//                        Image(systemName: "folder.badge.plus")
//                    }
//                }
//            }
//            .sheet(isPresented: $showNewFolderSheet) {
//                NewFolderSheet(
//                    onDismiss: { showNewFolderSheet = false },
//                    onSave: { name, isSync in
//                        viewModel.handleEvents(event: FoldersContract.EventOnCreateNewFolderClicked())
//                        showNewFolderSheet = false
//                    }
//                )
//            }
//        }
//        .onAppear {
//            viewModel.handleEvents(event: FoldersContract.EventGetData())
//        }
    }
}
