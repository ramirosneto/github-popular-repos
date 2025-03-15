package br.com.ramirosneto.github.repos.app.di

import br.com.ramirosneto.github.repos.app.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}
