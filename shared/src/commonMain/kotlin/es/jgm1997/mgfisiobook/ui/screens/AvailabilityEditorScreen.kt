package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.ui.components.datetime.DateSelector
import es.jgm1997.mgfisiobook.ui.components.datetime.TimePickerSheet
import es.jgm1997.mgfisiobook.viewmodels.availability.AvailabilityEditorState
import es.jgm1997.mgfisiobook.viewmodels.availability.AvailabilityEditorViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class AvailabilityEditorScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<AvailabilityEditorViewModel>()
        val state by viewModel.state.collectAsState()
        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
        val coroutineScope = rememberCoroutineScope()
        val hours = (8..20).map { it.toString().padStart(2, '0') }
        val minutes = listOf("00", "15", "30", "45")
        var selectedDate by remember {
            mutableStateOf(
                Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            )
        }
        var pickingStart by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            viewModel.reset()
        }

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                TimePickerSheet(
                    hours = hours,
                    minutes = minutes,
                    onSelect = { hour, minute ->
                        val localTime = LocalTime(hour.toInt(), minute.toInt())
                        val instant = LocalDateTime(selectedDate, localTime).toInstant(TimeZone.UTC)

                        if (pickingStart) {
                            viewModel.updateStartTime(instant)
                        } else {
                            viewModel.updateEndTime(instant)
                        }

                        coroutineScope.launch { sheetState.hide() }
                    }
                )
            }
        ) {
            Column(Modifier.fillMaxSize()) {
                AppTopBar("Crear disponibilidad")
                Spacer(Modifier.height(8.dp))

                val days = remember {
                    (0..7).map { offset ->
                        selectedDate.plus(DatePeriod(days = offset))
                    }
                }
                DateSelector(days, selectedDate) { selectedDate = it }
                Spacer(Modifier.height(16.dp))

                when (state) {
                    is AvailabilityEditorState.Loading -> LoadingComponent(32.dp)
                    is AvailabilityEditorState.Error -> ErrorComponent(
                        (state as AvailabilityEditorState.Error).message
                    )

                    is AvailabilityEditorState.Form -> {
                        val form = state as AvailabilityEditorState.Form

                        Column(Modifier.padding(16.dp)) {
                            AvailabilityField(
                                "Hora de inicio",
                                FeatherIcons.Clock,
                                form.startTime?.toLocalDateTime(
                                    TimeZone.UTC
                                )?.time?.toString() ?: "Seleccionar"
                            ) {
                                pickingStart = true
                                coroutineScope.launch { sheetState.show() }
                            }
                            Spacer(Modifier.height(32.dp))
                            AvailabilityField(
                                "Hora de fin",
                                FeatherIcons.Clock,
                                form.endTime?.toLocalDateTime(
                                    TimeZone.UTC
                                )?.time?.toString() ?: "Seleccionar"
                            ) {
                                pickingStart = false
                                coroutineScope.launch { sheetState.show() }
                            }
                            Spacer(Modifier.height(32.dp))
                            Button(
                                onClick = { viewModel.create { navigator?.pop() } },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = form.startTime != null && form.endTime != null,
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                            ) {
                                Text(
                                    "Guardar disponibilidad",
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun AvailabilityField(
    label: String,
    icon: ImageVector,
    value: String,
    onClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colors.primary)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(label, style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(4.dp))
                Text(value, style = MaterialTheme.typography.body1)
            }

        }
    }
}