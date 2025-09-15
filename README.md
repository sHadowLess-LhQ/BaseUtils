# BaseUtils

## 软件架构

个人自用工具类

## 安装教程

> 【注】：不提供远程库了，需要用什么直接单独复制就行，使用示例和实际有无，以代码为准（= =!懒了）

### Step 1. 添加依赖

#### a、克隆引入

直接下载源码引入model

```
//RxUtils
api 'io.reactivex.rxjava3:rxjava:3.1.8'
api 'io.reactivex.rxjava3:rxandroid:3.0.2'

//NetUtils
api 'com.squareup.okhttp3:okhttp:4.12.0'
api 'com.google.code.gson:gson:2.11.0'
api 'com.squareup.retrofit2:retrofit:2.9.0'
api 'com.squareup.retrofit2:converter-gson:2.9.0'
api 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'

//MMKVUtils
api 'com.tencent:mmkv:latest.release'

//微信
api 'com.tencent.mm.opensdk:wechat-sdk-android:latest.release'
```

## 使用说明

### PreferencesUtils

```java
//初始化
PreferencesUtils.INSTANCE.initPreferences(Context context)
//初始化自定义名称SP
PreferencesUtils.INSTANCE.initPreferences(Context context,String name)
//初始化自定义名称、模式SP
PreferencesUtils.INSTANCE.initPreferences(Context context,String name,int mode)
//同步存入字符串数据
PreferencesUtils.INSTANCE.putStringNow(String key, String value)
//异步存入字符串数据
PreferencesUtils.INSTANCE.putString(String key, String value)
//同步追加字符串集合数据
PreferencesUtils.INSTANCE.addStringSetNow(String key, String value)
//异步追加字符串集合数据
PreferencesUtils.INSTANCE.addStringSet(String key, String value)
//同步删除指定字符串集合数据
PreferencesUtils.INSTANCE.removeStringSetNow(String key, String value)
//异步删除指定字符串集合数据
PreferencesUtils.INSTANCE.removeStringSet(String key, String value)
//同步存入整型数据
PreferencesUtils.INSTANCE.putIntNow(String key, int value)
//异步存入整型数据
PreferencesUtils.INSTANCE.putInt(String key, int value)
//同步存入长整型数据
PreferencesUtils.INSTANCE.putLongNow(String key, long value)
//异步存入长整型数据
PreferencesUtils.INSTANCE.putLong(String key, long value)
//同步存入单精度浮点数据
PreferencesUtils.INSTANCE.putFloatNow(String key, float value)
//异步存入单精度浮点数据
PreferencesUtils.INSTANCE.putFloat(String key, float value)
//同步存入布尔类型数据
PreferencesUtils.INSTANCE.putBooleanNow(String key, boolean value)
//异步存入布尔类型数据
PreferencesUtils.INSTANCE.putBoolean(String key, boolean value)
//获取字符串数据
PreferencesUtils.INSTANCE.getString(String key)
PreferencesUtils.INSTANCE.getString(String key, String defaultValue)
//获取字符串集合
PreferencesUtils.INSTANCE.getStringSet(String key)
PreferencesUtils.INSTANCE.getStringSet(String key, Set<String> defaultValue)
//获取整型数据
PreferencesUtils.INSTANCE.getInt(String key)
PreferencesUtils.INSTANCE.getInt(String key, int defaultValue)
//获取长整型数据
PreferencesUtils.INSTANCE.getLong(String key)
PreferencesUtils.INSTANCE.getLong(String key, long defaultValue)
//获取单精度浮点数据
PreferencesUtils.INSTANCE.getFloat(String key)
PreferencesUtils.INSTANCE.getFloat(String key, float defaultValue)
//获取布尔类型数据
PreferencesUtils.INSTANCE.getBoolean(CString key)
PreferencesUtils.INSTANCE.getBoolean(String key, boolean defaultValue)
//同步清空SP
PreferencesUtils.INSTANCE.clearNow()
//异步清空SP
PreferencesUtils.INSTANCE.clear()
//同步删除指定数据
PreferencesUtils.INSTANCE.removeNow(String key)
//异步删除指定数据
PreferencesUtils.INSTANCE.remove(String key)
```

### RxTransformerUtils

```java
//指定Flowable上下游线程（不指定解除订阅线程）
RxTransformerUtils.setFlowableStream(Scheduler subscribeOn, Scheduler observeOn)
//指定Flowable上下游线程和解除订阅线程
RxTransformerUtils.setFlowableStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn
//指定Flowable上游线程（不指定下游）
RxTransformerUtils.setFlowableStreamWithoutObserveOn(Scheduler subscribeOn)
//指定Flowable上游线程和解除订阅线程（不指定下游）
RxTransformerUtils.setFlowableStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn)
//指定Flowable解除订阅线程和下游线程（不指定上游）
RxTransformerUtils.setFlowableStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn)
//指定Flowable解除订阅线程（不指定上下游）
RxTransformerUtils.setFlowableStreamUnsubscribeOn(Scheduler unsubscribeOn)
//指定Single上下游线程（不指定解除订阅线程）
RxTransformerUtils.setSingleStream(Scheduler subscribeOn, Scheduler observeOn)
//指定Single上下游线程和解除订阅线程
RxTransformerUtils.setSingleStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn)
//指定Single上游线程（不指定下游）
RxTransformerUtils.setSingleStreamWithoutObserveOn(Scheduler subscribeOn)
//指定Single上游线程和解除订阅线程（不指定下游）
RxTransformerUtils.setSingleStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn)
//指定Single解除订阅线程和下游线程（不指定上游）
RxTransformerUtils.setSingleStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn)
//指定Single解除订阅线程（不指定上下游）
RxTransformerUtils.setSingleStreamUnsubscribeOn(Scheduler unsubscribeOn)
//指定Maybe上下游线程（不指定解除订阅线程）
RxTransformerUtils.setMaybeStream(Scheduler subscribeOn, Scheduler observeOn)
//指定Maybe上下游线程和解除订阅线程
RxTransformerUtils.setMaybeStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn)
//指定Maybe上游线程（不指定下游）
RxTransformerUtils.setMaybeStreamWithoutObserveOn(Scheduler subscribeOn)
//指定Maybe上游线程和解除订阅线程（不指定下游）
RxTransformerUtils.setMaybeStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn)
//指定Maybe解除订阅线程和下游线程（不指定上游）
RxTransformerUtils.setMaybeStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn)
//指定Maybe解除订阅线程（不指定上下游）
RxTransformerUtils.setMaybeStreamUnsubscribeOn(Scheduler unsubscribeOn)
//指定Completable上下游线程（不指定解除订阅线程）
RxTransformerUtils.setCompletableStream(Scheduler subscribeOn, Scheduler observeOn)
//指定Completable上下游线程和解除订阅线程
RxTransformerUtils.setCompletableStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn)
//指定Completable上游线程（不指定下游）
RxTransformerUtils.setCompletableStreamWithoutObserveOn(Scheduler subscribeOn)
//指定Completable上游线程和解除订阅线程（不指定下游）
RxTransformerUtils.setCompletableStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn)
//指定Completable解除订阅线程和下游线程（不指定上游）
RxTransformerUtils.setCompletableStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn)
//指定Completable解除订阅线程（不指定上下游）
RxTransformerUtils.setCompletableStreamUnsubscribeOn(Scheduler unsubscribeOn) 
//指定Observable上下游线程（不指定解除订阅线程）
RxTransformerUtils.setObservableStream(Scheduler subscribeOn, Scheduler observeOn)
//指定Observable上下游线程和解除订阅线程
RxTransformerUtils.setObservableStream(Scheduler subscribeOn, Scheduler unsubscribeOn, Scheduler observeOn)
//指定Observable上游线程（不指定下游）
RxTransformerUtils.setObservableStreamWithoutObserveOn(Scheduler subscribeOn)
//指定Observable上游线程和解除订阅线程（不指定下游）
RxTransformerUtils.setObservableStreamWithoutObserveOn(Scheduler subscribeOn, Scheduler unsubscribeOn)
//指定Observable解除订阅线程和下游线程（不指定上游）
RxTransformerUtils.setObservableStreamWithoutSubscribeOn(Scheduler unsubscribeOn, Scheduler observeOn)
//指定Observable解除订阅线程（不指定上下游）
RxTransformerUtils.setObservableStreamUnsubscribeOn(Scheduler unsubscribeOn)
```

