package com.smarteca.foundsender.util.permission

import com.smarteca.foundsender.game.utils.log
import org.robovm.apple.apptrackingtransparency.ATTrackingManager
import org.robovm.apple.apptrackingtransparency.ATTrackingManagerAuthorizationStatus
import org.robovm.apple.dispatch.DispatchQueue

object PermissionATT {

    fun getATTStatus(): ATTrackingManagerAuthorizationStatus {
        val status = ATTrackingManager.getTrackingAuthorizationStatus()

        when (status) {
            ATTrackingManagerAuthorizationStatus.NotDetermined -> {
                log("🔄 ATT check: Користувач ще не прийняв рішення.")
            }
            ATTrackingManagerAuthorizationStatus.Restricted -> {
                log("🚫 ATT check: Обмежений доступ (наприклад, сімейний контроль).")
            }
            ATTrackingManagerAuthorizationStatus.Denied -> {
                log("❌ ATT check: Користувач заборонив трекінг.")
            }
            ATTrackingManagerAuthorizationStatus.Authorized -> {
                log("✅ ATT check: Дозвіл отримано.")
            }
        }

        return status
    }

    fun requestATT(block: (ATTrackingManagerAuthorizationStatus) -> Unit) {
        DispatchQueue.getMainQueue().async {
            ATTrackingManager.requestTrackingAuthorization { status ->
                block(status)

                when (status) {
                    ATTrackingManagerAuthorizationStatus.Authorized -> log("✅ ATT requestATT: Дозвіл отримано.")
                    ATTrackingManagerAuthorizationStatus.Denied -> log("❌ ATT requestATT: Користувач заборонив трекінг.")
                    else -> log("⚠️ ATT requestATT: Запит пройшов, але статус не змінився. $status")
                }
            }
        }
    }

}
