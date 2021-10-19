# BaseUtils

#### 软件架构

个人自用项目快速搭建框架

#### 安装教程

Step 1. 添加maven仓库地址

```
    allprojects {
		repositories {
			...
              maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. 添加依赖

```
    dependencies {
	        implementation 'com.gitee.shadowless_lhq:base-utils:Tag'
	}
```	

[![](https://jitpack.io/v/com.gitee.shadowless_lhq/base-utils.svg)](https://jitpack.io/#com.gitee.shadowless_lhq/base-utils)

#### 使用说明

1.  BaseActivity：直接继承
    
    【注】：若在initData()中需要同时从多个接口获取数据，可以使用RxJava的zip操作符，将数据进行集中处理后，再通过InitDataCallBack回调

2.  BaseFragment：直接继承

    【注】：若在initData()中需要同时从多个接口获取数据，可以使用RxJava的zip操作符，将数据进行集中处理后，再通过InitDataCallBack回调

3.  CustomDialog：调用示例

```
    CustomDialog
                .builder()
                .context() //传入上下文
                .dialogView()  //传入自定义View，但和layout两者选其一调用
                .layout()  //传入xml，但和dialogView两者选其一调用
                .isClearLayer()  //是否去除蒙层，默认不去除
                .isSetAnim()  //是否添加动画，和anim()方法同步使用，不可单一使用，默认不设置
                .anim()  //设置动画xml，和isSetAnim()方法同步使用，不可单一使用
                .isSystemDialog()  //是否设置为系统级提示框，默认不设置
                .isTitle()  //是否去除标题栏，默认不去除
                .isWindowSize()  //是否设置提示框大小，和.windowHeight() .windowWidth()方法同步使用
                .windowHeight()  //设置提示框高度，和.isWindowSize()同步使用，不可单独使用
                .windowWidth()  //设置提示框宽度，和.isWindowSize()同步使用，不可单独使用
                .cancel()  //是否外部触碰取消提示框，默认不取消
                .location()  //设置提示框显示位置，传入CustomDialog.location枚举，具体枚举进入源码查看
                .build()
                .show(new CustomDialog.InitViewListener() {
                    @Override
                    public void getDialogView(View view, Dialog dialog) {
                           //回调自定义View和提示框对象
                    }
                });
```

4.   CustomWindow：调用示例

```
     CustomWindow
                .builder()
                .context()  //传入上下文
                .view()  //传入自定义View，和.layout()两者选其一调用
                .layout()  //传入xml，和.view()两者选其一调用
                .isAnimation() //是否设置动画，默认不设置，和.anim()同步使用
                .anim()  //设置窗口动画，和.isAnimation()同步使用
                .isSystem()  //是否设置为系统级窗口，默认不设置
                .width()   //设置窗口宽度
                .height()  //设置窗口高度
                .windowFlag()  //设置窗口标识
                .location()  //设置窗口显示位置，传入Gravity的静态位置常量
                .build()
                .show(new CustomWindow.InitWindowListener() {
                    @Override
                    public void getWindowView(View view, WindowManager windowManager) {
                        //回调自定义View对象和WindowManager，用于移除自定义View
                    }
                });
```

5.   PreferencesUtils：SharedPreferences所支持的所有数据类型进行操作，对于StringSet类型，调用addStringSet()方法，可进行数据累加；调用removeStringSet()方法，可进行定向元素删除

6.   RxUtils：调用示例

```
     RxUtils
                .builder()
                .build()
                //参数1：传入执行需要的线程枚举，更多枚举详见RxUtils.ThreadSign
                //参数2：create()所需的发射器回调
                //参数3：订阅执行后的结果回调
                .rxCreate(RxUtils.ThreadSign.DEFAULT, new EmitterCallBack<所需数据类型>() {
                    @Override
                    public void onEmitter(ObservableEmitter<所定义的数据类型> emitter) {

                    }
                }, new RxUtils.ObserverCallBack<所定义的数据类型>() {
                    @Override
                    public void onSuccess(所定义的数据类型 x) {
                        
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }

                    @Override
                    public void onEnd() {

                    }
                });
```

7.   NetUtils：调用示例

```
      NetUtils
                .builder()
                .baseUrl()  //设置根地址
                .okHttpClient()  //设置自定义okhttp，不设置有默认
                .timeOut()  //设置超时时间
                .timeOutUnit()  //设置超时时间单位
                .build()
                .initRetrofit(new NetUtils.InitInterface() {
                    @Override
                    public void createInterface(Retrofit retrofit) {
                        //可在回调内实例化接口
                    }
                });
