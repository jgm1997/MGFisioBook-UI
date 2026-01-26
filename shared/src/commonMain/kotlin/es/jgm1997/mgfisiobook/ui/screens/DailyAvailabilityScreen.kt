package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.viewmodels.availability.DailyAvailabilityState
import es.jgm1997.mgfisiobook.viewmodels.availability.DailyAvailabilityViewModel
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class DailyAvailabilityScreen(val date: LocalDate, val therapistId: Uuid) : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<DailyAvailabilityViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.load(date, therapistId)
        }

        when (state) {
            is DailyAvailabilityState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.padding(32.dp))
                }
            }

            is DailyAvailabilityState.Success -> {
                val data = (state as DailyAvailabilityState.Success).data
            }

            is DailyAvailabilityState.Error ->
                ErrorComponent((state as DailyAvailabilityState.Error).message)

            else -> Unit
        }
    }
}