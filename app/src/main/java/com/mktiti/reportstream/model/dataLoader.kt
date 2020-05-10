package com.mktiti.reportstream.model

import android.graphics.drawable.Drawable
import java.io.IOException
import java.net.URI

interface DataLoader {

    fun loadBinary(uri: URI): Drawable?

}

object DefaultLoader : DataLoader {

    override fun loadBinary(uri: URI): Drawable? = try {
        null
/*        uri.toURL().openStream().use { stream ->
            Drawable.createFromStream(stream, "$uri")
        }*/
    } catch (ioe: IOException) {
        null
    } catch (iae: IllegalArgumentException) {
        null
    }

}
