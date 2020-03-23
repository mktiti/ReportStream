package com.mktiti.reportstream.model

import java.net.URL

interface DataLoader {

    fun loadBinary(url: URL): ByteArray

}