### RetrofitUtils

```java
RetrofitUtils retrofit = RetrofitUtils
          .builder()
          .baseUrl()       //设置根地址
          .okHttpClient()  //设置自定义okhttp，不设置有默认
          .timeOut()       //设置超时时间，不设置默认10秒
          .timeOutUnit()   //设置超时时间单位，不设置默认单位秒
          .gson(new Gson())//设置自定义Gson，不设置有默认
          .callAdapterFactory()//设置RxJava工厂类，不设置有默认
          .converterFactory()//设置解析工厂类，不设置有默认
          .build();
retrofit.initRetrofit()  //初始化Retrofit
retrofit.initApi(DeviceApi.class, DeviceApi.class); //初始化Api
//获取创建的接口对象
retrofit.getApiByApiName(String apiName) //key为传入initApi类的simpleName
//获取错误信息          
RetrofitUtils.getExceptionMessage(Throwable throwable)
```

### LocationUtils

```java
LocationUtils locationUtils = LocationUtils.builder()
          .context(Context context)     //设置上下文
          .minTime(int time)            //设置最短更新时间（单位秒），默认3秒
          .minDistance(int distance)    //设置最短更新距离，默认10
          .retryCount(int retryCount)   //设置立即获取位置信息重试次数，默认5次
          .delayTime(int delayTime)     //设置延迟获取时间（单位秒）
          .observeLife(LifecycleOwner owner)  //设置需要监听的生命周期组件（可为空，但需要手动移除监听）
          .stopEvent(Lifecycle.Event event)   //设置生命周期中止标识（默认ON_DESTROY）
          .locationListener(new LocationListener() {
              @Override
              public void onLocationChanged(@NonNull Location location) {
                     //自定义位置监听，不设置有默认监听
              }
          })
          .addressCallback(new LocationUtils.AddressCallback() {
              @Override
              public void onGetAddress(Address address) {
                  String countryName = address.getCountryName();//国家
                  String adminArea = address.getAdminArea();    //省
                  String locality = address.getLocality();      //市
                  String subLocality = address.getSubLocality();//区
                  String featureName = address.getFeatureName();//街道
                      ...
              }

              @Override
              public void onGetLocation(double lat, double lng) {
                  double mLng = lng;//经度
                  double mLat = lat;//纬度
              }

              @Override
              public void statue(String provider, boolean isEnable) {
                  //位置监听器回调
                  //回调位置提供器的状态
              }

              @Override
              public void onError(String error) {
                  //错误信息回调
                  //无权限
                  //重试失败
                  //获取详细地址信息错误
              }
          })
          .build();
//以下方法均需要设置回调后才能正常回调数据
//立即获取位置信息
locationUtils.getLocationNow();
//立即获取位置信息且设置位置变化监听
locationUtils.getLocation();
//获取经纬度
locationUtils.getLatitudeAndLongitude(Location location);
//根据经纬度获取详细地址
locationUtils.getAddressFromLatitudeAndLongitude(double latitude, double longitude);
//获取经纬度和详细地址
locationUtils.getLatitudeAndLongitudeAndAddress(Location location);
//移除位置信息监听器
locationUtils.clearLocationCallback();
```

### ApplicationUtils

```java
//通过包名打开应用
ApplicationUtils.startActivityForPackage(Context context, String packName)
//通过包名卸载应用
ApplicationUtils.startUnInstall(Context context, String packageName)
//通过路径安装应用
//【注】：MF配置的Provider中的authorities，需要包名+.fileProvider
ApplicationUtils.startInstallApk(Context context, String apkPath)
//通过包名查看应用详情
ApplicationUtils.startApplicationInfo(Context context)
ApplicationUtils.startApplicationInfo(Context context, String packageName)
//打开相机
ApplicationUtils.startCamera(Context context)
//打开浏览器访问
ApplicationUtils.startBrowser(Context context, String url)
//清除本应用数据
ApplicationUtils.clearAppData(Context context)
//获取设备唯一码
ApplicationUtils.getUUID()
//获取指定应用versionCode
ApplicationUtils.getVersionCode(Context context, String packName)
//获取指定应用versionName
ApplicationUtils.getVersionName(Context context, String packName)
//获取指定应用是否安装
ApplicationUtils.isInstall(Context context, String packageName)
//完全退出应用
ApplicationUtils.completeExitApp(Activity activity)
//获取应用信息
ApplicationUtils.getPackageInfo(Context context, String packName)
```

### DeviceUtils

> 【注】：使用前，请在MF清单文件中，给BasicDeviceAdminReceiver注册广播，并在res/xml资源中新建声明文件，也可以使用库中默认的配置文件，如下示例

```xml
<receiver
     android:name="cn.com.shadowless.baseutils.Receiver.BasicDeviceAdminReceiver"
     android:description="描述"
     android:label="名称"
     android:permission="android.permission.BIND_DEVICE_ADMIN">
     <meta-data
         android:name="android.app.device_admin"
         android:resource="@xml/basic_device_admin_receiver" />
</receiver>
```

**方法说明：**

