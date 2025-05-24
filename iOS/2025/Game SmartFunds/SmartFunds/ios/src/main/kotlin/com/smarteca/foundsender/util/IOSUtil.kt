package com.smarteca.foundsender.util

import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.log
import com.smarteca.foundsender.util.WebUtil.WebViewController
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSArray
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSString
import org.robovm.apple.foundation.NSURL
import org.robovm.apple.messageui.MFMailComposeResult
import org.robovm.apple.messageui.MFMailComposeViewController
import org.robovm.apple.messageui.MFMailComposeViewControllerDelegateAdapter
import org.robovm.apple.safariservices.SFSafariViewController
import org.robovm.apple.safariservices.SFSafariViewControllerDelegate
import org.robovm.apple.uikit.*

object IOSUtil {

    fun openUrlInSafari(url: String) {
        val nsUrl = safeNSURL(url) ?: return

        val app = UIApplication.getSharedApplication()

        if (app.canOpenURL(nsUrl)) {
            // Виконуємо на головному потоці
            DispatchQueue.getMainQueue().async {
                app.openURL(nsUrl, UIApplicationOpenURLOptions(), null)
            }
        } else log("openUrlInSafari: ⚠️ Помилка: Неможливо відкрити URL у Safari!")
    }

    fun openUrlInSafariViewController(url: String) {
        val nsUrl = safeNSURL(url) ?: return

        DispatchQueue.getMainQueue().async {
            val safariVC           = SafariWebViewController(nsUrl)
            val window: UIWindow   = UIApplication.getSharedApplication().windows.first()
            val rootViewController = window.rootViewController

            gdxGame.pause()
            rootViewController?.presentViewController(safariVC, true, null)
        }
    }

    fun sendEmail(email: String) {
        DispatchQueue.getMainQueue().async {
            val window = UIApplication.getSharedApplication().windows.first()
            val rootViewController = window.rootViewController

            if (MFMailComposeViewController.canSendMail()) {
                val mailCompose = MFMailComposeViewController().apply {
                    setToRecipients(listOf(email))
                    setSubject("Subject of the letter")
                    setMessageBody("Message text", false)
                }

                mailCompose.mailComposeDelegate = object : MFMailComposeViewControllerDelegateAdapter() {
                    override fun didFinish(
                        controller: MFMailComposeViewController?,
                        result: MFMailComposeResult?,
                        error: NSError?
                    ) {
                        controller?.dismissViewController(true, null)
                    }
                }

                rootViewController.presentViewController(mailCompose, true, null)
            } else {
                // Якщо немає поштового клієнта, відкриваємо посилання в браузері
                //val gmailUrl = "https://mail.google.com/mail/?view=cm&to=$email"
                //val yahooUrl = "https://compose.mail.yahoo.com/?to=$email"

                val subject   = "Subject of the letter".encodeURL()
                val body      = "Message text".encodeURL()
                val mailtoUrl = "mailto:$email?subject=$subject&body=$body"
                openUrlInSafari(mailtoUrl)
            }
        }
    }

    private fun safeNSURL(url: String): NSURL? {
        if (url.isBlank()) {
            log("safeNSURL: ⚠️ URL не може бути порожнім!")
            return null
        }

        val encodedUrl = url.encodeURL()
        val nsUrl = NSURL(encodedUrl)

        if (nsUrl == null) {
            log("safeNSURL: ⚠️ Неможливо створити NSURL з $encodedUrl")
        }

        return nsUrl
    }


    private fun String.encodeURL(): String {
        return this.replace(" ", "%20")
            .replace("\n", "%0A")
            .replace("&", "%26")
            .replace("?", "%3F")
    }

    // class ----------------------------------------------------------------------------

    class SafariWebViewController(url: NSURL) : SFSafariViewController(url), SFSafariViewControllerDelegate {

        init {
            this.delegate = this // Встановлюємо делегат
            configureUI()
        }

        // 🔹 Кастомізація UI (кольори, кнопки)
        private fun configureUI() {
            //preferredBarTintColor = UIColor.darkGray() // Колір фону верхньої панелі
            //preferredControlTintColor = UIColor.white() // Колір кнопок
            //dismissButtonStyle = SFSafariViewControllerDismissButtonStyle.Done // Стиль кнопки "Закрити"
        }

        // 🔹 Відстежуємо закриття браузера
        override fun didFinish(controller: SFSafariViewController) {
            gdxGame.resume()
        }

        // 🔹 Відстежуємо перше завантаження сторінки (успішно/невдало)
        override fun didCompleteInitialLoad(controller: SFSafariViewController?, success: Boolean) {
            if (success) {
                println("🌍 Веб-сторінка успішно завантажена!")
            } else {
                println("⚠️ Помилка при завантаженні веб-сторінки!")
            }
        }

        // 🔹 Відстежуємо, якщо сторінка робить редирект
        override fun initialLoadDidRedirectToURL(controller: SFSafariViewController?, newURL: NSURL?) {
            println("🔄 Веб-сторінка змінила URL на: ${newURL?.absoluteString}")
        }

        // 🔹 Якщо користувач відкриває сторінку в Safari (замість WebView)
        override fun safariViewControllerWillOpenInBrowser(controller: SFSafariViewController?) {
            println("🌐 Сторінка відкриється в стандартному браузері Safari!")
        }

        // 🔹 Додаємо власні елементи до меню спільного доступу (наприклад, кнопка 'Поділитися')
        override fun getActivityItems(
            controller: SFSafariViewController?,
            url: NSURL?,
            title: String?
        ): NSArray<UIActivity?>? {
            println("📤 Додаємо активність до меню 'Поділитися'")
            return NSArray()
        }

        // 🔹 Вказуємо, які типи дій не можна використовувати в меню 'Поділитися'
        override fun excludedActivityTypesForURL(
            p0: SFSafariViewController?,
            p1: NSURL?,
            p2: String?
        ): NSArray<NSString?>? {
            println("⛔ Відключаємо певні типи активностей у 'Поділитися'")
            return NSArray(/*NSString("com.apple.UIKit.activity.PostToFacebook")*/) // Наприклад, забороняємо публікацію у Facebook
        }
    }

}
