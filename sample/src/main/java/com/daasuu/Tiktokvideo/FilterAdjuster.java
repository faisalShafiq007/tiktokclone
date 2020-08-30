package com.daasuu.Tiktokvideo;

import com.daasuu.Tiktokvideo.Tiktok.egl.filter.GlFilter;

public interface FilterAdjuster {
    public void adjust(GlFilter filter, int percentage);
}