```java
//获取DeviceAdmin权限
//在onActivityResult()回调中，判断DeviceUtils.REQUEST_CODE_CHECK_ACTIVE的请求值，是否获取成功
DeviceUtils.initDeviceAdmin(Activity context)
//创建企业空间
//在onActivityResult()回调中，判断DeviceUtils.REQUEST_CODE_CHECK_PROFILE的请求值，是否创建成功
DeviceUtils.initProfile(Activity context)
//清除DeviceOwner权限(需拥有DeviceOwner级权限)
DeviceUtils.clearDeviceOwner(Context context)
//获取拥有界面的应用列表
DeviceUtils.getApp(Context context)
//判断应用是否拥有DeviceAdmin权限
DeviceUtils.isAdminDeviceApp(Context context)
//判断应用是否拥有ProfileOwner权限
DeviceUtils.isProfileOwnerDeviceApp(Context context)
//判断应用是否拥有DeviceOwner权限
DeviceUtils.isDeviceOwnerDeviceApp(Context context)
//全局禁用企业空间应用截屏
DeviceUtils.banCompanyScreenShoot(Context context, boolean disable)
//全局禁用设备应用截屏
DeviceUtils.banDeviceScreenShoot(Context context, boolean disable)
//全局禁用企业空间应用打开摄像头
DeviceUtils.banCompanyCamera(Context context, boolean disable)
//全局禁用设备应用打开摄像头
DeviceUtils.banDeviceCamera(Context context, boolean disable)
//删除企业空间
DeviceUtils.wipeCompanyData(Context context)
//设备恢复出厂设置
DeviceUtils.wipeDeviceData(Context context)
//设置企业空间应用密码(API 23以上仅无密码时生效)
DeviceUtils.setCompanyPassword(Context context, String password)
//设置设备锁屏密码(API 23以上仅无密码时生效)
DeviceUtils.setDevicePassword(Context context, String password)
//设置用户限制
DeviceUtils.setUserRestriction(Context context, String key)
//清除指定用户限制
DeviceUtils.clearUserRestriction(Context context, String key)
//遍历清除所有用户限制(仅API 24及以上有效)
DeviceUtils.clearAllUserRestriction(Context context)
//获取所有用户限制(仅API 24及以上有效)
DeviceUtils.clearAllUserRestriction(Context context)
//通过包名清除指定应用数据(仅API 28及以上有效)
DeviceUtils.clearApplicationUserData(Context context, String packageName)
//息屏
DeviceUtils.lockScreen(Context context)
DeviceUtils.lockScreen(Context context, int flag) //仅API 26以上有效
```

### FileUtils

```java
//获取内部存储data文件夹
FileUtils.getAppData()
//获取内部存储应用file文件夹
FileUtils.getAppFile(Context context)
//获取内部存储应用file文件夹下的子文件
FileUtils.getAppFileList(Context context)
//获取内部存储应用cache文件夹
FileUtils.getAppCacheFile(Context context)
//获取内部存储应用指定文件夹(若无自动创建)
FileUtils.getAppDirFile(Context context, String fileName, int mode)
//获取内部存储应用file文件夹下创建子文件
FileUtils.getAppFileOutPut(Context context, String fileName, int mode)
//内部存储应用file文件夹读取子文件
FileUtils.getAppFileInPut(Context context, String fileName)
//内部存储应用file文件夹删除指定子文件
FileUtils.deleteAppFile(Context context, String fileName)
//获取外部储存文件夹
FileUtils.getDeviceStorageDir()
//获取外部储存应用file文件夹
FileUtils.getDeviceAppFileDir(Context context)
//获取外部储存应用cache文件夹
FileUtils.getDeviceAppCacheDir(Context context)
//获取外部储存相册文件夹
FileUtils.getDeviceDiCmDir()
//获取外部储存视频文件夹
FileUtils.getDeviceMovieDir()
//获取外部储存音乐文件夹
FileUtils.getDeviceMusicDir()
//获取外部储存下载文件夹
FileUtils.getDeviceDownloadDir()
//获取外部储存通知文件夹
FileUtils.getDeviceNotificationDir()
//获取外部储存警告文件夹
FileUtils.getDeviceAlarmsDir()
//获取外部储存播客文件夹
FileUtils.getDevicePodCastDir()
//获取外部储存铃声文件夹
FileUtils.getDeviceRingTonesDir()
//获取外部储存文件文件夹
FileUtils.getDeviceDocumentDir()
//获取外部储存截屏文件夹
FileUtils.getDeviceScreenShotDir()
//kb转mb
FileUtils.getNetFileSizeDescription(long size)
//创建文件夹
FileUtils.createFolder(String filePath)
//创建文件
FileUtils.createFile(String filePath)
//删除文件
FileUtils.deleteFile(File file, boolean isDelFolder)
//写入文件到内部存储应用file文件夹
FileUtils.writeFileToData(Context context, String fileName, int mode, byte[] data) 
//写入文件到SD卡
FileUtils.writeFileToSdCard(String dirPath, String fileName, byte[] data, int len, boolean isContinue)
```

### WindowUtils

```java
//设置状态栏隐藏风格
WindowUtils.setStatueBarHideStyle(Activity activity)
//设置隐藏状态栏
WindowUtils.hideStatusBar(Activity activity)
//设置显示状态栏
WindowUtils.showStatusBar(Activity activity)
//设置隐藏虚拟键盘
WindowUtils.hideSoftInput(Activity activity)
//设置显示虚拟键盘
WindowUtils.showSoftInput(Activity activity)
//设置隐藏导航栏
WindowUtils.hideNavigationBar(Activity activity)
//设置显示导航栏
WindowUtils.showNavigationBar(Activity activity)
//设置隐藏全屏
WindowUtils.hideSystemBar(Activity activity)
//设置显示全屏
WindowUtils.showSystemBar(Activity activity)
//设置隐藏标题栏
WindowUtils.hideCaptionBar(Activity activity)
//设置显示标题栏
WindowUtils.showCaptionBar(Activity activity)
//设置状态栏颜色
WindowUtils.setStatueBarColor(Window window, int color)
//设置导航栏颜色
WindowUtils.setNavigationBarColor(Window window, int color)
//设置导航栏是否有阴影
WindowUtils.setNavigationBarHasShadow(Window window, boolean hasShadow)
//设置导航栏是否为白色/黑字：黑色/白字
WindowUtils.setNavigationBarIsLight(Activity activity, boolean isLight)
//设置状态栏是否为白色/黑字：黑色/白字
WindowUtils.setStatueBarIsLight(Activity activity, boolean isLight)
//设置内容是否被导航栏和状态栏遮挡：自适应高度
WindowUtils.setContentFront(Window window, boolean isFront)
//设置内容与状态栏/导航栏高度自适应(适配刘海屏)
WindowUtils.setContentBelowAndTop(Activity activity, String type)
//获取屏幕宽度
WindowUtils.getDisplayWidth()
//获取屏幕高度
WindowUtils.getDisplayHeight()
```

### BaseAccessibilityService

