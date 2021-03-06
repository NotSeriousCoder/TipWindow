package com.bingor.poptipwindow.view.wheel;

import java.util.List;

/**
 * 用于滑轮选择器展示的条目
 * <br />
 * Author:李玉江[QQ:1032694760]
 * DateTime:2017/04/17 00:28
 * Builder:Android Studio
 */
public interface WheelItem extends java.io.Serializable {
    String getName();

    List<? extends WheelItem> getChildren();
}
