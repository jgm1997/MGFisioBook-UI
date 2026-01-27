//
// Created by Javier Gonzalez Martin on 22/1/26.
//

import UIKit
import shared

@main
class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    var window: UIWindow?
    let notificationBridge = NotificationBridge()

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        UNUserNotificationCenter.current().delegate = self
        application.registerForRemoteNotifications()
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
        }
        return true
    }

    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        let tokenString = deviceToken.map {
            String(format: "%02x", $0)
        }
        .joined()
        notificationBridge.onNewToken(token: tokenString)
    }

    func application(
        _ app: UIApplication,
        didFailToRegisterForRemoteNotificationsWithError error: Error
    ) {
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        let userInfo = notification.request.content.userInfo
        if let data = userInfo as? [String: String] {
            notificationBridge.onMessageReceived(data: data)
        }
        completionHandler([.banner, .sound])
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse,
        withCompletionHandler completionHandler: @escaping () -> Void
    ) {
        let userInfo = response.notification.request.content.userInfo
        if let data = userInfo as? [String: String] {
            notificationBridge.onMessageReceived(data: data)
        }
        completionHandler()
    }
}