```java
public class MyService extends BaseAccessibilityService {

  @Override
  protected void onConnected() {
      //服务已开启
  }

  @Override
  protected void viewClickListener(AccessibilityEvent event) {
      //视图单击监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // isPassword()-源是否是个密码。 
      // isChecked()-源是否选中。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
      // getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
  }

  @Override
  protected void viewLongClickListener(AccessibilityEvent event) {
      //视图长按监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // isPassword()-源是否是个密码。 
      // isChecked()-源是否选中。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
      // getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
  }

  @Override
  protected void viewSelectListener(AccessibilityEvent event) {
      //选中视图监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // isPassword()-源是否是个密码。 
      // isChecked()-源是否选中。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
      // getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
  }

  @Override
  protected void viewFocusListener(AccessibilityEvent event) {
      //聚焦的视图
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // isPassword()-源是否是个密码。 
      // isChecked()-源是否选中。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
      // getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
  } 

  @Override
  protected void etTextChangedListener(AccessibilityEvent event) {
      //编辑框文本改变监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // isPassword()-源是否是个密码。 
      // isChecked()-源是否选中。
      // getFromIndex()-文本改变起始索引。 
      // getAddedCount()-被添加的字符数。
      // getRemovedCount()-被移除的字符数。
      // getBeforeText()-改变之前的源文本。
      // getContentDescription()-源的内容描述。
  }

  @Override
  protected void etSelectedTextChangedListener(AccessibilityEvent event) {
      //编辑框选中文本改变监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // isPassword()-源是否是个密码。 
      // isChecked()-源是否选中。
      // getFromIndex()-文本改变起始索引。 
      // getAddedCount()-被添加的字符数。
      // getRemovedCount()-被移除的字符数。
      // getBeforeText()-改变之前的源文本。
      // getContentDescription()-源的内容描述。
  }

  @Override
  protected void viewScrollListener(AccessibilityEvent event) {
      //视图滚动监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。
      // getScrollY()-使用像素表示源顶边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。 
      // getItemCount()-源的总项目数（用于AdapterView的子视图）
  }

  @Override
  protected void windowStatusChangedListener(AccessibilityEvent event) {
      //窗口状态改变监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
  }

  @Override
  protected void windowContentChangedListener(AccessibilityEvent event) {
      //窗口内容改变监听
      //可用属性：
      // getEventType()-事件的类型。
      // getContentChangeTypes()-内容改变的类型。 
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
  }

  @Override
  protected void windowChangedListener(AccessibilityEvent event) {
      //窗口内容改变监听
      //可用属性：
      // getEventType()-事件类型。
      // getEventTime()-事件时间。
  }

  @Override
  protected void notificationStatusChangedListener(AccessibilityEvent event) {
      //通知监听
      //可用属性：
      // getEventType()-事件类型。
      // getClassName()-源的类名。
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getParcelableData-发布的通知（Notification）。 
      // getText-提供更多上下文的文本。
  }

  @Override
  protected void viewHoverEnterListener(AccessibilityEvent event) {
      //视图悬停进入监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。
      // getScrollY()-使用像素表示源顶边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。 
      // getItemCount()-源的总项目数（用于 AdapterView 的子视图）。
  }

  @Override
  protected void viewHoverExitListener(AccessibilityEvent event) {
      //视图悬停退出监听
      //可用属性：
      // getEventType()-事件类型。
      // getSource()-源信息（对于已注册的客户端）。 
      // getClassName()-源的类名。 
      // getPackageName()-源的包名。
      // getEventTime()-事件时间。
      // getText()-源的子树的文本。 
      // isEnabled()-源是否可用。
      // getContentDescription()-源的内容描述。
      // getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。
      // getScrollY()-使用像素表示源顶边缘的偏移量(不包含AdapterView的子视图)。 
      // getFromIndex()-源第一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。
      // getToIndex()-源最后一个可见项的从零开始的索引，包含（用于AdapterView的子视图）。 
      // getItemCount()-源的总项目数（用于 AdapterView 的子视图）。
  }

  @Override
  protected void touchInteractionStartedListener(AccessibilityEvent event) {
      //触摸交互开始监听
      //可用属性：
      // getEventType()-事件的类型。
  }

  @Override
  protected void touchInteractionEndedListener(AccessibilityEvent event) {
      //触摸交互结束出监听
      //可用属性：
      // getEventType()-事件的类型。
  }

  @Override
  protected void touchExploredStartedListener(AccessibilityEvent event) {
      //触摸浏览手势开始监听
      //可用属性：
      // getEventType()-事件的类型。
  }

  @Override
  protected void touchExploredEndedListener(AccessibilityEvent event) {
      //触摸浏览手势结束监听
      //可用属性：
      // getEventType()-事件的类型。
  }

  @Override
  protected void gestureDetectedStartListener(AccessibilityEvent event) {
      //触摸手势检测开始监听
      //可用属性：
      // getEventType()-事件的类型。
  }

  @Override
  protected void gestureDetectedEndListener(AccessibilityEvent event) {
      //触摸手势检测结束监听
      //可用属性：
      // getEventType()-事件的类型。
  }
}
```

MF文件中，注册服务，可使用库中默认的配置文件，如下示例

```xml
<service
     android:name=".HelpService"
     android:enabled="true"
     android:exported="true"
     android:foregroundServiceType="location"
     android:label="辅助服务"
     android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
     <intent-filter>
         <action android:name="android.accessibilityservice.AccessibilityService" />
     </intent-filter>
     <meta-data
         android:name="android.accessibilityservice"
         android:resource="@xml/base_accessibility_config" />
</service>
```

**方法列表**

```java
//获取辅助服务对象
HelpService.getService()
//查找指定字符的不可点击视图节点
.findViewByTextUnClickable(String text)
//延迟查找指定字符的不可点击视图节点
.findViewByTextUnClickable(String text, int milliSecond)
//查找指定字符的可点击视图节点
.findViewByTextClickable(String text)
//延迟查找指定字符的可点击视图节点
.findViewByTextClickable(String text, int milliSecond)
//查找指定视图id的不可点击视图节点
//id规范：包名:id/视图id
.findViewByIdUnClickable(String id)
//延迟查找指定视图id的不可点击视图节点
.findViewByIdUnClickable(String id, int milliSecond)
//查找指定视图id的可点击视图节点
//id规范：包名:id/视图id
.findViewByClickable(String id)
//延迟查找指定视图id的可点击视图节点
.findViewByIdClickable(String id, int milliSecond)
//单击视图节点
.performViewClick(AccessibilityNodeInfo nodeInfo)
//延迟单击视图节点
.performViewClick(AccessibilityNodeInfo nodeInfo, int milliSecond)
//长按视图节点
.performViewLongClick(AccessibilityNodeInfo nodeInfo)
//延迟长按视图节点
.performViewLongClick(AccessibilityNodeInfo nodeInfo, int milliSecond)
//执行单击返回键
.performBackClick()
//延迟单击返回键
.performBackClick(int milliSecond)
//执行分屏
.performSplitScreen()
//延迟执行分屏
.performSplitScreen(int milliSecond)
//执行截图
.performTakeScreenShot()
//延迟执行截图
.performTakeScreenShot(int milliSecond)
//执行单击最近任务
.performRecent()
//延迟执行单击最近任务
.performRecent(int milliSecond)
//执行下拉二级状态栏
.performChildStatueBar()
//延迟执行下拉二级状态栏
.performChildStatueBar(int milliSecond)
//执行弹出电源管理框
.performPowerDialog()
//延迟执行弹出电源管理框
.performPowerDialog(int milliSecond)
//执行下拉状态栏
.performStatueBar()
//延迟执行下拉状态栏
.performStatueBar(int milliSecond)
//执行单击home键
.performBackHomClicke()
//延迟执行单击home键
.performBackHomeClick(int milliSecond)
//执行锁屏
.performLockScreen()
//延迟执行锁屏
.performLockScreen(int milliSecond)
//点击指定字符的视图
.clickViewByText(String text)
//延迟点击指定字符的视图
.clickViewByText(String text, int milliSecond)
//长按指定字符的视图
.longClickViewByText(String text)
//延迟长按指定字符的视图
.longClickViewByText(String text, int milliSecond)
//点击指定id的视图
//id规范：包名:id/视图id
.clickViewById(String id)
//延迟点击指定id的视图
//id规范：包名:id/视图id
.clickViewById(String id, int milliSecond)
//长按指定id的视图
//id规范：包名:id/视图id
.longClickViewById(String id)
//延迟长按指定id的视图
//id规范：包名:id/视图id
.longClickViewById(String id, int milliSecond)
//输入指定字符到指定字符的视图
.inputTextToEtView(String view, String text)
//延迟输入指定字符到指定字符的视图
.inputTextToEtView(String view, String text, int milliSecond)
//输入指定id到指定id的视图
//id规范：包名:id/视图id
.inputIdToEtView(String view, String text)
//延迟输入指定id到指定id的视图
//id规范：包名:id/视图id
.inputIdToEtView(String view, String text, int milliSecond)
//单击指定坐标
.dispatchGestureClick(int x, int y)
//延迟单击指定坐标
.dispatchGestureClick(int x, int y, int milliSecond)
//长按指定坐标
.dispatchGestureLongClick(int x, int y)
//延迟长按指定坐标
.dispatchGestureLongClick(int x, int y, int milliSecond)
//指定坐标连续手势滑动
//参数1：x坐标
//参数2：y坐标
//参数3：滑动持续时间
//参数4：每次滑动的间隔时间
//坐标数量需要对应，否则不会执行方法
.continueSwipe(List<Integer> x, List<Integer> y, int swipeDuration, int stepDuration)
//判断辅助服务是否开启
//参数2：辅助服务类
HelpService.isAccessibilitySettingsOn(Context mContext, Class<?> cls)
```

