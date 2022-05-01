package com.sunnyoaklabs.manodienynas.core.util.demo

import android.util.Log
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.ATTENDANCE_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.CONTROL_WORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.HOMEWORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.MARK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.MESSAGE_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Event

class DemoAccountImpl: DemoAccount {

    override fun verifyDemoAccount(credentials: Credentials): Boolean {
        return (credentials.username == "DemoAccount" && credentials.password == "DemoAccount")
    }

    override fun getDataDemoAccountEvent(): List<Event> {
        return listOf(
            Event("", CONTROL_WORK_EVENT_TYPE, "Vilniaus mokykla", "2022-01-05", "2022-01-05 12:21", "Užsienio kalba (anglų)", "Past perfect and spelling test", ""),
            Event("", HOMEWORK_EVENT_TYPE, "Vilniaus mokykla", "2022-01-04", "2022-01-04 12:21", "Fizika", "Pasikartoti taisykles ir išspręsti uždavinius", ""),
            Event("", MARK_EVENT_TYPE, "Vilniaus mokykla", "2022-01-03", "2022-01-03 12:21", "Lietuvių kalba ir literatūra", "9", ""),
            Event("", ATTENDANCE_EVENT_TYPE, "Vilniaus mokykla", "2022-01-02", "2022-01-02 12:21", "Fizinis ugdymas", "n", ""),
            Event("", MESSAGE_EVENT_TYPE, "Vilniaus mokykla", "2022-01-01", "2022-01-01 12:21", "Kajus Kazlauskas", "Direktorės įsakymas dėl nuotolinio mokymo", ""),
        )
    }

}