package com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnyoaklabs.manodienynas.R
import com.sunnyoaklabs.manodienynas.core.util.Fragments
import com.sunnyoaklabs.manodienynas.core.util.Fragments.MESSAGES_FRAGMENT
import com.sunnyoaklabs.manodienynas.core.util.Fragments.TERMS_FRAGMENT
import com.sunnyoaklabs.manodienynas.domain.model.*
import com.sunnyoaklabs.manodienynas.presentation.core.LoadingList
import com.sunnyoaklabs.manodienynas.presentation.core.disableScrolling
import com.sunnyoaklabs.manodienynas.presentation.core.getTermItemColor
import com.sunnyoaklabs.manodienynas.presentation.main.MainViewModel
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms.dialog.AbbreviationDescriptionDialog
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms.dialog.AbbreviationDescriptionDialogItem
import com.sunnyoaklabs.manodienynas.presentation.main.fragment.terms.dialog.TermMarkDialog
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.TermsFragmentViewModel
import com.sunnyoaklabs.manodienynas.ui.theme.*
import kotlinx.coroutines.flow.collect

@Composable
fun TermsFragment(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val termsFragmentViewModel = mainViewModel.termsFragmentViewModel

    val termMarkDialogItem = remember {
        mutableStateOf(
            TermMarkDialogItem(
                "", "", "", "", "", ""
            )
        )
    }
    var showDialogTermMarkDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        termsFragmentViewModel.termMarkDialogItemFlow.collect {
            it.termMarkDialogItem?.let { marksEventItemIt ->
                termMarkDialogItem.value = marksEventItemIt
            }
            if (termMarkDialogItem.value.url.isNotBlank()) {
                showDialogTermMarkDialog = true
            }
        }
    }

    val terms = termsFragmentViewModel.termState.value.terms
    val collapsableSectionTerms = mutableListOf<CollapsableSectionTerms>()
    terms.forEach { collapsableSectionTerms.add(CollapsableSectionTerms(it)) }
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        val scope = rememberCoroutineScope()
        val state = rememberLazyListState()
        state.disableScrolling(scope)
        when {
            termsFragmentViewModel.validator.validateIsLoading(
                termsFragmentViewModel.termState.value.isLoading,
                termsFragmentViewModel.termState.value.isLoadingLocale,
                terms
            ) -> {
                LoadingList(items = 10, state = state)
            }
            termsFragmentViewModel.validator.validateIsLoading(
                termsFragmentViewModel.termState.value.isLoading,
                termsFragmentViewModel.termState.value.isLoadingLocale,
                terms
            ) -> {
                EmptyTermsItem(mainViewModel)
            }
            else -> {
                TermsTopText()
                CollapsableLazyColumnTerms(
                    mainViewModel = mainViewModel,
                    sections = collapsableSectionTerms
                )
            }
        }
    }
    TermMarkDialog(
        showDialog = showDialogTermMarkDialog,
        termMarkDialogItem = termMarkDialogItem.value,
        onNegativeClick = { showDialogTermMarkDialog = false },
        onDismiss = { showDialogTermMarkDialog = false }
    )
}

