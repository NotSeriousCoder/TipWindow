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
	implementation 'com.github.NotSeriousCoder:NumberTipView:{lastversion}'

## 使用方法
### 1.常规使用
	private SimpleListAdapter adapter;
	
	if (adapter == null) {
                    List<String> data = new ArrayList<>();
                    data.add("13710267845");
                    data.add("15025515656");
                    data.add("13325161561");
                    data.add("18945851215");
                    data.add("18154545455");
                    data.add("16545145158");
                    data.add("15454661456");
                    data.add("18714545458");
                    adapter = new SimpleListAdapter(MainActivity.this, data, Color.parseColor("#1a56f1"));
                }
         new ListTipWindow.Builder(MainActivity.this)
                        .setAdapter(adapter)
                        .setOnItemClickListener(new OnItemClickListener<String>() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id, String data) {
                                Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setAlpha(0.3f)
                        .create()
                        .show(findViewById(R.id.tv));
			
### 2.自定义adapter
	继承GeneralAdapter<Data>

## BUG反馈
	请在Github直接提，或者邮箱找我710267819@qq.com
