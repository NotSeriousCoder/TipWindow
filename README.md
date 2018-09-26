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
	lastversion目前是1.0.4

## 使用方法
### 1.常规使用
	private SimpleListAdapter adapter;
	
	if (adapter == null) {
                    ...
                    adapter = new SimpleListAdapter(activity, data, Color.parseColor("#1a56f1"));
                }
         ListTipWindow.Builder(activity)
                        .setAdapter(adapter)
                        .setOnItemClickListener(object : OnItemClickListener<String> {
                            override fun onItemDeleteClick(position: Int, item: String?) {
                               ToastMaker.makeToast(baseContext, item)
                            }

                            override fun onItemClick(p0: AdapterView<*>?, parent: View?, position: Int, id: Long, item: String?) {
                                ToastMaker.makeToast(baseContext, item)
                            }
                        })
                        .setAlpha(0.3f)
                        .create()
                        .show(findViewById(R.id.tv));
			
### 2.自定义adapter
	继承GeneralAdapter<Data>

## BUG反馈
	请在Github直接提，或者邮箱找我710267819@qq.com
