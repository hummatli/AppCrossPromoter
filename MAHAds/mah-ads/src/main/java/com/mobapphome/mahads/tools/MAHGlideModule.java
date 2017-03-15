package com.mobapphome.mahads.tools;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by settar on 3/15/17.
 */

public class MAHGlideModule implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(
                new DiskLruCacheFactory(context.getFilesDir().getAbsolutePath(), "glide", 1024*1024*256));

    }

    @Override public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }
}