### ToastUtils

copy轮子哥的 https://github.com/getActivity/Toaster

补了下常用四个固定样式

```java
//在Application中初始化土司
ToastUtils.init(this); 
// 显示 Toast
ToastUtils.show(CharSequence text);
ToastUtils.show(int id);
ToastUtils.show(Object object);
// debug 模式下显示 Toast
ToastUtils.debugShow(CharSequence text);
ToastUtils.debugShow(int id);
ToastUtils.debugShow(Object object);
// 延迟显示 Toast
ToastUtils.delayedShow(CharSequence text, long delayMillis);
ToastUtils.delayedShow(int id, long delayMillis);
ToastUtils.delayedShow(Object object, long delayMillis);
// 显示短 Toast
ToastUtils.showShort(CharSequence text);
ToastUtils.showShort(int id);
ToastUtils.showShort(Object object);
// 显示长 Toast
ToastUtils.showLong(CharSequence text);
ToastUtils.showLong(int id);
ToastUtils.showLong(Object object);
// 显示成功
ToastUtils.showSuccess(CharSequence text)
ToastUtils.delayShowSuccess(CharSequence text, long delayMillis);
ToastUtils.shortShowSuccess(CharSequence text);
ToastUtils.longShowSuccess(CharSequence text);
// 显示信息
ToastUtils.showInfo(CharSequence text)
ToastUtils.delayShowInfo(CharSequence text, long delayMillis);
ToastUtils.shortShowInfo(CharSequence text);
ToastUtils.longShowInfo(CharSequence text);
// 显示警告
ToastUtils.showWaring(CharSequence text)
ToastUtils.delayShowWaring(CharSequence text, long delayMillis);
ToastUtils.shortShowWaring(CharSequence text);
ToastUtils.longShowWaring(CharSequence text);
// 显示失败
ToastUtils.showError(CharSequence text)
ToastUtils.delayShowError(CharSequence text, long delayMillis);
ToastUtils.shortShowError(CharSequence text);
ToastUtils.longShowError(CharSequence text);
// 自定义显示 Toast
ToastUtils.show(ToastParams params);
// 取消 Toast
ToastUtils.cancel();
// 设置 Toast 布局（全局生效）
ToastUtils.setView(int id);
// 设置 Toast 样式（全局生效）
ToastUtils.setStyle(IToastStyle<?> style);
// 获取 Toast 样式
ToastUtils.getStyle()
// 判断当前框架是否已经初始化
ToastUtils.isInit();
// 设置 Toast 策略（全局生效）
ToastUtils.setStrategy(IToastStrategy strategy);
// 获取 Toast 策略     
ToastUtils.getStrategy();
// 设置 Toast 重心和偏移
ToastUtils.setGravity(int gravity);
ToastUtils.setGravity(int gravity, int xOffset, int yOffset);
// 设置 Toast 拦截器（全局生效）
ToastUtils.setInterceptor(IToastInterceptor interceptor);
// 获取 Toast 拦截器
ToastUtils.getInterceptor();
```

### LogUtils

```java
//配置日志库
LogUtils.getLogConfig()
           //允许显示log
           .configAllowLog(true)
           //设置log显示抬头
           .configTagPrefix("MyAppName")
           //设置显示边框
           .configShowBorders(true)
           //自定义TAG
           .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
//配置写入本地日志
LogUtils.getLog2FileConfig()
           //允许写入本地
           .configLog2FileEnable(true)
           //设置日志路径
           .configLog2FilePath("/sdcard/项目文件夹/logs/")
           //设置日志文件名
           .configLog2FileNameFormat("%d{yyyyMMdd}.txt")
           //设置日志引擎
           .configLogFileEngine(new LogFileEngineFactory());
//输出字符串
LogUtils.d("12345");
//输出参数
LogUtils.d("12%s3%d45", "a", 0);
//输出异常
LogUtils.d(new NullPointerException("12345"));
//输出对象
Person person = new Person();
person.setAge(11);
person.setName("pengwei");
person.setScore(37.5f);
LogUtils.d(person);
//对象为空
LogUtils.d(null);
//输出json（json默认debug打印）
String json = "{'a':'b','c':{'aa':234,'dd':{'az':12}}}";
LogUtils.json(json);
//打印数据集合
List<Person> list1 = new ArrayList<>();
for(int i = 0; i < 4; i++){
    list1.add(person);
}
LogUtils.d(list1);
//打印数组
double[][] doubles = {{1.2, 1.6, 1.7, 30, 33},
          {1.2, 1.6, 1.7, 30, 33},
          {1.2, 1.6, 1.7, 30, 33},
          {1.2, 1.6, 1.7, 30, 33}};
LogUtils.d(doubles);
//自定义tag
LogUtils.tag("我是自定义tag").d("我是打印内容");
//其他用法
LogUtils.v("12345");
LogUtils.i("12345");
LogUtils.w("12345");
LogUtils.e("12345");
LogUtils.wtf("12345");
```

### MmKvUtils

