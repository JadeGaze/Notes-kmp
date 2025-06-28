import SwiftUI
import Combine
import CommonKmp
import Foundation
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesCombine

struct SignUpScreen: View {
    @StateObject private var viewModel = IosSignUpViewModel(KoinHelper().getSignUp())
    
    @State private var email: String = ""
    @State private var password: String = ""
    @State private var name: String = ""

    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            Text("Sign up")
                .font(.system(size: 20, weight: .bold))
                .padding(.vertical, 16)

            TextField("your name", text: $name)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .overlay(
                    VStack {
                        if viewModel.state.isWrongName {
                            Text("Error").foregroundColor(.red).font(.caption)
                        }
                    }, alignment: .bottomLeading
                )

            TextField("example@mail.com", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .keyboardType(.emailAddress)
                .overlay(
                    VStack {
                        if viewModel.state.isWrongEmail {
                            Text("Error").foregroundColor(.red).font(.caption)
                        }
                    }, alignment: .bottomLeading
                )
            
            SecureField("password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .overlay(
                    VStack {
                        if viewModel.state.isWrongPassword {
                            Text("Error").foregroundColor(.red).font(.caption)
                        }
                    }, alignment: .bottomLeading
                )

            Button(action: {
                viewModel.handleEvents(event: SignUpContract.EventOnSignUpClicked(email: email, password: password, name: name) as SignUpContract.Event)
            }) {
                Text("sign up")
                    .font(.system(size: 16))
                    .frame(maxWidth: .infinity)
            }
            .padding(.top, 16)

            Text("Do have an account? Sign in!")
                .font(.system(size: 16))
                .padding(.top, 8)
                .onTapGesture {
                    viewModel.handleEvents(event: SignUpContract.EventOnSignInClicked())
                }

            Spacer()
        }
        .padding(.horizontal, 52)
//        .onReceive(viewModel.state) { state in
//            if state.isError {
//                // тостик
//            }
//        }
    }
}

extension View {
    func toast(isPresented: Binding<Bool>, message: String) -> some View {
        ZStack {
            self
            if isPresented.wrappedValue {
                VStack {
                    Spacer()
                    Text(message)
                        .padding()
                        .background(Color.black.opacity(0.8))
                        .foregroundColor(.white)
                        .cornerRadius(8)
                        .padding(.bottom, 50)
                }
                .transition(.move(edge: .bottom))
                .onAppear {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                        withAnimation {
                            isPresented.wrappedValue = false
                        }
                    }
                }
            }
        }
    }
}



