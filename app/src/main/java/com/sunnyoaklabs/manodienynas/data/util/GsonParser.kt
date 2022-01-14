package com.sunnyoaklabs.manodienynas.data.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson
): com.sunnyoaklabs.manodienynas.data.util.JsonParser {

    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}