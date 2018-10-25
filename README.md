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
	lastversion目前是1.1.4

## 简单使用方法，详细的使用方法请看Demo
### 1.列表模式
	private SimpleListAdapter adapter;
	
	if (adapter == null) {
                    ...
                    adapter = new SimpleListAdapter(activity, data, Color.parseColor("#1a56f1"));
                }
         new ListTipWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        //列表适配器
                        .setAdapter(adapter)
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
                        .create()
                        .show(findViewById(R.id.bt_list));
##### 注：自定义adapter
	列表模式下可以继承GeneralAdapter<Data>，自己实现列表样式

### 2.自定义模式
	new CustomTipWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        .setOK("好的")
                        .setCancel("不要")
                        .setTextContent("确定要删除这个文件吗~~")
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
                        .show(findViewById(R.id.ll_main));

### 3.时间选择器模式
	new DateTimePickerWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        .setOK("选定")
                        .setCancel("取消")
                        //日期选择器回调
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
                        .create()
                        .show(findViewById(R.id.bt_data_picker));

### 4.等待模式
	new WaitingWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        //提示语
                        .setMsg("读取中")
                        .create()
                        .show(findViewById(R.id.bt_data_picker));

## BUG反馈
	请在Github直接提，或者邮箱找我710267819@qq.com
	
	
