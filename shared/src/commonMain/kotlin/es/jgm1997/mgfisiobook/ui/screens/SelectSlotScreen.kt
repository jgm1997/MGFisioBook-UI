package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.availability.DailyAvailabilityList
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.availability.DailyAvailabilityState
import es.jgm1997.mgfisiobook.viewmodels.availability.DailyAvailabilityViewModel
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SelectSlotScreen(private val date: LocalDate, private val therapistId: Uuid) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<DailyAvailabilityViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.load(date, therapistId)
        }

        when (state) {
            is DailyAvailabilityState.Loading -> LoadingComponent()
            is DailyAvailabilityState.Error ->
                ErrorComponent((state as DailyAvailabilityState.Error).message)

            is DailyAvailabilityState.Success -> {
                val slots = (state as DailyAvailabilityState.Success).slots

                DailyAvailabilityList(
                    slots = slots,
                    onSelect = { slot ->
                        navigator?.push(
                            SelectTreatmentScreen(
                                therapistId,
                                slot.start,
                                slot.end
                            )
                        )
                    })
            }

            else -> Unit
        }
    }
}