package me.teyatha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import me.teyatha.core.EntryProviderInstaller
import me.teyatha.core.Navigator
import me.teyatha.ds.theme.TeyaTakeHomeAssignmentTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviderBuilders: Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeyaTakeHomeAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavDisplay(
                        backStack = navigator.backStack,
                        modifier = Modifier.padding(innerPadding),
                        onBack = { navigator.goBack() },
                        entryProvider = entryProvider {
                            entryProviderBuilders.forEach { builder -> this.builder() }
                        }
                    )
                }
            }
        }
    }
}