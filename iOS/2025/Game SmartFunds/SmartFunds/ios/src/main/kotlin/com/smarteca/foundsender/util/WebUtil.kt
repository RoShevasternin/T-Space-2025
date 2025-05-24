package com.smarteca.foundsender.util

import com.smarteca.foundsender.ConfigState
import com.smarteca.foundsender.GLOBAL_ConfigState
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.game.utils.log
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSArray
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSObject
import org.robovm.apple.foundation.NSString
import org.robovm.apple.foundation.NSURL
import org.robovm.apple.foundation.NSURLAuthenticationChallenge
import org.robovm.apple.foundation.NSURLCredential
import org.robovm.apple.foundation.NSURLRequest
import org.robovm.apple.foundation.NSURLSessionAuthChallengeDisposition
import org.robovm.apple.safariservices.SFSafariViewController
import org.robovm.apple.safariservices.SFSafariViewControllerDelegate
import org.robovm.apple.safariservices.SFSafariViewControllerDismissButtonStyle
import org.robovm.apple.uikit.*
import org.robovm.apple.webkit.WKAudiovisualMediaTypes
import org.robovm.apple.webkit.WKDownload
import org.robovm.apple.webkit.WKNavigation
import org.robovm.apple.webkit.WKNavigationAction
import org.robovm.apple.webkit.WKNavigationActionPolicy
import org.robovm.apple.webkit.WKNavigationDelegate
import org.robovm.apple.webkit.WKNavigationDelegateAdapter
import org.robovm.apple.webkit.WKNavigationResponse
import org.robovm.apple.webkit.WKNavigationResponsePolicy
import org.robovm.apple.webkit.WKUIDelegate
import org.robovm.apple.webkit.WKWebView
import org.robovm.apple.webkit.WKWebViewConfiguration
import org.robovm.apple.webkit.WKWebpagePreferences
import org.robovm.apple.webkit.WKWebsiteDataStore
import org.robovm.objc.block.VoidBlock1
import org.robovm.objc.block.VoidBlock2
import org.robovm.objc.block.VoidBooleanBlock

object WebUtil {

    fun openUrlWebView(url: String) {
        DispatchQueue.getMainQueue().async {
            val window = UIApplication.getSharedApplication().windows.first()
            val rootViewController = window.rootViewController

            rootViewController?.presentViewController(WebViewController(url), true, null)
        }
    }

    class WebViewController(url: String) : UIViewController() {
        private var webView: WKWebView

        init {
            modalPresentationStyle = UIModalPresentationStyle.FullScreen

            val webConfig = WKWebViewConfiguration().apply {
                websiteDataStore = WKWebsiteDataStore.getDefaultDataStore()
                setAllowsInlineMediaPlayback(true)
                mediaTypesRequiringUserActionForPlayback = WKAudiovisualMediaTypes.None
                preferences.isJavaScriptEnabled = true
                preferences.setJavaScriptCanOpenWindowsAutomatically(true)
            }
            webView = WKWebView(view.bounds, webConfig).apply {
                navigationDelegate = WebClient()

                autoresizingMask = UIViewAutoresizing(
                    UIViewAutoresizing.FlexibleWidth.value() or UIViewAutoresizing.FlexibleHeight.value()
                )
            }

            webView.loadRequest(NSURLRequest(NSURL(url)))
            view.addSubview(webView)
        }

        // Відкриття WebView
        override fun viewWillAppear(animated: Boolean) {
            super.viewWillAppear(animated)
            gdxGame.pause()
        }

        // Закриття WebView
        override fun viewWillDisappear(animated: Boolean) {
            super.viewWillDisappear(animated)
            gdxGame.resume()
        }

        fun closeWebView() {
            dismissViewController(true, null)
        }

        private inner class WebClient : WKNavigationDelegateAdapter() {

            override fun decidePolicyForNavigationAction(
                webView: WKWebView?,
                navigationAction: WKNavigationAction?,
                decisionHandler: VoidBlock1<WKNavigationActionPolicy>?
            ) {
                val url = navigationAction?.request?.url?.absoluteString ?: return

                log("redirect: $url")

                if (url.contains("https://tluihiar")) {
                    log("contains")
                    closeWebView()
                    GLOBAL_ConfigState = ConfigState.ToGame
                    decisionHandler?.invoke(WKNavigationActionPolicy.Cancel)
                    return
                }

                decisionHandler?.invoke(WKNavigationActionPolicy.Allow)
            }
        }

    }

}
