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
	lastversion目前是1.0.7

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


## BUG反馈
	请在Github直接提，或者邮箱找我710267819@qq.com
