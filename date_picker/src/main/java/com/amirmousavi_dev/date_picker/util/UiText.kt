package com.amirmousavi_dev.date_picker.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.serialization.Serializable

@Serializable
sealed class UiText {

    data class DynamicString(val text: String) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        val args: Array<String> = arrayOf(),
    ) : UiText()


    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> stringResource(resId) + args.joinToString(separator = "")
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId, *args)
        }
    }


}