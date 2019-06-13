package com.example.bumptech.glide.load.engine;


import com.example.bumptech.glide.load.Encoder;
import com.example.bumptech.glide.load.Key;
import com.example.bumptech.glide.load.ResourceDecoder;
import com.example.bumptech.glide.load.ResourceEncoder;
import com.example.bumptech.glide.load.Transformation;
import com.example.bumptech.glide.load.resource.transcode.ResourceTranscoder;

class EngineKeyFactory {

    @SuppressWarnings("rawtypes")
    public EngineKey buildKey(String id, Key signature, int width, int height, ResourceDecoder cacheDecoder,
                              ResourceDecoder sourceDecoder, Transformation transformation, ResourceEncoder encoder,
                              ResourceTranscoder transcoder, Encoder sourceEncoder) {
        return new EngineKey(id, signature, width, height, cacheDecoder, sourceDecoder, transformation, encoder,
                transcoder, sourceEncoder);
    }

}
