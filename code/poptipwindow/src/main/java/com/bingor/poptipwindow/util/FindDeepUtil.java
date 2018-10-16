package com.bingor.poptipwindow.util;


import com.bingor.poptipwindow.view.wheel.WheelItem;

import java.util.List;

public class FindDeepUtil<T extends WheelItem> {

	public int find(List<T> datas) {
		int deep = 1;
		for (int i = 0; i < datas.size(); i++) {
			int childDeep = find(datas.get(i));
			if (childDeep + 1 > deep) {
				deep = childDeep + 1;
			}
		}
		return deep;
	}

	public int find(T data) {
		if (data.getChildren() == null || data.getChildren().isEmpty()) {
			return 0;
		} else {
			return find((List<T>) data.getChildren());
		}
	}

}
