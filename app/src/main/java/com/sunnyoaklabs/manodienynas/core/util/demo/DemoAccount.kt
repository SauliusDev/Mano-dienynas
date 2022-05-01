package com.sunnyoaklabs.manodienynas.core.util.demo

import com.sunnyoaklabs.manodienynas.domain.model.Credentials
import com.sunnyoaklabs.manodienynas.domain.model.Event

interface DemoAccount {

    fun verifyDemoAccount(credentials: Credentials): Boolean

    fun getDataDemoAccountEvent(): List<Event>

}