package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.availability.DailyAvailabilityList
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.EmptyState
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.ui.components.datetime.DateSelector
import es.jgm1997.mgfisiobook.viewmodels.availability.DailyAvailabilityState
import es.jgm1997.mgfisiobook.viewmodels.availability.DailyAvailabilityViewModel
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SelectSlotScreen(
    private val therapistId: Uuid,
    private val initialDate: LocalDate = Clock.System.now().toLocalDateTime(
        TimeZone.UTC
    ).date
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<DailyAvailabilityViewModel>()
        val state by viewModel.state.collectAsState()
        var selectedDate by remember { mutableStateOf(initialDate) }
        val days = (0..7).map { offset -> initialDate.plus(DatePeriod(days = offset)) }

        LaunchedEffect(Unit) {
            viewModel.load(selectedDate, therapistId)
        }

        Column(Modifier.fillMaxSize()) {
            AppTopBar("Seleccionar horario")
            Spacer(Modifier.height(8.dp))

            DateSelector(days, selectedDate) { selectedDate = it }
            Spacer(Modifier.height(8.dp))

            when (state) {
                is DailyAvailabilityState.Loading -> LoadingComponent(32.dp)
                is DailyAvailabilityState.Error ->
                    ErrorComponent((state as DailyAvailabilityState.Error).message)

                is DailyAvailabilityState.Success -> {
                    val slots = (state as DailyAvailabilityState.Success).slots
                    if (slots.isEmpty()) {
                        EmptyState("No hay disponibilidad para este día")
                    } else {
                        DailyAvailabilityList(slots) { slot ->
                            navigator?.push(
                                SelectTreatmentScreen(
                                    therapistId,
                                    slot.start.toLocalDateTime(TimeZone.UTC),
                                    slot.end.toLocalDateTime(TimeZone.UTC)
                                )
                            )
                        }
                    }
                }

                else -> Unit
            }
        }


    }
}