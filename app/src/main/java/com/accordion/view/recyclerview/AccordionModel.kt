package com.accordion.view.recyclerview

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
data class DataModel(val title: String, val desc: List<String>)

enum class TitleType {
    COLLAPSE, EXPAND
}