```java
//初始化MMKV
MmKvUtils.getInstance().initMmKv(Context context)
//初始化MMKV多进程
MmKvUtils.getInstance().initMmKvMultipleProcesses(Context context)
//初始化MMKV，指定名称，指定是否多进程
MmKvUtils.getInstance().initMmKv(Context context, String name, boolean isMultiple)
//初始化MMKV，指定路径，指定名称，指定是否多进程
MmKvUtils.getInstance().initMmKv(Context context, String path, String name, boolean isMultiple)
//放int
MmKvUtils.putInt(String key, int value)
//放String
MmKvUtils.putString(String key, String value)
//放boolean
MmKvUtils.putBoolean(String key, boolean value)
//放long
MmKvUtils.putLong(String key, long value)
//放double
MmKvUtils.putDouble(String key, double value)
//放float
MmKvUtils.putFloat(String key, float value)
//放bytes
MmKvUtils.putBytes(String key, byte[] value)
//放StringSet
MmKvUtils.putStringSet(String key, Set<String> value)
//放Parcelable
MmKvUtils.putParcelable(String key, Parcelable value)
//拿int
MmKvUtils.getInt(String key)
//拿int，有默认值
MmKvUtils.getIntWithDefault(String key, int value)
//拿String
MmKvUtils.getString(String key)
//拿String，有默认值
MmKvUtils.getStringWithDefault(String key, String value)
//拿boolean
MmKvUtils.getBoolean(String key)
//拿boolean，有默认值
MmKvUtils.getBooleanWithDefault(String key, boolean value)
//拿long
MmKvUtils.getLong(String key)
//拿long，有默认值
MmKvUtils.getLongWithDefault(String key, long value)
//拿double
MmKvUtils.getDouble(String key)
//拿double，有默认值
MmKvUtils.getDoubleWithDefault(String key, double value)
//拿float
MmKvUtils.getFloat(String key)
//拿float，有默认值
MmKvUtils.getFloatWithDefault(String key, float value)
//拿bytes
MmKvUtils.getBytes(String key)
//拿bytes，有默认值
MmKvUtils.getBytesWithDefault(String key, float value)
//拿StringSet
MmKvUtils.getStringSet(String key)
//拿StringSet，有默认值
MmKvUtils.getStringSetWithDefault(String key, Set<String> value)
//拿Parcelable
MmKvUtils.getParcelable(String key, Class<T> cls)
//拿Parcelable，有默认值
MmKvUtils.getParcelableWithDefault(String key, Class<T> cls, T t)
//查询是否有key
MmKvUtils.queryKey(String key)
//删除指定值
MmKvUtils.removeValue(String key)
//删除多个指定值
MmKvUtils.removeValues(String[] key)
```

### RouterUtils

```java
//获取指定路由的Navigator
RouterUtils.getNavigator(String path)
//获取碎片
RouterUtils.<T>getFragment(String path)
//路由显示碎片
RouterUtils.showRouterFragment(FragmentManager fragmentManager, String path, int layout)
RouterUtils.showRouterFragment(FragmentManager fragmentManager, Navigator path, int layout)
RouterUtils.showRouterFragment(FragmentManager fragmentManager, Fragment path, int layout)
//路由显示带动画碎片
RouterUtils.showRouterFragment(FragmentManager fragmentManager, String path, int layout, int... anim)
RouterUtils.showRouterFragment(FragmentManager fragmentManager, Navigator path, int layout, int... anim)
RouterUtils.showRouterFragment(FragmentManager fragmentManager, Fragment path, int layout, int... anim)
//路由替换碎片
RouterUtils.replaceRouterFragment(FragmentManager fragmentManager, String path, int layout)
RouterUtils.replaceRouterFragment(FragmentManager fragmentManager, Navigator path, int layout)
RouterUtils.replaceRouterFragment(FragmentManager fragmentManager, Fragment path, int layout)
//路由替换带动画碎片
RouterUtils.replaceRouterFragment(FragmentManager fragmentManager, String path, int layout, int... anim)
RouterUtils.replaceRouterFragment(FragmentManager fragmentManager, Navigator path, int layout, int... anim)
RouterUtils.replaceRouterFragment(FragmentManager fragmentManager, Fragment path, int layout, int... anim)
//指定路由跳转（走Application的Context）
RouterUtils.jump(String path)
//指定路径超时跳转（走Application的Context）
RouterUtils.jump(String path, int timeOut)
//指定上下文、路由跳转
RouterUtils.jump(String path, Context context)
//指定上下文、路由和路由回调跳转
RouterUtils.jump(String path, Context context, NavigationCallback callback)
//指定上下文、路由和请求返回值跳转
RouterUtils.jump(String path, Activity context, int requestCode)
//指定上下文、路由、超时和请求返回值跳转
RouterUtils.jump(String path, Activity context, int requestCode, NavigationCallback callback)
```

---

### FragmentUtils

```java
//碎片工具类，可配合ARouter使用
//显示碎片
FragmentUtils.showFragment(FragmentManager manager, Fragment fragment, int layout)
//显示带动画碎片
FragmentUtils.showFragment(FragmentManager manager, Fragment fragment, int layout, int... animation)
//替换碎片
FragmentUtils.replaceFragment(FragmentManager manager, Fragment fragment, int layout)
//替换带动画碎片
FragmentUtils.replaceFragment(FragmentManager manager, Fragment fragment, int layout, int... animation)
```

### ViewAnimatorUtils

```java
ViewAnimatorUtils
   //设置动画的视图/视图组
   .animate(View... view)
   //设置动画预设参数
   .property(String propertyName, float... values)
   //移动视图Y轴
   .translationY(float... y)
   //移动视图X轴
   .translationX(float... x)
   //设置视图透明度
   .alpha(float... alpha)
   //拉伸视图X轴
   .scaleX(float... scaleX)
   //拉伸视图Y轴
   .scaleY(float... scaleY)
   //整体拉伸视图
   .scale(float... scale)
   //X中轴旋转视图
   .pivotX(float pivotX)
   //Y中轴旋转视图
   .pivotY(float pivotY)
   //X轴旋转视图
   .rotationX(float... rotationX)
   //Y轴旋转视图
   .rotationY(float... rotationY)
   //整体旋转视图
   .rotation(float... rotation)
   //背景颜色渐变
   .backgroundColor(int... colors)
   //文字颜色渐变
   .textColor(int... colors)
   //监听动画
   .custom(final AnimationListener.Update update, float... values)
   //设置动画过程视图高度
   .height(float... height)
   //设置动画过程视图宽度
   .width(float... width)
   //创建动画集合
   .createAnimatorSet()
   //并行动画的视图
   .andAnimate(View... views)
   //顺序动画的视图
   .thenAnimate(View... views)
   //动画持续时间
   .duration(@IntRange(from = 1) long duration)
   //开始延迟动画
   .startDelay(@IntRange(from = 0) long startDelay)
   //动画重复次数
   .repeatCount(@IntRange(from = -1) int repeatCount)
   //动画重复模式
   .repeatMode(@ViewAnimatorUtils.RepeatMode int repeatMode)
   //动画开始回调
   .onStart(AnimationListener.Start startListener)
   //动画停止回调
   .onStop(AnimationListener.Stop stopListener)
   //动画终止回调
   .interpolator(Interpolator interpolator)
   //设置单个插值器
   .singleInterpolator(Interpolator interpolator)
   //加速
   .accelerate()
   //减速
   .decelerate()
   //弹入弹出
   .bounce()
   //弹入
   .bounceIn()
   //弹出
   .bounceOut()
   //淡入
   .fadeIn()
   //淡出
   .fadeOut()
   //闪出
   .flash()
   //水平反转
   .flipHorizontal()
   //垂直反转
   .flipVertical()
   //脉冲
   .pulse()
   //转入
   .rollIn()
   //转出
   .rollOut()
   //粘性
   .rubber()
   //震动
   .shake()
   //弹出
   .standUp()
   //涌动
   .swing()
   .tada()
   //波动
   .wave()
   //摆动
   .wobble()
   //放大
   .zoomIn()
   //缩小
   .zoomOut()
   //落下
   .fall()
   //报纸
   .newsPaper()
   //狭小
   .slit()
   //左侧滑进
   .slideLeftIn()
   //右侧滑进
   .slideRightIn()
   //顶部滑进
   .slideTopIn()
   //底部滑进
   .slideBottomIn()
   //SVG路径
   .path(Path path)
   //开始动画
   .start();
```

