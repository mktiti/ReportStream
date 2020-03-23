package com.mktiti.reportstream.service

import java.net.URL

interface DataLoader {

    fun loadBinary(url: URL): ByteArray

}
