//
//  iosAppApp.swift
//  iosApp
//
//  Created by Aliya Gabdullina on 11.06.2025.
//

import SwiftUI
import CommonKmp

@main
struct iOSApp: App {
    init() {
        initShared()
    }
        
        var body: some Scene {
            WindowGroup {
                NavigationView {
                    SignInScreen()
                }
            }
        }
        
        private func initShared() {
            let version = Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "0.0.1"
            let build = Bundle.main.object(forInfoDictionaryKey: "CFBundleVersion") as? String ?? "0"
            
            let isDebug: Bool
    #if Debug
            isDebug = true
    #else
            isDebug = false
    #endif
            
            let platform = PlatformConfiguration(
                appVersionName: version,
                appVersionNumber: build,
                osVersion: UIDevice.current.systemVersion,
                deviceType: Configuration.DeviceType.phone
            )
            let config = Configuration(
                platformConfiguration: platform,
                isHttpLoggingEnabled: isDebug,
                isDebug: isDebug
            )
            
            CommonKmp.shared.doInitKoin(configuration: config, appDeclaration: {_ in })
        }
}