### ScreenRecordUtils

```java
RecordConfig recordConfig = RecordConfig
               .builder()
               //设置Activity级实例，不可为空
               .activity(this)
               //设置视频的宽度，默认720
               .videoWidth(1080)
               //设置视频高度，默认1280
               .videoHeight(1920)
               //设置视频文件名称，不可为空
               .fileName("xxx")
               //设置视频保存路径，不可为空
               .savePath(Constants.PATH_SCREEN_VIDEO)
               //设置是否录制音频，默认不录制
               .isRecordAudio(true)
               //设置音频编码，仅在录制音频开启生效，有默认设置
               .audioEncoder()
               //设置音频源，仅在录制音频开启生效，有默认设置
               .audioSource()
               //设置视频输出格式，有默认设置
               .outputFormat()
               //设置视频源，有默认设置
               .videoSource()
               //设置视频编码，有默认设置
               .videoEncoder()
               //设置视频帧数，有默认设置
               .videoFrameRate()
               //编码比特率，有默认设置
               .videoEncodingBitRate()
               .build();
ScreenRecordUtils screenRecordUtils = new ScreenRecordUtils(recordConfig, new ScreenRecordUtils.OnVideoRecordListener() {
           @Override
           public void onBeforeRecord() {
              //准备录制前
           }

           @Override
           public void onStartRecord() {
              //开始录制
           }

           @Override
           public void onCancelRecord() {
              //停止录制
           }

           @Override
           public void onEndRecord() {
              //录制结束
           }
       });
//开始录制
screenRecordUtils.startRecord();
//停止录制
screenRecordUtils.cancelRecord();
//是否正在录制
screenRecordUtils.isRecording();
//恢复录制
screenRecordUtils.resume();
//暂停录制
screenRecordUtils.pause();
//在重写onActivityResult()，并回传三个参数
screenRecordUtils.onActivityResult(int requestCode, int resultCode, @NotNull Intent data);
```

### AssetUtils

```java
//初始化
AssetUtils.INSTANCE.getInstance(Context context);
//复制asset文件夹内所有文件到指定目录
AssetUtils.INSTANCE.copyAssetsToSd(String srcPath, String sdPath, FileOperateCallback callback);
```

### IpUtils

```java
//获取mac地址
IpUtils.getMacAddress();
//获取ip地址
IpUtils.getIpAddress(Context context);
```

### WeChatUtils

```java
//初始化
WeChatUtils.INSTANCE.getInstance(Context context, String appId);
//微信是否可以使用
WeChatUtils.INSTANCE.canUseWeChat();
//获取微信接口
WeChatUtils.INSTANCE.getApi();
//调起微信支付
WeChatUtils.INSTANCE.openWeChatPay(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String sign);
//分享文本到微信
WeChatUtils.INSTANCE.openWeChatShareString(String text, int scene);
//分享图片位图到微信
WeChatUtils.INSTANCE.openWeChatShareImg(Bitmap bitmap, int scene);
//分享图片二进制到微信
WeChatUtils.INSTANCE.openWeChatShareImg(byte[] bitmap, int scene);
//分享视频到微信
WeChatUtils.INSTANCE.openWeChatShareVideo(String url, boolean isLow, String title, String description, Bitmap bitmap, int scene);
//分享网页到微信
WeChatUtils.INSTANCE.openWeChatShareWeb(String url, String title, String description, Bitmap bitmap, int scene);
//分享小程序到微信
WeChatUtils.INSTANCE.openWeChatShareMiniProgram(String url, int type, String programId, String path, String title, String description, Bitmap bitmap);
```

### TencentUtils

```java
//初始化
TencentUtils.INSTANCE.getInstance(Context context, String appId);
//QQ是否可以使用
TencentUtils.INSTANCE.canUseQq();
//分享图文到QQ
TencentUtils.INSTANCE.openQqShareImgString(Activity activity, String title, String description, String imgPath, IUiListener listener);
//分享图片到QQ
TencentUtils.INSTANCE.openQqShareImg(Activity activity, String imgPath, IUiListener listener);
//分享小程序到QQ
TencentUtils.INSTANCE.openQqShareMiniProgram(Activity activity, String title, String description, String url, String imgPath, String appId, String path, IUiListener listener);
```

### ViewAnimBorderUtils

```java
//快速实现任意视图边框路径动画
//以EditText为例
public class AnimatedEditText extends AppCompatEditText {

     private final ViewAnimBorderUtils animator;

     private boolean isBorder = true;

     public AnimatedEditText(Context context) {
         this(context, null);
     }
 
     public AnimatedEditText(Context context, AttributeSet attrs) {
         super(context, attrs);
         //构造工具类
         //传递必要参数
         animator = ViewAnimBorderUtils
                 .builder()
                 //需要边框的视图
                 .effectView(this)
                 //边框颜色
                 .borderColor("#818181")
                 //动画持续时间
                 .duration(2000)
                 //边框宽度
                 .borderWidth(3f)
                 //圆角弧度
                 .cornerRadius(8f)
                 .build();
         //初始化（一定要调用）
         animator.init();
     }
 
     @Override
     protected void onSizeChanged(int w, int h, int oldw, int oldh) {
         super.onSizeChanged(w, h, oldw, oldh);
         //重写onSizeChanged，并把宽高传递给工具类
         animator.onSizeChanged(w, h);
     }
 
     @Override
     protected void onDraw(Canvas canvas) {
         super.onDraw(canvas);
         //重写onDraw，把是否绘制边框和画布传递给工具类
         animator.onDraw(isBorder, canvas);
     }
           
     public void showBorder() {
         //显示边框
         animator.showBorder();
     }
 
     public void hideBorder() {
         //隐藏边框
         animator.hideBorder();
     }
}
```

