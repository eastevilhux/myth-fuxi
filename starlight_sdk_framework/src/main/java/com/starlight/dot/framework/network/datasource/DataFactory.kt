package com.starlight.dot.framework.network.datasource

import com.starlight.dot.framework.network.datasource.Data

interface DataFactory {
    fun create() : Data
}
