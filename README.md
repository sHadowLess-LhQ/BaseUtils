#  BaseUtils

#### 软件架构

个人自用项目快速搭建框架

#### 安装教程

Step 1. 添加maven仓库地址和配置

```
     //旧AndroidStudio版本
     //build.gradle
     allprojects {
         repositories {
            ...
              maven { url 'https://jitpack.io' }
         }
     }
     
     //新AndroidStudio版本
     //settings.gradle
     dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
            ...
             maven { url 'https://jitpack.io' }
          }
      }
```

```
     //主项目的build.gradle中加入
     //新AndroidStudio版本
     plugins{
       ...
        //若使用RouterUtils，请在主model添加
        id 'therouter'
     }
     
     android {
      ...
       buildFeatures {
         viewBinding true
          }
     }
     
     //主项目的build.gradle中加入
     //旧AndroidStudio版本
      ...
     //若使用RouterUtils，请添加
     apply plugin 'therouter'
     
     android {
      ...
       viewBinding {
         enable = true
          }
     }
```

```
     buildscript {
        repositories {
        google()
        mavenCentral()
      }
        dependencies {
          classpath "com.android.tools.build:gradle:7.0.2"
          //若使用RouterUtils，请添加
          classpath 'cn.therouter:plugin:1.1.1'
        }
      }

      task clean(type: Delete) {
          delete rootProject.buildDir
      }
```

Step 2. 添加依赖

a、克隆引入

```
     //删除model中build.gradle的
     apply plugin: 'com.github.dcendents.android-maven'
     group = 'com.gitee.shadowless_lhq'
```

b、远程仓库引入

