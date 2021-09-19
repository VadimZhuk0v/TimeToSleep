package com.vadmax.extentions

import com.android.build.api.dsl.VariantDimension
import com.vadmax.VariableType

fun VariantDimension.buildConfig(name: String, value: VariableType) =
    buildConfigField(value.type, name, value.value)
