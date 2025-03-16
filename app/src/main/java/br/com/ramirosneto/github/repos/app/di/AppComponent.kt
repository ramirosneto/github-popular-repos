package br.com.ramirosneto.github.repos.app.di

import br.com.ramirosneto.github.repos.app.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
