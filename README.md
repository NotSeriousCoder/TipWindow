# TipWindow

## 引用方法
### 1.在项目的Gradle文件
	allprojects {
		repositories {
			.......
			maven { url 'https://jitpack.io' }
		}
	}
  
### 2.在你要用这个控件的模块gradle文件
	implementation 'com.github.NotSeriousCoder:TipWindow:{lastversion}'
	lastversion目前是1.1.3

## 使用方法
### 1.列表模式
	private SimpleListAdapter adapter;
	
	if (adapter == null) {
                    ...
                    adapter = new SimpleListAdapter(activity, data, Color.parseColor("#1a56f1"));
		    	adapter.setNeedDelete(false);
                }
         new ListTipWindowBuilder(MainActivity.this)
                        .setAdapter(adapter)
			//点击空白处是否可关闭
                        .setCancelable(false)
                        .setOnItemClickListener(new OnItemClickListener<String>() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id, String data) {
                                Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemDeleteClick(int position, String data) {
                                Toast.makeText(getBaseContext(), "del==" + data, Toast.LENGTH_SHORT).show();
                            }
                        })
			//背景透明度
                        .setAlpha(0.3f)
                        .create()
                        .show(findViewById(R.id.tv));
##### 注：自定义adapter
	列表模式下可以继承GeneralAdapter<Data>，自己实现列表样式

### 2.自定义模式
	TextView tv = new TextView(MainActivity.this);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv.setText("确定要删除这个文件吗？");
                new CustomTipWindowBuilder(MainActivity.this)
                        .setOK("好的")
                        .setCancel("不要")
			//设置自定义View
                        .setContentView(tv)
			//或者设置文字，可以不设置ContentView
			//.setTextContent("确定要删除这个文件吗")
                        .setAlpha(0.3f)
                        .setCancelable(false)
                        .setOnWindowStateChangedListener(new OnWindowStateChangedListener() {
                            @Override
                            public void onOKClicked() {
                                Toast.makeText(getBaseContext(), "好吧", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {
                                Toast.makeText(getBaseContext(), "取消", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onOutsideClicked() {
                                Toast.makeText(getBaseContext(), "窗户消失", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show(findViewById(R.id.tv));

### 3.时间选择器模式
	new DateTimePickerWindowBuilder(MainActivity.this)
                        .setOK("选定")
                        .setOnDataTimeDialogListener(new OnDataTimeDialogListener() {
                            @Override
                            public void onOKClicked(@NotNull String dateTimeFormat, long dateTime) {
                                Toast.makeText(getBaseContext(), dateTimeFormat, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {

                            }

                            @Override
                            public void onOutsideClicked() {

                            }
                        })
                        //起始时间，不设置默认1-1-1 0:0
                        .setDateTimeStart(1995, 3, 20, 0, 0)
                        //终止时间，不设置默认9999-12-31 23:59
                        .setDateTimeEnd(2222, 8, 8, 23, 59)
                        //默认显示时间，不设置默认当前时间
                        .setDateTimeInit(2018, 10, 22, 10, 29)
                        .setOK("好的")
                        .setCancel("取消")
			//设置空白处不透明度（0=完全透明 1=完全不透明）
                        .setAlpha(0.2f)
                        //设置是否能点击空白处取消（对返回键无效，待改进）
                        .setCancelable(false)
                        //设置滚轮分割线颜色（同时也是顶部Tab的选中颜色）
                        .setDividerColor(Color.parseColor("#1069C2"))
                        //滚轮聚焦文字的颜色（同时也是顶部Tab的选中文字颜色）
                        .setTextColorFocus(Color.parseColor("#F10606"))
                        //滚轮正常文字的颜色（同时也是顶部Tab的非选中文字颜色）
                        .setTextColorNormal(getResources().getColor(R.color.colorAccent))
                        //设置滚轮可见项数量
                        .setVisibleItemCount(7)
                        //设置滚轮分割线宽度比例
                        .setDividerWidthRatio(0.7f)
                        //设置显示模式（日期-时间/仅日期/仅时间）
                        /**
                         * {@link DateTimePickerView#TYPE_NORMAL}
                         * {@link DateTimePickerView#TYPE_JUST_DATE}
                         * {@link DateTimePickerView#TYPE_JUST_TIME}
                         */
                        .setType(DateTimePickerView.TYPE_JUST_TIME)
                        .create()
                        .show(findViewById(R.id.bt_data_picker));


## BUG反馈
	请在Github直接提，或者邮箱找我710267819@qq.com
	
	
