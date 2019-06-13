package com.example.bumptech.glide.load.resource.bitmap;

import com.example.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.example.bumptech.glide.load.resource.drawable.DrawableResource;
import com.example.bumptech.glide.util.Util;

/**
 * A resource wrapper for {@link GlideBitmapDrawable}.
 */
public class GlideBitmapDrawableResource extends DrawableResource<GlideBitmapDrawable> {
    private final BitmapPool bitmapPool;

    public GlideBitmapDrawableResource(GlideBitmapDrawable drawable, BitmapPool bitmapPool) {
        super(drawable);
        this.bitmapPool = bitmapPool;
    }

    @Override
    public int getSize() {
        return Util.getBitmapByteSize(drawable.getBitmap());
    }

    @Override
    public void recycle() {
        bitmapPool.put(drawable.getBitmap());
    }
}