```

8.    LocationUtils：调用示例

```
       //参数1：上下文
       //参数2：结果回调
       LocationUtils.getInstance(this).setAddressCallback(new LocationUtils.AddressCallback() {
            @Override
            public void onGetAddress(Address address) {
                String countryName = address.getCountryName();//国家
                String adminArea = address.getAdminArea();//省
                String locality = address.getLocality();//市
                String subLocality = address.getSubLocality();//区
                String featureName = address.getFeatureName();//街道
            }

            @Override
            public void onGetLocation(double lat, double lng) {
                AppLogger.i(DateUtils.getCurrentDateString() + " 经纬度：" + lng + "," + lat);
            }
        });
```

9.     BaseModelUtils：调用示例

```
        参数1：状态码 - int
        参数2：状态消息 - String 
        参数3：数据 - T
        BaseModelUtils.getMod(参数1，参数二，参数三);
```

10.     ApplicationUtils：方法说明

```
        //通过包名打开应用
        - startActivityForPackage(Context context, String packName)
        //通过包名卸载应用
        - startUnInstall(Context context, String packageName)
        //通过路径安装应用
        - startInstallApk(Context context, String apkPath)
        //通过包名查看应用详情
        - startApplicationInfo(Context context)
        - startApplicationInfo(Context context, String packageName)
        //打开日历
        - startCalendar(Context context)
        //打开相机
        - startCamera(Context context)
        //打开浏览器访问
        - startBrowser(Context context, String url)
        //清除本应用数据
        - clearAppData(Context context)
        //获取设备唯一码
        - getUUID()
```

11.     DeviceUtils
       【注】：使用前，请在AM清单文件中，给BasicDeviceAdminReceiver注册广播，并在res/xml资源中新建声明文件
```
          <receiver
                     android:name="cn.com.shadowless.baseutils.BasicDeviceAdminReceiver"
                     android:description="描述"
                     android:label="名称"
                     android:permission="android.permission.BIND_DEVICE_ADMIN">
                     <meta-data
                         android:name="android.app.device_admin"
                         android:resource="@xml/basic_device_admin_receiver" />
         
                     <intent-filter>
                         <action android:name="android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED" />
                         <action android:name="android.app.action.DEVICE_ADMIN_DISABLED" />
                         <action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
                     </intent-filter>
                 </receiver>
```
        方法说明：
```
        //获取DeviceAdmin权限
        //在onActivityResult()回调中，判断DeviceUtils.REQUEST_CODE_CHECK_ACTIVE的请求值，是否获取成功
        - initDeviceAdmin(Activity context)
        //创建企业空间
        //在onActivityResult()回调中，判断DeviceUtils.REQUEST_CODE_CHECK_PROFILE的请求值，是否创建成功
        - initProfile(Activity context)
        //清除DeviceOwner权限(需拥有DeviceOwner级权限)
        - clearDeviceOwner(Context context)
        //获取拥有界面的应用列表
        - getApp(Context context)
        //判断应用是否拥有DeviceAdmin权限
        - isAdminDeviceApp(Context context)
        //判断应用是否拥有ProfileOwner权限
        - isProfileOwnerDeviceApp(Context context)
        //判断应用是否拥有DeviceOwner权限
        - isDeviceOwnerDeviceApp(Context context)
        //全局禁用企业空间应用截屏
        - banCompanyScreenShoot(Context context, boolean disable)
        //全局禁用设备应用截屏
        - banDeviceScreenShoot(Context context, boolean disable)
        //全局禁用企业空间应用打开摄像头
        - banCompanyCamera(Context context, boolean disable)
        //全局禁用设备应用打开摄像头
        - banDeviceCamera(Context context, boolean disable)
        //删除企业空间
        - wipeCompanyData(Context context)
        //设备恢复出厂设置
        - wipeDeviceData(Context context)
        //设置企业空间应用密码(API 23以上仅无密码时生效)
        - setCompanyPassword(Context context, String password)
        //设置设备锁屏密码(API 23以上仅无密码时生效)
        - setDevicePassword(Context context, String password)
        //设置用户限制
        - setUserRestriction(Context context, String key)
        //清除指定用户限制
        - clearUserRestriction(Context context, String key)
        //遍历清除所有用户限制(仅API 24及以上有效)
        - clearAllUserRestriction(Context context)
        //获取所有用户限制(仅API 24及以上有效)
        - clearAllUserRestriction(Context context)
        //通过包名清除指定应用数据(仅API 28及以上有效)
        - clearApplicationUserData(Context context, String packageName)

```

#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)