### ParseUtils

```java
//字符串转短整型
ParseUtils.parseStringToInt(String string)
ParseUtils.parseStringToInt(String string, int defValue)
//字符串转短整型对象
ParseUtils.parseStringToInteger(String string)
ParseUtils.parseStringToInteger(String string, Integer defValue)
//字符串转双精度
ParseUtils.parseStringToBasicDouble(String value)
ParseUtils.parseStringToBasicDouble(String value, double defValue)
//字符串转双精度对象
ParseUtils.parseStringToObjectDouble(String value)
ParseUtils.parseStringToObjectDouble(String value, double defValue)
//字符串传长整型
ParseUtils.parseStringToBasicLong(String value)
ParseUtils.parseStringToBasicLong(String value, long defValue)
//字符串转长整型对象
ParseUtils.parseStringToObjectLong(String value)
ParseUtils.parseStringToObjectLong(String value, Long defValue)
//字符串转单精度
ParseUtils.parseStringToBasicFloat(String value)
ParseUtils.parseStringToBasicFloat(String value, float defValue)
//字符串转单精度对象
ParseUtils.parseStringToObjectFloat(String value)
ParseUtils.parseStringToObjectFloat(String value, Float defValue)
//字符串判空转布尔型
ParseUtils.parseStringToBasicBoolean(String value)
ParseUtils.parseStringToBasicBoolean(String value, boolean defValue)
//字符串判空转布尔型对象
ParseUtils.parseStringToObjectBoolean(String value)
ParseUtils.parseStringToObjectBoolean(String value, Boolean defValue)
//对象转泛型（强转）
ParseUtils.parseObjectToT(Object o)
//深拷贝数组
ParseUtils.deepCopyList(List<T> originalList)
//身份证获取性别（支持15和18位）
ParseUtils.getSexFromIdCard(String idCard)
ParseUtils.getSexNameFromIdCard(String idCard)
//身份证获取出生日期（支持15和18位）（分隔符）
ParseUtils.getBirthDayFromIdCard(String idCard, String separator)
//身份证15位转18位
ParseUtils.convertIdCard15To18(String idCard)
//正则字符串匹配（前后匹配）
ParseUtils.matchKeyWords(String keyword, String needMatchWord)
//是否有效名称
ParseUtils.isValidName(String value)
//是否有效身份证
ParseUtils.isValidIdCardNum(String idCard)
ParseUtils.isValidIdCardNumWithSex(String idCard)
//身份证脱敏
ParseUtils.desensitizedIdNumber(String idNumber)
//电话脱敏
ParseUtils.desensitizedPhoneNumber(String phoneNumber)
//身份证计算年龄
ParseUtils.calculateAgeFromIdCard(String idCard)
ParseUtils.calculateAgeFromIdCard(String idCard, String formatValue)
//出生日期计算年龄
ParseUtils.calculateAgeFromBornDate(String bornDate)
ParseUtils.calculateAgeFromBornDate(String bornDate, String formatValue)
```

### SwipeDetectorUtils

```java
//任意视图的滑动方向监听(支持八向、多指)
SwipeDetectorUtils object = new SwipeDetectorUtils();
//设置监听回调
//滑动开始(默认方法，需手动重写)：onSwipeStart(View v, MotionEvent event, FingerInfo fingerInfo)
//滑动中：onSwiping(View v, MotionEvent event, FingerInfo fingerInfo)
//滑动范围超出：onOverSwipeDetect(View v, MotionEvent event, FingerInfo fingerInfo)
//滑动结束：onSwipeEnd(View v, MotionEvent event, FingerInfo fingerInfo)
object.setOnSwipeDirectionListener(OnSwipeDirectionListener listener);
//设置最大检测距离X轴
object.setMaxDetectDistanceX(float maxDistance)
//设置最大检测距离Y轴
object.setMaxDetectDistanceY(float maxDistance)
//传入需要检测的触摸事件
object.onTouch(View v, MotionEvent event)
//设置双轴滑动阈值
object.setThresholdLimit(int min)
//设置是否持续监测滑动行为
object.setIsLongDetect(boolean longDetect)
```

### SwipeViewUtils

```java
//RecycleView Item的左右两侧滑动按钮显示
//需要配合SwipeDetectorUtils使用
//在Item布局xml中，定义好滑动按钮
//一个item只能有一种类型的滑动按钮（因为反方向的滑动事件为恢复滑动）
//传入滑动方向
SwipeViewUtils swipeViewUtils = new SwipeViewUtils(SwipeDetectorUtils.SwipeDirection swipeDirection);
//设置最大检测距离X轴
swipeViewUtils.setMaxDetectDistanceX(float maxDistance)
//设置滑动阈值
swipeViewUtils.setThresholdLimit(int min)
//是否正在滑动（用于item点击事件拦截判断）
swipeViewUtils.isSwiping()
//设置自定义滑动动画
swipeViewUtils.setCustomAnimEvent(CustomAnimEvent event)
//是否重置指定视图滑动（多用于点击后恢复）
swipeViewUtils.resetViewStateByPosition(int position)
swipeViewUtils.resetViewStateByView(View v)
//绑定RecycleView和需要滑动显示的视图id（内容视图不会跟随滑动）
swipeViewUtils.attachToRecyclerView(RecyclerView recyclerView, int swipeViewId)
//绑定RecycleView和需要滑动内容和显示的视图id（内容视图会跟随滑动）
swipeViewUtils.attachToRecyclerView(RecyclerView recyclerView, int contentId, int swipeViewId)
//解除监听绑定
swipeViewUtils.detachFromRecyclerView()
```

### DragViewUtils

```java
//RecycleView 拖拽封装
//通过ItemTouchHelper实现
//不要改造支持滑动后和SwipeViewUtils混用，两个的实现方式不一样
//传入拖拽方向
DragViewUtils dragUtils = new DragViewUtils(int dragDirs)
//绑定RecycleView
dragUtils.attachToRecyclerView(RecyclerView recyclerView)
//设置数据变更监听
dragUtils.setDataCallback(DragDataCallback dataCallback)
//设置拖拽监听
dragUtils.setDragCallback(OnItemDragListener dragCallback)
```

### CompositeFontUtils

```java
//生僻字处理
//适用于需要的生僻字存在于多个不同的字库中
//内部会自动判断生僻字码点需要使用哪个字库
//传入需要使用的生僻字字库
CompositeFontUtils font = new CompositeFontUtils(@NonNull List<Typeface> typefaceList)
//传入默认字库和需要使用的生僻字字库
CompositeFontUtils font = new CompositeFontUtils(@Nullable Typeface defaultTypeface, @NonNull List<Typeface> typefaceList)
//传入需要处理的文本（包含正常字和生僻字、只含生僻字都行）
SpannableStringBuilder builder = font.createCompositeText(String text)
textView.setText(builder)
//传入需要处理的文本（包含正常字和生僻字、只含生僻字都行）
//可实现接口完善码表与字库判断的逻辑
SpannableStringBuilder builder = font.createCompositeText(String text, FontCompareRule rule)
textView.setText(builder)
```
