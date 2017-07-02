package com.mobapphome.mahads.tools

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.GlideModule

/**
 * Created by settar on 3/15/17.
 */

class MAHGlideModule : GlideModule {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(
                DiskLruCacheFactory(context.filesDir.absolutePath, "glide", 1024 * 1024 * 256))

    }

    override fun registerComponents(context: Context, glide: Glide) {
        // register ModelLoaders here.
    }
}