[![](https://jitpack.io/v/com.gitee.shadowless_lhq/base-utils.svg)](https://jitpack.io/#com.gitee.shadowless_lhq/base-utils)

```
     dependencies {
            //主模块
            implementation 'com.gitee.shadowless_lhq:base-utils:Tag'
            implementation 'io.reactivex.rxjava2:rxjava:2.2.21'
            implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
            implementation 'com.trello.rxlifecycle3:rxlifecycle-android:3.1.0'
            
            //BasePopView
            //【注】：使用BaseXPop，请引入以下依赖
            implementation 'com.github.li-xiaojun:XPopup:2.9.4'
          
            //使用RetrofitUtils
            //【注】：使用RetrofitUtils，请引入以下依赖
            implementation 'com.squareup.okhttp3:okhttp:4.7.2'
            implementation 'com.google.code.gson:gson:2.8.9'
            implementation 'com.squareup.retrofit2:retrofit:2.9.0'
            implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
            implementation 'com.squareup.retrofit2:adapter-rxjava2:2.9.0'
        
            //MMKVUtils
            //【注】：使用MmKvUtils，请引入以下依赖
            implementation 'com.tencent:mmkv:1.2.13'
        
            //TheRouter
            //【注】：使用RouterUtils，请引入以下依赖：
            //【注】：使用RouterUtils进行携参跳转，请声明以下注解处理器
            implementation "cn.therouter:router:1.1.1"
            annotationProcessor "cn.therouter:apt:1.1.1"
    }
```

c、混淆规则

```
//主模块混淆
-keepattributes Signature
-keep class cn.com.shadowless.baseutils.log.**{*;}
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-keepclassmembers class rx.android.**{*;}

//RetrofitUtils混淆
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

//BaseXPop混淆
-dontwarn com.lxj.xpopup.widget.**
-keep class com.lxj.xpopup.widget.**{*;}
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

//TheRouter混淆
-keep class androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}
-keepclasseswithmembers class * {
    @com.therouter.router.Autowired <fields>;
}
```

#### 使用说明

### 1、BaseActivity

```
//创建xml后，点击编译，填入需要绑定的视图和传递数据类型
//click监听已做快速点击处理
//填入传递数据类型
//更换ARouter为TheRouter
//设置Activity主题，请重写initTheme()方法
//设置initData()方法所在线程，请重写initDataThreadMod()，回传RxUtils的其他线程模式
//更多用法请参考TheRouter
@Router(path = "xxx")
public class MainActivity extends BaseActivity<ActivityMainBinding,String> {

    //ARouter跳转参数获取，一定要public，name最好声明，不声明默认使用变量名为key
    //用法请参考TheRouter的使用
    @Autowired(name = "key1")
    public String s;

    @Nullable
    @Override
    protected String[] permissionName() {
       //设置需要获取的权限，无需申请可传null或空数组
       return null;
    }

    @NonNull
    @Override
    protected ActivityMainBinding setBindView() {
       //回传ViewBinding绑定的视图
       return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData(@NonNull InitDataCallBack<String> initDataCallBack) {
       //携参路由，需要页面参数注入
       TheRouter.inject(this);
       //默认在IO线程，需要更改，请重写initDataThreadMod()方法，传递新的RxUtils线程值
       //初始化数据
       【注】：若在initData()中需要同时从多个接口获取数据，可以使用RxJava的zip操作符，将数据进行集中处理后，再通过InitDataCallBack回调自己的装箱数据
       //若有数据给视图绑定，使用initViewWithData
       //若无数据给视图绑定，使用initViewWithOutData
       initDataCallBack.initViewWithData("1");
       initDataCallBack.initViewWithOutData();
    }
    
    @Override
    protected void initListener() {
       //初始化监听
       getBindView().test.setOnClickListener(this);
    }

    @Override
    protected void initView(@Nullable String data) {
       //默认在主线程
       //初始化界面控件
       getBindView().test.setText(data);
       //正常路由
       RouterUtils.jump("xxx").navigation();
    }
    
    @Override
    protected void click(View v) {
        
    }
}
```

可根据实际使用二次封装

```
public abstract class PrinterBaseActivity<VB extends ViewBinding, T> extends BaseActivity<VB, T> {
    ...
}
```

### 2、BaseFragment

```
//创建xml后，点击编译，填入需要绑定的视图
//click监听已做快速点击处理
//填入传递数据类型Pop
//设置initData()方法所在线程，请重写initDataThreadMod()，回传RxUtils的其他线程模式
//更换ARouter为TheRouter
//更多用法请参考TheRouter
@Router(path = "xxx")
public class MainFragment extends BaseFragment<FragmentMainBinding,String> {
    
    //ARouter跳转参数获取，一定要public，name最好声明，不声明默认使用变量名为key
    //用法请参考TheRouter的使用
    @Autowired(name = "key1")
    public String s;
    
    @Nullable
    @Override
    protected String[] permissionName() {
       //设置需要获取的权限，无需申请可传null或空数组
       return null;
    }

    @NonNull
    @Override
    protected FragmentMainBinding setBindView() {
        //回传ViewBinding绑定的视图
        return FragmentMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData(@NonNull InitDataCallBack<String> initDataCallBack) {
       //携参路由，需要页面参数注入
       TheRouter.inject(this);
       //默认在IO线程，需要更改，请重写initPermissions()方法，向父类super传递新的RxUtils线程值
       //初始化数据
       【注】：若在initData()中需要同时从多个接口获取数据，可以使用RxJava的zip操作符，将数据进行集中处理后，再通过InitDataCallBack回调自己的装箱数据
       Toast.makeText(getBindActivity(), "可用Activity对象", Toast.LENGTH_SHORT).show();
       //若有数据给视图绑定，使用initViewWithData
       //若无数据给视图绑定，使用initViewWithOutData
       initDataCallBack.initViewWithData("1");
       initDataCallBack.initViewWithOutData();
    }
    
    @Override
    protected void initListener() {
       //初始化监听
       getBindView().test.setOnClickListener(this);
    }

    @Override
    protected void initView(@Nullable String map) {
       //默认在主线程
       //初始化界面控件
       getBindView().test.setText(map);
       RouterUtils.jump("xxx").navigation();
    }
    
    @Override
    protected void click(View v) {
        
    }
}
```

可根据实际使用二次封装

```
public abstract class PrinterBaseFragment<VB extends ViewBinding,K,V> extends BaseFragment<VB,K,V> {
     ...
}
```

### 3、PreferencesUtils

```
     //初始化
     PreferencesUtils.getInstance().initPreferences(Context context)
     //同步存入字符串数据
     PreferencesUtils.putStringNow(String key, String value)
     //异步存入字符串数据
     PreferencesUtils.putString(String key, String value)
     //同步追加字符串集合数据
     PreferencesUtils.addStringSetNow(String key, String value)
     //异步追加字符串集合数据
     PreferencesUtils.addStringSet(String key, String value)
     //同步删除指定字符串集合数据
     PreferencesUtils.removeStringSetNow(String key, String value)
     //异步删除指定字符串集合数据
     PreferencesUtils.removeStringSet(String key, String value)
     //同步存入整型数据
     PreferencesUtils.putIntNow(String key, int value)
     //异步存入整型数据
     PreferencesUtils.putInt(String key, int value)
     //同步存入长整型数据
     PreferencesUtils.putLongNow(String key, long value)
     //异步存入长整型数据
     PreferencesUtils.putLong(String key, long value)
     //同步存入单精度浮点数据
     PreferencesUtils.putFloatNow(String key, float value)
     //异步存入单精度浮点数据
     PreferencesUtils.putFloat(String key, float value)
     //同步存入布尔类型数据
     PreferencesUtils.putBooleanNow(String key, boolean value)
     //异步存入布尔类型数据
     PreferencesUtils.putBoolean(String key, boolean value)
     //获取字符串数据
     PreferencesUtils.getString(String key)
     PreferencesUtils.getString(String key, String defaultValue)
     //获取字符串集合
     PreferencesUtils.getStringSet(String key)
     PreferencesUtils.getStringSet(String key, Set<String> defaultValue)
     //获取整型数据
     PreferencesUtils.getInt(String key)
     PreferencesUtils.getInt(String key, int defaultValue)
     //获取长整型数据
     PreferencesUtils.getLong(String key)
     PreferencesUtils.getLong(String key, long defaultValue)
     //获取单精度浮点数据
     PreferencesUtils.getFloat(String key)
     PreferencesUtils.getFloat(String key, float defaultValue)
     //获取布尔类型数据
     PreferencesUtils.getBoolean(CString key)
     PreferencesUtils.getBoolean(String key, boolean defaultValue)
     //同步清空SP
     PreferencesUtils.clearNow()
     //异步清空SP
     PreferencesUtils.clear()
     //同步删除指定数据
     PreferencesUtils.removeNow(String key)
     //异步删除指定数据
     PreferencesUtils.remove(String key)
```

### 4、RxUtils

```
     //具体参数
     /**
         * 默认
         */
        DEFAULT,
        /**
         * 非密集计算线程到主线程
         */
        IO_TO_MAIN,
        /**
         * 非密集计算线程到非密集计算线程
         */
        IO_TO_IO,
        /**
         * 非密集计算线程到新线程
         */
        IO_TO_NEW_THREAD,
        /**
         * 非密集计算线程到密集计算线程
         */
        IO_TO_COMPUTATION,
        /**
         * 非密集计算线程到有序单线程
         */
        IO_TO_SINGLE,
        /**
         * 新线程到主线程
         */
        NEW_THREAD_TO_MAIN,
        /**
         * 新线程到非密集计算线程
         */
        NEW_THREAD_TO_IO,
        /**
         * 新线程到新线程
         */
        NEW_THREAD_TO_NEW_THREAD,
        /**
         * 新线程到密集计算线程
         */
        NEW_THREAD_TO_COMPUTATION,
        /**
         * 新线程到有序单线程
         */
        NEW_THREAD_TO_SINGLE,
        /**
         * 密集计算线程到主线程
         */
        COMPUTATION_TO_MAIN,
        /**
         * 密集计算线程到非密集计算线程到
         */
        COMPUTATION_TO_IO,
        /**
         * 密集计算线程到新线程
         */
        COMPUTATION_TO_NEW_THREAD,
        /**
         * 密集计算线程到密集计算线程到
         */
        COMPUTATION_TO_COMPUTATION,
        /**
         * 密集计算线程到有序单线程
         */
        COMPUTATION_TO_SINGLE,
        /**
         * 有序单线程到主线程
         */
        SINGLE_TO_MAIN,
        /**
         * 有序单线程到非密集计算线程
         */
        SINGLE_TO_IO,
        /**
         * 有序单线程到新线程
         */
        SINGLE_TO_NEW_THREAD,
        /**
         * 有序单线程到密集计算线程
         */
        SINGLE_TO_COMPUTATION,
        /**
         * 有序单线程到有序单线程到
         */
        SINGLE_TO_SINGLE
    
     //绑定生命周期订阅
     RxUtils.bindLifecycle(@NonNull Observable<Lifecycle.Event> lifecycle)
     //绑定View的生命周期
     RxUtils.bindViewTransformer(View view)
     //绑定View指定的生命周期
     RxUtils.bindViewTransformer(View view, Lifecycle.Event event)
     //绑定Activity的生命周期
     RxUtils.bindActivityTransformer(Activity activity)
     //绑定Activity指定的生命周期
     RxUtils.bindActivityTransformer(Activity activity, ActivityEvent event)
     //绑定Fragment的生命周期
     RxUtils.bindFragmentTransformer(Fragment fragment)
     //绑定Fragment指定的生命周期
     RxUtils.bindFragmentTransformer(Fragment fragment, FragmentEvent event)
     //返回ObservableTransformer流对象
     RxUtils.dealObservableThread(int threadSign)
     //返回dealCompletableThread流对象
     RxUtils.dealCompletableThread(int threadSign)
     //返回dealMaybeThread流对象
     RxUtils.dealMaybeThread(int threadSign)
     //返回dealSingleThread流对象
     RxUtils.dealSingleThread(int threadSign)
     //返回dealFlowableThread流对象
     RxUtils.dealFlowableThread(int threadSign)
```

### 5、RetrofitUtils

```
      RetrofitUtils
                .builder()
                .baseUrl()       //设置根地址
                .okHttpClient()  //设置自定义okhttp，不设置有默认
                .timeOut()       //设置超时时间，不设置默认10秒
                .timeOutUnit()   //设置超时时间单位，不设置默认单位秒
                .gson(new Gson())//设置自定义Gson，不设置有默认
                .callAdapterFactory()//设置RxJava工厂类，不设置有默认
                .converterFactory()//设置解析工厂类，不设置有默认
                .build()
                .initRetrofit()  //初始化Retrofit
                .initRetrofit(new Gson()) //传入需要创建的接口类，默认有Gson，可自定义
                .initApi(DeviceApi.class, api -> {
                     //实例化单个接口对象，可用于单个Presenter，可多次实例不同的接口
                })
                .initApi(apiMap -> {
                     //实例化多个接口对象，可用于单Presenter，可多次实例不同的接口
                }, DeviceApi.class, DeviceApi.class);
                   ...
```

### 6、LocationUtils

```
     LocationUtils.builder()
                .mContext(context)  //设置上下文
                .minTime(30000)     //设置最短更新时间（单位毫秒），不设置默认30000
                .minDistance(10)    //设置最短更新距离，不设置默认10
                .locationListener(new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        //位置回调，不设置有默认
                    }
                })
                .build()
                .getLocation(new LocationUtils.AddressCallback() {
                    @Override
                    public void onGetAddress(Address address) {
                      String countryName = address.getCountryName();//国家
                      String adminArea = address.getAdminArea();    //省
                      String locality = address.getLocality();      //市
                      String subLocality = address.getSubLocality();//区
                      String featureName = address.getFeatureName();//街道
                    }

                    @Override
                    public void onGetLocation(double lat, double lng) {
                      double mLng = lng;//经度
                      double mLat = lat;//纬度
                    }
                });
```

### 7、ApplicationUtils

```
     //通过包名打开应用
     ApplicationUtils.startActivityForPackage(Context context, String packName)
     //通过包名卸载应用
     ApplicationUtils.startUnInstall(Context context, String packageName)
     //通过路径安装应用
     【注】：MF配置的Provider中的authorities，需要包名+.fileProvider
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
```

### 8、DeviceUtils

【注】：使用前，请在MF清单文件中，给BasicDeviceAdminReceiver注册广播，并在res/xml资源中新建声明文件，也可以使用库中默认的配置文件，如下示例

```
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

方法说明：

```
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

### 9、FileUtils

```
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

### 10、WindowUtils

```
     //隐藏状态栏
     WindowUtils.hideStatusBar(Activity activity)
     //设置状态栏透明
     WindowUtils.setHalfTransparent(Activity activity)
     //获取屏幕宽
     WindowUtils.getWidth(Activity context)
     //获取屏幕高
     WindowUtils.getHeight(Activity context)
     //隐藏软键盘
     WindowUtils.hideSoftInput(Context context, View view)
```

### 11、BasePopView

```
     //创建xml后，点击编译，填入需要绑定的视图
     //支持ViewBinding
     //支持LifecycleProvider
     //click监听已做快速点击处理
     //共有9种基类封装弹窗
     //BaseBottomPopView - 底部弹出弹窗
     //BaseBubbleHorizontalAttachPopupView - 水平弹出可依附气泡弹窗
     //BaseCenterPopView - 居中弹窗
     //BaseDrawerPopupView - 实现Drawer的弹窗
     //BaseFullScreenPopupView - 全屏弹窗
     //BaseHorizontalAttachPopView - 水平弹出可依附视图弹窗
     //BasePositionPopupView - 自定义方向弹窗
     //BaseVerticalAttachPopView - 垂直弹出可依附视图弹窗
     //BaseVerticalBubbleAttachPopupView - 垂直弹出可依附气泡弹窗
     //【注】：内部使用Fragment，需要设置isViewMode为true
     //继承示例
     public class TestPopView extends BaseCenterPopView<PopAddCardViewBinding>{

         public TestPopView(@NonNull Context context) {
            super(context);
         }
         
         @NonNull
         @Override
         protected PopAddCardViewBinding setBindView(View v) {
            return PopAddCardViewBinding.bind(v);
         }
         
         @Override
         protected void setLayoutId() {
             return R.layout.pop_addCard_view;
         }
         
         @Override
         protected boolean isDefaultBackground() {
            return false;
         }
        
         @Override
         protected void initView() {
       
         }

         @Override
         protected void initListener() {
       
         }
         
         @Override
         protected void click(View v) {
              
         }
      }
```

### 12、WordUtils

```
     //比较字符串
     WordUtils.contain(String input, String regex)
```

### 13、BaseAccessibilityService

```
public class MyService extends BaseAccessibilityService {

    @Override
    protected void onConnected() {
        //服务已开启
    }

    @Override
    protected void viewClickListener(AccessibilityEvent event) {
        //视图单击监听
        //可用属性：
           getEventType()-事件类型。
           getSource()-源信息（对于已注册的客户端）。 
           getClassName()-源的类名。 
           getPackageName()-源的包名。
           getEventTime()-事件时间。
           getText()-源的子树的文本。 
           isEnabled()-源是否可用。
           isPassword()-源是否是个密码。 
           isChecked()-源是否选中。
           getContentDescription()-源的内容描述。
           getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
           getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
           getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
            AdapterView 的子视图）。
           getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
            AdapterView 的子视图）。
    }

    @Override
    protected void viewLongClickListener(AccessibilityEvent event) {
        //视图长按监听
        //可用属性：
           getEventType()-事件类型。
           getSource()-源信息（对于已注册的客户端）。 
           getClassName()-源的类名。 
           getPackageName()-源的包名。
           getEventTime()-事件时间。
           getText()-源的子树的文本。 
           isEnabled()-源是否可用。
           isPassword()-源是否是个密码。 
           isChecked()-源是否选中。
           getContentDescription()-源的内容描述。
           getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
           getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
           getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
            AdapterView 的子视图）。
           getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
            AdapterView 的子视图）。
    }

    @Override
    protected void viewSelectListener(AccessibilityEvent event) {
          //选中视图监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             isPassword()-源是否是个密码。 
             isChecked()-源是否选中。
             getContentDescription()-源的内容描述。
             getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
             getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
             getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
             getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
    }

    @Override
    protected void viewFocusListener(AccessibilityEvent event) {
          //聚焦的视图
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             isPassword()-源是否是个密码。 
             isChecked()-源是否选中。
             getContentDescription()-源的内容描述。
             getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。 
             getScrollY()-使用像素表示源顶部边缘的偏移量(不包含AdapterView的子视图)。 
             getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
             getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
    } 

    @Override
    protected void etTextChangedListener(AccessibilityEvent event) {
          //编辑框文本改变监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             isPassword()-源是否是个密码。 
             isChecked()-源是否选中。
             getFromIndex()-文本改变起始索引。 
             getAddedCount()-被添加的字符数。
             getRemovedCount()-被移除的字符数。
             getBeforeText()-改变之前的源文本。
             getContentDescription()-源的内容描述。
    }

    @Override
    protected void etSelectedTextChangedListener(AccessibilityEvent event) {
          //编辑框选中文本改变监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             isPassword()-源是否是个密码。 
             isChecked()-源是否选中。
             getFromIndex()-文本改变起始索引。 
             getAddedCount()-被添加的字符数。
             getRemovedCount()-被移除的字符数。
             getBeforeText()-改变之前的源文本。
             getContentDescription()-源的内容描述。
    }

    @Override
    protected void viewScrollListener(AccessibilityEvent event) {
          //视图滚动监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             getContentDescription()-源的内容描述。
             getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。
             getScrollY()-使用像素表示源顶边缘的偏移量(不包含AdapterView的子视图)。 
             getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
               getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。 
             getItemCount()-源的总项目数（用于AdapterView 的子视图）
    }

    @Override
    protected void windowStatusChangedListener(AccessibilityEvent event) {
          //窗口状态改变监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
    }

    @Override
    protected void windowContentChangedListener(AccessibilityEvent event) {
          //窗口内容改变监听
          //可用属性：
             getEventType()-事件的类型。
             getContentChangeTypes()-内容改变的类型。 
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
    }

    @Override
    protected void windowChangedListener(AccessibilityEvent event) {
          //窗口内容改变监听
          //可用属性：
             getEventType()-事件类型。
             getEventTime()-事件时间。
    }

    @Override
    protected void notificationStatusChangedListener(AccessibilityEvent event) {
          //通知监听
          //可用属性：
             getEventType()-事件类型。
             getClassName()-源的类名。
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getParcelableData-发布的通知（Notification）。 
             getText-提供更多上下文的文本。
    }

    @Override
    protected void viewHoverEnterListener(AccessibilityEvent event) {
          //视图悬停进入监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             getContentDescription()-源的内容描述。
             getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。
             getScrollY()-使用像素表示源顶边缘的偏移量(不包含AdapterView的子视图)。 
             getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
             getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。 
             getItemCount()-源的总项目数（用于 AdapterView 的子视图）。
    }

    @Override
    protected void viewHoverExitListener(AccessibilityEvent event) {
          //视图悬停退出监听
          //可用属性：
             getEventType()-事件类型。
             getSource()-源信息（对于已注册的客户端）。 
             getClassName()-源的类名。 
             getPackageName()-源的包名。
             getEventTime()-事件时间。
             getText()-源的子树的文本。 
             isEnabled()-源是否可用。
             getContentDescription()-源的内容描述。
             getScrollX()-使用像素表示源左边缘的偏移量(不包含AdapterView的子视图)。
             getScrollY()-使用像素表示源顶边缘的偏移量(不包含AdapterView的子视图)。 
             getFromIndex()-源第一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。
             getToIndex()-源最后一个可见项的从零开始的索引，包含（用于
              AdapterView 的子视图）。 
             getItemCount()-源的总项目数（用于 AdapterView 的子视图）。
    }

    @Override
    protected void touchInteractionStartedListener(AccessibilityEvent event) {
          //触摸交互开始监听
          //可用属性：
             getEventType()-事件的类型。
    }

    @Override
    protected void touchInteractionEndedListener(AccessibilityEvent event) {
          //触摸交互结束出监听
          //可用属性：
             getEventType()-事件的类型。
    }

    @Override
    protected void touchExploredStartedListener(AccessibilityEvent event) {
          //触摸浏览手势开始监听
          //可用属性：
             getEventType()-事件的类型。
    }

    @Override
    protected void touchExploredEndedListener(AccessibilityEvent event) {
          //触摸浏览手势结束监听
          //可用属性：
             getEventType()-事件的类型。
    }

    @Override
    protected void gestureDetectedStartListener(AccessibilityEvent event) {
          //触摸手势检测开始监听
          //可用属性：
             getEventType()-事件的类型。
    }

    @Override
    protected void gestureDetectedEndListener(AccessibilityEvent event) {
          //触摸手势检测结束监听
          //可用属性：
             getEventType()-事件的类型。
    }
}
```

MF文件中，注册服务，可使用库中默认的配置文件，如下示例

```
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

方法列表

```
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

### 14、ToastUtils

```
     //在Application中初始化土司
     //参数2是多次点击依然显示
     ToastUtils.INSTANCE.initToast(Context mContext);
     ToastUtils.INSTANCE.initToast(Context mContext,boolean isMoreShow);
     //成功土司
     ToastUtils.INSTANCE.onSuccessShowToast(String message);
     //成功显示xml文字
     ToastUtils.INSTANCE.onSuccessShowToast(int messageID);
     //成功显示自定义icon
     ToastUtils.INSTANCE.onSuccessShowToast(String message, int iconID);
     //成功显示xml自定义icon
     ToastUtils.INSTANCE.onSuccessShowToast(int messageID, int iconID);
     //错误土司
     ToastUtils.INSTANCE.onErrorShowToast(String message);
     //错误显示xml文字
     ToastUtils.INSTANCE.onErrorShowToast(int messageID);
     //错误显示自定义icon
     ToastUtils.INSTANCE.onErrorShowToast(String message, int iconID);
     //错误显示xml自定义icon
     ToastUtils.INSTANCE.onErrorShowToast(int messageID, int iconID);
     //默认土司
     ToastUtils.INSTANCE.onDefaultShowToast(String message);
     //默认显示xml文字
     ToastUtilsv.onDefaultShowToast(int messageID);
     //默认显示自定义icon
     ToastUtils.INSTANCE.onDefaultShowToast(String message, int iconID);
     //默认显示xml自定义icon
     ToastUtils.INSTANCE.onDefaultShowToast(int messageID, int iconID);
     //默认带图标土司
     ToastUtilsv.onDefaultWithoutIconShowToast(String message);
     //默认带图标显示xml文字
     ToastUtils.INSTANCE.onDefaultWithoutIconShowToast(int messageID);
     //默认带图标显示自定义icon
     ToastUtils.INSTANCE.onDefaultWithoutIconShowToast(String message, int iconID);
     //默认带图标显示xml自定义icon
     ToastUtils.INSTANCE.onDefaultWithoutIconShowToast(int messageID, int iconID);
     //警告土司
     ToastUtils.INSTANCE.onWarnShowToast(String message);
     //警告显示xml文字
     ToastUtils.INSTANCE.onWarnShowToast(int messageID);
     //警告显示自定义icon
     ToastUtils.INSTANCE.onWarnShowToast(String message, int iconID);
     //警告显示xml自定义icon
     ToastUtils.INSTANCE.onWarnShowToast(int messageID, int iconID);
     //信息土司
     ToastUtils.INSTANCE.onInfoShowToast(String message);
     //信息显示xml文字
     ToastUtils.INSTANCE.onInfoShowToast(int messageID);
     //信息显示自定义icon
     ToastUtils.INSTANCE.onInfoShowToast(String message, int iconID);
     //信息显示xml自定义icon
     ToastUtils.INSTANCE.onInfoShowToast(int messageID, int iconID);
```

### 15、LogUtils

```
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

### 16、BaseObserver/BaseLifeObserver

【注】：BaseLifeObserver请配合lifecycle使用

```
     //无加载框
     new BaseObserver()
     //自定义LoadingPopView/是否智能关闭
     new BaseObserver(LoadingPopupView loadingPopupView, boolean isSmartDismiss)
     //配置LoadingPopView
     new BaseObserver(Activity activity, LoadingConfig config)
```

### 17、BaseCompletableObserver/BaseLifeCompletableObserver

【注】：BaseCompletableObserver请配合lifecycle使用

```
     //无加载框
     new BaseCompletableObserver()
     //自定义LoadingPopView/是否智能关闭
     new BaseCompletableObserver(LoadingPopupView loadingPopupView, boolean isSmartDismiss)
     //配置LoadingPopView
     new BaseCompletableObserver(Activity activity, LoadingConfig config)
```

### 18、BaseMaybeObserver/BaseLifeMaybeObserver

【注】：BaseLifeMaybeObserve请配合lifecycle使用

```
     //无加载框
     new BaseMaybeObserver()
     //自定义LoadingPopView/是否智能关闭
     new BaseMaybeObserver(LoadingPopupView loadingPopupView, boolean isSmartDismiss)
     //配置LoadingPopView
     new BaseMaybeObserver(Activity activity, LoadingConfig config)
```

### 19、BaseSingleObserver/BaseLifeSingleObserver

【注】：BaseLifeSingleObserver请配合lifecycle使用

```
     //无加载框
     new BaseSingleObserver()
     //自定义LoadingPopView/是否智能关闭
     new BaseSingleObserver(LoadingPopupView loadingPopupView, boolean isSmartDismiss)
     //配置LoadingPopView
     new BaseSingleObserver(Activity activity, LoadingConfig config)
```

### 20、BaseSubscriber/BaseLifeSubscriber

【注】：BaseLifeSubscriber请配合lifecycle使用

```
     //无加载框
     new BaseSubscriber()
     //自定义LoadingPopView/是否智能关闭
     new BaseSubscriber(LoadingPopupView loadingPopupView, boolean isSmartDismiss)
     //配置LoadingPopView
     new BaseSubscriber(Activity activity, LoadingConfig config)
```

### 21、CrashConfig

```
      CrashConfig.Builder
                .create()
                //当应用程序处于后台时崩溃，也会启动错误页面
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                //当应用程序处于后台崩溃时显示默认系统错误
                .backgroundMode(CrashConfig.BACKGROUND_MODE_CRASH)
                //当应用程序处于后台时崩溃，默默地关闭程序
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SILENT)
                //false表示对崩溃的拦截阻止。用它来禁用customactivityoncrash
                .enabled(true)
                //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪,针对框架自带程序崩溃后显示的页面有用
                .showErrorDetails(true)
                //是否可以重启页面,针对框架自带程序崩溃后显示的页面有用
                .showRestartButton(true)
                //崩溃页面显示的图标
                .errorDrawable(R.mipmap.ic_launcher)
                .logErrorOnRestart(true)
                //错误页面中显示错误详细信息
                .trackActivities(true)
                //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中
                .minTimeBetweenCrashesMs(2000)
                //重新启动后的页面
                .restartActivity(LoginActivity.class)
                .apply();
```

### 22、MmKvUtils

```
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
      /放long
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
      /拿String
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

### 23、RouterUtils

```
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

***

### 24、FragmentUtils

```
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

### 25、ViewAnimatorUtils

```
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

### 26、ScreenRecordUtils

```
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
            screenRecordUtils = new ScreenRecordUtils(recordConfig, new ScreenRecordUtils.OnVideoRecordListener() {
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

### 27、BaseApplication

```
      //支持应用前后台判断
      //已接入MyActivityManager，可直接使用
      //已接入MyApplicationManager，可直接使用
      public class MyApplication extends BaseApplication {
      
          @Override
          protected void init() {
             //初始化
          }
          
      }
      
      //判断应用在前/后台
      //true为前台
      //false为后台
      MyApplication.isAppForeground()
```

### 28、ActivityManager

```
      //使用BaseApplicatio可直接使用
      //单独使用，需要在ActivityLifecycleCallbacks回调中的onActivityResumed设置
      //支持弱引用，获取到的Activity已做弱引用处理
      //设置当前正在显示的Activity
      MyActivityManager.INSTANCE.setCurrentActivity(Activity activity);
      //获取当前正在显示的Activity
      MyActivityManager.INSTANCE.getCurrentActivity();
```

### 29、ContextManager

```
      //使用BaseApplicatio可直接使用
      //单独使用，需要在Application的onCreate设置
      //支持弱引用，获取到的Context已做弱引用处理
      //设置当前Context
      MyApplicationManager.INSTANCE.setCurrentContext(Context context);
      //获取当前Cotext
      MyApplicationManager.INSTANCE.getCurrentContext();
```
