import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                // Removing this for now so that iOS rescales window size smaller when the
                // on-screen keyboard is shown:
                //.ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



