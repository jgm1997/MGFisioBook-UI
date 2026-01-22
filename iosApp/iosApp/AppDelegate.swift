//
// Created by Javier Gonzalez Martin on 22/1/26.
//

import UIKit
import shared

func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
    InitKoinKt.doInitKoin(specModule: IosModuleKt.iosModule)
    let token = deviceToken.map {
        String(format: "%02.2hhx", $0)
    }
    .joined()
    IosPushTokenHolder.shared.token = token
    IosPushTokenHolder.shared.appVersion = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String
}