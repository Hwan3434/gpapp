package jeonghwan.app.gpa

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GPApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        val options = FirebaseOptions.Builder()
            .setApplicationId("1:192338015806:android:d24a278d2555c152c2836f")
            .setProjectId("gplapplication")
            .build()
        FirebaseApp.initializeApp(applicationContext, options)
    }
}