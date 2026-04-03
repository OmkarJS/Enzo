package omkar.android.projects

import android.app.Application
import omkar.android.projects.app.utils.KoinUtils
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinUtils.startKoinProcess {
            androidContext(this@MyApplication)
        }
    }
}