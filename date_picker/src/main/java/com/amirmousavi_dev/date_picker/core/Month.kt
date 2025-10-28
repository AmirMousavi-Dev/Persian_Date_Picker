package com.amirmousavi_dev.date_picker.core

import com.amirmousavi_dev.date_picker.R
import com.amirmousavi_dev.date_picker.util.UiText


internal enum class Month(val monthNumber: Int, val monthName: UiText) {
    FARVARDIN(1, UiText.StringResource(R.string.farvardin)),
    ORDIBEHESHT(2, UiText.StringResource(R.string.ordibehesht)),
    KHORDAD(3, UiText.StringResource(R.string.khordad)),
    TIR(4, UiText.StringResource(R.string.tir)),
    MORDAD(5, UiText.StringResource(R.string.mordad)),
    SHAHRIVAR(6, UiText.StringResource(R.string.shahrivar)),
    MEHR(7, UiText.StringResource(R.string.mehr)),
    ABAN(8, UiText.StringResource(R.string.aban)),
    AZAR(9, UiText.StringResource(R.string.azar)),
    DEY(10, UiText.StringResource(R.string.dey)),
    BAHMAN(11, UiText.StringResource(R.string.bahman)),
    ESFAND(12, UiText.StringResource(R.string.esfand))
}