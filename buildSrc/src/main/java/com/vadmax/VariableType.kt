package com.vadmax

sealed class VariableType(val type: kotlin.String, val value: kotlin.String) {
    class String(value: kotlin.String) : VariableType("String", "\"$value\"")
    class Boolean(value: kotlin.Boolean) : VariableType("Boolean", value.toString())
    class Int(value: kotlin.Int) : VariableType("Int", value.toString())
}