@Composable
private fun TermListItem(
    mainViewModel: MainViewModel,
    i: Int,
    term: Term,
    collapsedState: SnapshotStateList<Boolean>,
    collapsed: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp).defaultMinSize(minHeight = 50.dp),
        elevation = 2.dp,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    collapsedState[i] = !collapsed
                }
                .fillMaxWidth()
                .background(
                    colorResource(
                        id = getTermItemColor(collapsed)
                    )
                )
        ) {
            Box(Modifier.weight(0.9f)) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(accentYellowDark)
                    ) {
                        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "${i+1}",
                                modifier = Modifier,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = term.subject,
                        modifier = Modifier,
                        fontSize = 16.sp
                    )
                }
            }
            Icon(
                Icons.Default.run {
                    if (collapsed)
                        KeyboardArrowDown
                    else
                        KeyboardArrowUp
                },
                contentDescription = "",
                tint = Color.LightGray,
                modifier = Modifier.padding(horizontal = 10.dp).weight(0.1f),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
    if (!collapsed) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(term.termRange.size) {
                if (term.termRange[it].title == "Metinis" && term.yearMark.isNotBlank()) {
                    TermListSingleTermItemYearly(term.yearMark)
                } else if (term.termRange[it].title != "Metinis") {
                    TermListSingleTermItem(
                        mainViewModel,
                        SingleTermItem(
                            term.termRange[it],
                            term.abbreviationMarks[it],
                            term.abbreviationMissedLessons[it],
                            term.average[it],
                            term.derived[it],
                            term.derivedInfoUrl[it],
                            term.credit[it],
                            term.creditInfoUrl[it]
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TermListSingleTermItemYearly(
    yearMark: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Text(text = "Metinis: $yearMark")
        }
    }
}

@Composable
private fun TermListSingleTermItem(
    mainViewModel: MainViewModel,
    singleTermItem: SingleTermItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Text(text = singleTermItem.termRange.title)
            Text(text = singleTermItem.termRange.date ?: "", fontSize = 12.sp, color = Color.Gray)
            Text(text = "Vidurkis: ${singleTermItem.average}")
            Text(text = singleTermItem.abbreviationMarks)
            Text(text = singleTermItem.abbreviationMissedLessons)
            if (singleTermItem.derived.isNotBlank()) {
                TermsAverageItem(
                    mainViewModel,
                    singleTermItem.derived,
                    singleTermItem.derivedInfoUrl
                )
            }
            if (singleTermItem.credit.isNotBlank()) {
                TermsCreditItem(
                    mainViewModel,
                    singleTermItem.credit,
                    singleTermItem.creditInfoUrl
                )
            }
        }
    }
}

@Composable
fun TermsAverageItem(
    mainViewModel: MainViewModel,
    average: String,
    url: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Išvesta: ")
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(accentGreenLight)
                .clickable {
                    mainViewModel.initExtraItemDataFromFragment(TERMS_FRAGMENT, TERMS_FRAGMENT, url)
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = average,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
private fun TermsCreditItem(
    mainViewModel: MainViewModel,
    average: String,
    url: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Įskaita: ")
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(accentGreenLight)
                .clickable {
                    mainViewModel.initExtraItemDataFromFragment(TERMS_FRAGMENT, TERMS_FRAGMENT, url)
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = average,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
private fun CollapsableLazyColumnTerms(
    mainViewModel: MainViewModel,
    sections: List<CollapsableSectionTerms>,
    modifier: Modifier = Modifier
) {
    val collapsedState = remember(sections) { sections.map { true }.toMutableStateList() }
    LazyColumn(modifier) {
        sections.forEachIndexed { i, dataItem ->
            val collapsed = collapsedState[i]
            item {
                TermListItem(
                    mainViewModel,
                    i,
                    dataItem.term,
                    collapsedState,
                    collapsed
                )
            }
        }
    }
}


private data class CollapsableSectionTerms(val term: Term)

private data class SingleTermItem(
    val termRange: TermRange,
    val abbreviationMarks: String,
    val abbreviationMissedLessons: String,
    val average: String,
    val derived: String,
    val derivedInfoUrl: String,
    val credit: String,
    val creditInfoUrl: String,
)

@Composable
private fun TermsTopText() {
    var showDialogLegend by remember {
        mutableStateOf(false)
    }
    val abbreviationDescriptionDialogItem by remember {
        mutableStateOf(AbbreviationDescriptionDialogItem(
            Pair("Trumpinys", "Aprašymas"),
            listOf(
                Pair("0,1..9,10,11..", "Paprastas pažymys"),
                Pair("įsk", "Įskaityta"),
                Pair("nsk", "Neįskaityta"),
                Pair("neat", "Neatestuotas"),
                Pair("atl", "Atleistas"),
                Pair("pp", "Padarė pažangą"),
                Pair("np", "Nepadarė pažangos"),
                Pair("pg", "Pagrindinis lygis"),
                Pair("a", "Aukštesnysis lygis"),
                Pair("pt", "Patenkinamas lygis"),
                Pair("npt", "Nepatenkinamas lygis"),
            )
        ))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_terms),
                contentDescription = stringResource(R.string.ic_terms_description),
                tint = accentYellowDark
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.terms_fragment_name),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = accentYellowDark
            )
        }
        Card(
            modifier = Modifier.clickable {
                showDialogLegend = true
            },
            elevation = 2.dp
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_legend),
                contentDescription = stringResource(id = R.string.go_to_settings_screen),
                tint = accentYellowDark,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
    AbbreviationDescriptionDialog(
        showDialog = showDialogLegend,
        abbreviationDescriptionDialogItem = abbreviationDescriptionDialogItem,
        onNegativeClick = { showDialogLegend = false },
        onDismiss = { showDialogLegend = false }
    )
}

@Composable
private fun EmptyTermsItem(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_folder),
                contentDescription = stringResource(id = R.string.no_data),
                modifier = Modifier.size(150.dp)
            )
            Text(text = stringResource(id = R.string.no_data))
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(
                modifier = modifier.background(Color.Transparent),
                onClick = {
                    isLoading.value = !isLoading.value
                    mainViewModel.initDataOnEmptyFragment(
                        Fragments.TERMS_FRAGMENT,
                        Fragments.TERMS_FRAGMENT
                    )
                },
                enabled = !isLoading.value
            ) {
                Icon(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = stringResource(id = R.string.reload),
                    tint = accentBlueLight
                )
            }
        }
    }
}




