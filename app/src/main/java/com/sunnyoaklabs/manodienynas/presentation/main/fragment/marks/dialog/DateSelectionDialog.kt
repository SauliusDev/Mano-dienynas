package com.sunnyoaklabs.manodienynas.presentation.main.fragment.marks.dialog

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker

@Composable
fun DateSelectionDialog() {
    
}

private fun showDatePicker(
    fragmentManager: FragmentManager,
    updatedDate: (Long, Long) -> Unit
) {
    val picker = MaterialDatePicker.Builder.dateRangePicker().build()
    picker.show(fragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updatedDate(it.first, it.second)
    }
}

@Composable
fun DatePickerView(
    fragmentManager: FragmentManager,
    datePicked : String?,
    updatedDate : ( dateFirst : Long?, dateSecond : Long? ) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .border(0.75.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable{
                showDatePicker(fragmentManager, updatedDate)
            },
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val (lable, iconView) = createRefs()

            Text(
                text= datePicked?:"Date Picker",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lable) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

        }

    }
}