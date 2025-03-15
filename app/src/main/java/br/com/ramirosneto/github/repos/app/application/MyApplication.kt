package br.com.ramirosneto.github.repos.app.application

import android.app.Application
import br.com.ramirosneto.github.repos.app.di.AppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
