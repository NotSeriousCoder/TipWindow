package com.bingor.tipwindow;

import com.bingor.picker.datetimepickerdialog.wheel.WheelItem;

import java.util.List;

public class TestBean implements WheelItem {
	private List<TestBean> children;
	private String name;

	public TestBean(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<TestBean> getChildren() {
		return children;
	}

	public void setChildren(List<TestBean> children) {
		this.children = children;
	}

}
