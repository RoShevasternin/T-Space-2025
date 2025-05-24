package com.testapp.testapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAttribution
import com.adjust.sdk.OnAttributionReadListener
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib


class TestFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val finotpuskshared =
            (activity as MainActivity).getSharedPreferences("Finotpusk", Context.MODE_PRIVATE)
        val getShared = finotpuskget(finotpuskshared)
        val finotpuskwebv = view.findViewById<WebView>(R.id.finotpuskwebv)
        finotpuskwebv.apply {
            settings.allowFileAccessFromFileURLs = true
            settings.allowContentAccess = true
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowFileAccess = true
            settings.mixedContentMode = 0
            settings.useWideViewPort = true
            settings.allowUniversalAccessFromFileURLs = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
        }
        finotpuskwebv.webViewClient = Finotpuskviewclient(finotpuskwebv)
        if (getShared == null || getShared == "empty") {
            //TODO adjust
//            Adjust.getAttribution(ReadListenerAdjust(finotpuskshared,finotpuskwebv))
            //TODO appsFlyer
            AppsFlyerLib.getInstance().init("nn8TUKjwPrWEQVRjJHf5Ag", object :
                AppsFlyerConversionListener {
                    var flag = 0
                override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                    when(flag){
                        0->{
                            flag = 1
                            val getAfAd = p0?.get("af_ad") as? String

                            val getAfAdset = p0?.get("af_adset") as? String
                            if(getAfAd!=null){
                                val domen = "https://google.com/"
                                val link = domen + "?afAdParam=$getAfAd&afAdSetParam=$getAfAdset"
                                editfinotpuskget(finotpuskshared,link)
                                //TODO nav to webvidew
                                finotpuskwebv.loadUrl(link)
                            }
                            else{
                                //TODO nav to game
                            }

                        }
                        else->{

                        }
                    }

                }

                override fun onConversionDataFail(p0: String?) {
                    when(flag){
                        0->{
                            flag = 1
                            //TODO nav to game

                        }
                        else->{

                        }
                    }

                }

                override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

                }

                override fun onAttributionFailure(p0: String?) {

                }

            }, requireActivity())
            AppsFlyerLib.getInstance().start(requireActivity())
        } else {
            finotpuskwebv.loadUrl(getShared)
        }
    }

    private fun finotpuskget(finotpuskshared: SharedPreferences): String? {
        return finotpuskshared.getString("valueInSharedPreferences", "empty")
    }

    private fun editfinotpuskget(finotpuskshared: SharedPreferences, str: String) {
        finotpuskshared.edit().putString("valueInSharedPreferences", str).apply()
    }

    inner class ReadListenerAdjust(
        val finotpuskshared: SharedPreferences,
        val finotpuskwebv: WebView
    ) :
        OnAttributionReadListener {
        override fun onAttributionRead(attribution: AdjustAttribution?) {
            val getLabel = attribution?.clickLabel
            val campaign = attribution?.campaign

            if (attribution != null) {
                if (campaign != null && campaign != "" && campaign != "None") {
                    val gr = "ps://cfdstrategy.cfd/Ggfy"
                    val completed =
                        "htt${gr}z92y?namcamp=$campaign&sidadgrp=$getLabel"

                    editfinotpuskget(finotpuskshared, completed)
                    finotpuskwebv.loadUrl(completed)
                } else {
                    //TO GAME
//                    findNavController().navigate(R.id.action_mainFragment_to_additionTripFragment)
                }
            } else {
                //TO GAME
//                findNavController().navigate(R.id.action_mainFragment_to_additionTripFragment)
            }
        }
    }

    inner class Finotpuskviewclient(val finotpuskwebv: WebView) : WebViewClient() {
        override fun onPageStarted(views: WebView?, url: String?, favicon: Bitmap?) {
            finotpuskwebv.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }
    }

}