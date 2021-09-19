package com.vadmax.extentions

import com.android.build.api.dsl.BaseFlavor
import com.vadmax.VariableType

fun BaseFlavor.flavorConfig(name: String, value: VariableType) =
    buildConfigField(value.type, name, value.value)
