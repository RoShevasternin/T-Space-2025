package com.clickandbuild.motors.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.clickandbuild.motors.GameActivity
import kotlinx.coroutines.CoroutineScope

class LibGDXFragment : AndroidFragmentApplication() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conf = AndroidApplicationConfiguration().apply {
            a = 8
            useAccelerometer = false
            useCompass       = false
        }

        return initializeForView(LibGDXGame(requireActivity() as GameActivity), conf)
    }

}