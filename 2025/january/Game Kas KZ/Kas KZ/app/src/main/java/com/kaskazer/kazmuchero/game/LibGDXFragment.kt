package com.kaskazer.kazmuchero.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.kaskazer.kazmuchero.GameActivity

class LibGDXFragment : AndroidFragmentApplication() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        com.kaskazer.kazmuchero.util.log("onCreateView: ${requireActivity().hashCode()}")

            val conf = AndroidApplicationConfiguration().apply {
                a = 8
                useAccelerometer = false
                useCompass       = false
                useImmersiveMode = false
            }

        return initializeForView(LibGDXGame(requireActivity() as GameActivity), conf)
    }

}