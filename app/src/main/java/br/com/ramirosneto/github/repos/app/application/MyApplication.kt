package br.com.ramirosneto.github.repos.app.application

import android.app.Application
import br.com.ramirosneto.github.repos.app.di.AppComponent
import br.com.ramirosneto.github.repos.app.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}
