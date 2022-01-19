# BaseUtils

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
              maven { url 'https://www.jitpack.io' }
         }
     }
     
     //新AndroidStudio版本
     //settings.gradle
     dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
            ...
             maven { url 'https://jitpack.io' }
             maven { url 'https://www.jitpack.io' }
          }
      }
```

```
     //主项目的build.gradle中加入
     //新AndroidStudio版本
     android {
      ...
       buildFeatures {
         viewBinding = true
          }
     }
     
     //主项目的build.gradle中加入
     //旧AndroidStudio版本
     android {
      ...
       viewBinding {
         enable = true
          }
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

```
     dependencies {
            //主模块
            implementation 'com.gitee.shadowless_lhq:base-utils:Tag'
            implementation 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.5@aar'
            implementation 'io.reactivex.rxjava2:rxjava:2.2.20'
            implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
            //【注：】使用NetUtils，请额外添加以下依赖：
            //NetUtils
                implementation 'com.squareup.okhttp3:okhttp:3.14.9'
                implementation 'com.google.code.gson:gson:2.8.6'
                implementation 'com.squareup.retrofit2:retrofit:2.6.1'
                implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
                implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
                implementation 'org.projectlombok:lombok:1.18.8'
                annotationProcessor 'org.projectlombok:lombok:1.18.8'
            //【注：】使用BaseXPop，请额外添加以下依赖：
            //BaseXPop
                implementation 'com.github.li-xiaojun:XPopup:2.5.15'
                implementation 'com.google.android.material:material:1.3.0-alpha01'
            //【注：】使用CustomDialog、CustomWindow，请额外添加以下依赖：
                implementation 'org.projectlombok:lombok:1.18.8'
                annotationProcessor 'org.projectlombok:lombok:1.18.8'
    }
```

[![](https://jitpack.io/v/com.gitee.shadowless_lhq/base-utils.svg)](https://jitpack.io/#com.gitee.shadowless_lhq/base-utils)

#### 使用说明

### 1、BaseActivity：直接继承

```
//创建xml后，点击编译，填入需要绑定的视图
public class MainActivity extends BaseActivity<ActivityMainBinding> {

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
    protected void initData(@NonNull Map<String, Object> mData, @NonNull InitDataCallBack initDataCallBack) {
       //初始化数据
       【注】：若在initData()中需要同时从多个接口获取数据，可以使用RxJava的zip操作符，将数据进行集中处理后，再通过InitDataCallBack回调
       【注】：若遇到mData表未清空，请手动调用mData.clear()清空。
       if(isOrientation){
        //竖屏
       }else{
        //横屏
       }
       mData.put("data","成功");
       initDataCallBack.success(mData);
    }

    @Override
    protected void initView(@NonNull Map<String, Object> data) {
       //初始化界面控件
       getBindView().test.setText(map.get("data"));
    }
    
    @Override
    protected int theme() {
       //动态设置Activity主题
       //0为不设置
       return 0;
    }
}
```

### 2、BaseFragment：直接继承

```
//创建xml后，点击编译，填入需要绑定的视图
public class MainFragment extends BaseFragment<FragmentMainBinding> {
    
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
    protected void initData(@NonNull Map<String, Object> mData, @NonNull InitDataCallBack initDataCallBack) {
       //初始化数据
       【注】：若在initData()中需要同时从多个接口获取数据，可以使用RxJava的zip操作符，将数据进行集中处理后，再通过InitDataCallBack回调
       【注】：若遇到mData表未清空，请手动调用mData.clear()清空。
       if(isOrientation){
        //竖屏
       }else{
        //横屏
       }
       mData.put("data","成功");
       initDataCallBack.success(mData);
    }

    @Override
    protected void initView(@NonNull Map<String, Object> map) {
       //初始化界面控件
       getBindView().test.setText(map.get("data"));
    }
}
```

### 3、CustomDialog：调用示例

```
     CustomDialog
                .builder()
                .context()        //传入上下文
                .dialogView()     //传入自定义View，但和layout两者选其一调用
                .layout()         //传入xml，但和dialogView两者选其一调用
                .isClearLayer()   //是否去除蒙层，默认不去除
                .isSetAnim()      //是否添加动画，和anim()方法同步使用，不可单一使用，默认不设置
                .anim()           //设置动画xml，和isSetAnim()方法同步使用，不可单一使用
                .isSystemDialog() //是否设置为系统级提示框，默认不设置
                .isTitle()        //是否去除标题栏，默认不去除
                .isWindowSize()   //是否设置提示框大小，和.windowHeight() .windowWidth()方法同步使用
                .windowHeight()   //设置提示框高度，和.isWindowSize()同步使用，不可单独使用
                .windowWidth()    //设置提示框宽度，和.isWindowSize()同步使用，不可单独使用
                .cancel()         //是否外部触碰取消提示框，默认不取消
                .location()       //设置提示框显示位置，传入CustomDialog.location枚举，具体枚举进入源码查看
                .build()
                .show(new CustomDialog.InitViewListener() {
                    @Override
                    public void getDialogView(View view, Dialog dialog) {
                                  //回调自定义View和提示框对象
                    }
                //参数1：自定义主题
                .show(int stype, new CustomDialog.InitViewListener() {
                    @Override
                    public void getDialogView(View view, Dialog dialog) {
                                  //回调自定义View和提示框对象
                    }
                });
```

### 4、CustomWindow：调用示例

```
     CustomWindow
                .builder()
                .context()     //传入上下文
                .view()        //传入自定义View，和.layout()两者选其一调用
                .layout()      //传入xml，和.view()两者选其一调用
                .isAnimation() //是否设置动画，默认不设置，和.anim()同步使用
                .anim()        //设置窗口动画，和.isAnimation()同步使用
                .isSystem()    //是否设置为系统级窗口，默认不设置
                .width()       //设置窗口宽度
                .height()      //设置窗口高度
                .windowFlag()  //设置窗口标识
                .location()    //设置窗口显示位置，传入Gravity的静态位置常量
                .build()
                .show(new CustomWindow.InitWindowListener() {
                    @Override
                    public void getWindowView(View view, WindowManager windowManager) {
                               //回调自定义View对象和WindowManager，用于移除自定义View
                    }
                });
```

### 5、PreferencesUtils：方法说明

```
     //同步存入字符串数据
     PreferencesUtils.putStringNow(Context context, String key, String value)
     //异步存入字符串数据
     PreferencesUtils.putString(Context context, String key, String value)
     //同步追加字符串集合数据
     PreferencesUtils.addStringSetNow(Context context, String key, String value)
     //异步追加字符串集合数据
     PreferencesUtils.addStringSet(Context context, String key, String value)
     //同步删除指定字符串集合数据
     PreferencesUtils.removeStringSetNow(Context context, String key, String value)
     //异步删除指定字符串集合数据
     PreferencesUtils.removeStringSet(Context context, String key, String value)
     //同步存入整型数据
     PreferencesUtils.putIntNow(Context context, String key, int value)
     //异步存入整型数据
     PreferencesUtils.putInt(Context context, String key, int value)
     //同步存入长整型数据
     PreferencesUtils.putLongNow(Context context, String key, long value)
     //异步存入长整型数据
     PreferencesUtils.putLong(Context context, String key, long value)
     //同步存入单精度浮点数据
     PreferencesUtils.putFloatNow(Context context, String key, float value)
     //异步存入单精度浮点数据
     PreferencesUtils.putFloat(Context context, String key, float value)
     //同步存入布尔类型数据
     PreferencesUtils.putBooleanNow(Context context, String key, boolean value)
     //异步存入布尔类型数据
     PreferencesUtils.putBoolean(Context context, String key, boolean value)
     //获取字符串数据
     PreferencesUtils.getString(Context context, String key)
     PreferencesUtils.getString(Context context, String key, String defaultValue)
     //获取字符串集合
     PreferencesUtils.getStringSet(Context context, String key)
     PreferencesUtils.getStringSet(Context context, String key, Set<String> defaultValue)
     //获取整型数据
     PreferencesUtils.getInt(Context context, String key)
     PreferencesUtils.getInt(Context context, String key, int defaultValue)
     //获取长整型数据
     PreferencesUtils.getLong(Context context, String key)
     PreferencesUtils.getLong(Context context, String key, long defaultValue)
     //获取单精度浮点数据
     PreferencesUtils.getFloat(Context context, String key)
     PreferencesUtils.getFloat(Context context, String key, float defaultValue)
     //获取布尔类型数据
     PreferencesUtils.getBoolean(Context context, String key)
     PreferencesUtils.getBoolean(Context context, String key, boolean defaultValue)
     //同步清空SP
     PreferencesUtils.clearNow(Context context)
     //异步清空SP
     PreferencesUtils.clear(Context context)
     //同步删除指定数据
     PreferencesUtils.removeNow(Context context, String key)
     //异步删除指定数据
     PreferencesUtils.remove(Context context, String key)
```

### 6、RxUtils：调用示例

```
     RxUtils
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

### 7、NetUtils：调用示例

```
      NetUtils
                .builder()
                .baseUrl()       //设置根地址
                .okHttpClient()  //设置自定义okhttp，不设置有默认
                .timeOut()       //设置超时时间，不设置默认10秒
                .timeOutUnit()   //设置超时时间单位，不设置默认单位秒
                .build()
                .initRetrofit(new NetUtils.InitInterface() {
                    @Override
                    public void createInterface(Retrofit retrofit) {
                                 //可在回调内实例化接口
                    }
                });
```

### 8、LocationUtils：调用示例

```
      //参数1：上下文
      //参数2：结果回调
      LocationUtils.getInstance(context).getLocation(new LocationUtils.AddressCallback() {
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

### 9、BaseModelUtils：调用示例

```
      //参数1：状态码 - int
      //参数2：状态消息 - String 
      //参数3：数据 - T
      BaseModelUtils.getMod(参数1，参数二，参数三);
```

### 10、ApplicationUtils：方法说明

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

### 11、DeviceUtils

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

### 12、FileUtils：方法说明

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
```

### 13、WindowUtils：方法说明

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

### 14、BasePopView：直接继承

```
     //依附输入法的PopView
     - BaseBottomPopView
     //水平气泡依附的PopView
     - BaseBubbleHorizontalAttachPopupView
     //垂直气泡依附的PopView
     - BaseVerticalBubbleAttachPopupView
     //居中弹出的PopView
     - BaseCenterPopView
     //侧滑拉出的PopView
     - BaseDrawerPopupView
     //全屏的PopView
     - BaseFullScreenPopupView
     //水平依附的PopView
     - BaseHorizontalAttachPopView
     //垂直依附的PopView
     - BaseVerticalAttachPopView
     //自定位的PopView
     - BasePositionPopupView
```

### 15、WordUtils：方法说明

```
     //比较字符串
     WordUtils.contain(String input, String regex)
```

### 16、SqlUtils：方法说明

```
     //初始化数据库
     SqlUtils.initDateBase(Context context, String dbName)
     //写入字符串数据
     SqlUtils.writeString(String key, String value)
     //写入字节数组数据
     SqlUtils.writeByteList(String key, byte[] value)
     //获取字符串数据
     SqlUtils.getString(String key)
     //获取字节数组数据
     SqlUtils.getByteList(String key)
     //标记需要删除的数据
     SqlUtils.deleteAndSign(String key)
     //直接删除数据
     SqlUtils.removeData(String key)
     //删除标记的数据
     SqlUtils.clearSignData()
     //关闭数据库
     SqlUtils.closeDb()
```

### 17、BaseAccessibilityService：直接继承

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
     HelpService.getService().findViewByTextUnClickable(String text)
     //延迟查找指定字符的不可点击视图节点
     HelpService.getService().findViewByTextUnClickable(String text, int milliSecond)
     //查找指定字符的可点击视图节点
     HelpService.getService().findViewByTextClickable(String text)
     //延迟查找指定字符的可点击视图节点
     HelpService.getService().findViewByTextClickable(String text, int milliSecond)
     //查找指定视图id的不可点击视图节点
     //id规范：包名:id/视图id
     HelpService.getService().findViewByIdUnClickable(String id)
     //延迟查找指定视图id的不可点击视图节点
     HelpService.getService().findViewByIdUnClickable(String id, int milliSecond)
     //查找指定视图id的可点击视图节点
     //id规范：包名:id/视图id
     HelpService.getService().findViewByClickable(String id)
     //延迟查找指定视图id的可点击视图节点
     HelpService.getService().findViewByIdClickable(String id, int milliSecond)
     //单击视图节点
     HelpService.getService().performViewClick(AccessibilityNodeInfo nodeInfo)
     //延迟单击视图节点
     HelpService.getService().performViewClick(AccessibilityNodeInfo nodeInfo, int milliSecond)
     //长按视图节点
     HelpService.getService().performViewLongClick(AccessibilityNodeInfo nodeInfo)
     //延迟长按视图节点
     HelpService.getService().performViewLongClick(AccessibilityNodeInfo nodeInfo, int milliSecond)
     //执行单击返回键
     HelpService.getService().performBackClick()
     //延迟单击返回键
     HelpService.getService().performBackClick(int milliSecond)
     //执行分屏
     HelpService.getService().performSplitScreen()
     //延迟执行分屏
     HelpService.getService().performSplitScreen(int milliSecond)
     //执行截图
     HelpService.getService().performTakeScreenShot()
     //延迟执行截图
     HelpService.getService().performTakeScreenShot(int milliSecond)
     //执行单击最近任务
     HelpService.getService().performRecent()
     //延迟执行单击最近任务
     HelpService.getService().performRecent(int milliSecond)
     //执行下拉二级状态栏
     HelpService.getService().performChildStatueBar()
     //延迟执行下拉二级状态栏
     HelpService.getService().performChildStatueBar(int milliSecond)
     //执行弹出电源管理框
     HelpService.getService().performPowerDialog()
     //延迟执行弹出电源管理框
     HelpService.getService().performPowerDialog(int milliSecond)
     //执行下拉状态栏
     HelpService.getService().performStatueBar()
     //延迟执行下拉状态栏
     HelpService.getService().performStatueBar(int milliSecond)
     //执行单击home键
     HelpService.getService().performBackHomClicke()
     //延迟执行单击home键
     HelpService.getService().performBackHomeClick(int milliSecond)
     //执行锁屏
     HelpService.getService().performLockScreen()
     //延迟执行锁屏
     HelpService.getService().performLockScreen(int milliSecond)
     //点击指定字符的视图
     HelpService.getService().clickViewByText(String text)
     //延迟点击指定字符的视图
     HelpService.getService().clickViewByText(String text, int milliSecond)
     //长按指定字符的视图
     HelpService.getService().longClickViewByText(String text)
     //延迟长按指定字符的视图
     HelpService.getService().longClickViewByText(String text, int milliSecond)
     //点击指定id的视图
     //id规范：包名:id/视图id
     HelpService.getService().clickViewById(String id)
     //延迟点击指定id的视图
     //id规范：包名:id/视图id
     HelpService.getService().clickViewById(String id, int milliSecond)
     //长按指定id的视图
     //id规范：包名:id/视图id
     HelpService.getService().longClickViewById(String id)
     //延迟长按指定id的视图
     //id规范：包名:id/视图id
     HelpService.getService().longClickViewById(String id, int milliSecond)
     //输入指定字符到指定字符的视图
     HelpService.getService().inputTextToEtView(String view, String text)
     //延迟输入指定字符到指定字符的视图
     HelpService.getService().inputTextToEtView(String view, String text, int milliSecond)
     //输入指定id到指定id的视图
     //id规范：包名:id/视图id
     HelpService.getService().inputIdToEtView(String view, String text)
     //延迟输入指定id到指定id的视图
     //id规范：包名:id/视图id
     HelpService.getService().inputIdToEtView(String view, String text, int milliSecond)
     //单击指定坐标
     HelpService.getService().dispatchGestureClick(int x, int y)
     //延迟单击指定坐标
     HelpService.getService().dispatchGestureClick(int x, int y, int milliSecond)
     //长按指定坐标
     HelpService.getService().dispatchGestureLongClick(int x, int y)
     //延迟长按指定坐标
     HelpService.getService().dispatchGestureLongClick(int x, int y, int milliSecond)
     //指定坐标连续手势滑动
     //参数1：x坐标
     //参数2：y坐标
     //参数3：滑动持续时间
     //参数4：每次滑动的间隔时间
     //坐标数量需要对应，否则不会执行方法
     HelpService.getService().continueSwipe(int[] x, int[] y, int swipeDuration, int stepDuration)
     //判断辅助服务是否开启
     //参数2：辅助服务类
     HelpService.isAccessibilitySettingsOn(Context mContext, Class<?> cls)
```

### 18、ToastUtils：方法说明

```

     //普通土司
     ToastUtils.normal(@NonNull Context context, @NonNull String message)
     //普通土司带图标
     ToastUtils.normal(@NonNull Context context, @NonNull String message, Drawable icon)
     //普通土司带显示时长
     ToastUtils.normal(@NonNull Context context, @NonNull String message, int duration)
     //普通土司带图标带显示时长
     ToastUtils.normal(@NonNull Context context, @NonNull String message, int duration, Drawable icon)
     //获取一个自定义土司对象
     ToastUtils.normal(@NonNull Context context, @NonNull String message, int duration, Drawable icon, boolean withIcon)
     //警告土司
     ToastUtils.warning(@NonNull Context context, @NonNull String message)
     //警告土司带时长
     ToastUtils.warning(@NonNull Context context, @NonNull String message, int duration) 
     //警告土司带时长是否带图标
     ToastUtils.warning(@NonNull Context context, @NonNull String message, int duration, boolean withIcon)
     //信息土司
     ToastUtils.info(@NonNull Context context, @NonNull String message)
     //信息土司带时长
     ToastUtils.info(@NonNull Context context, @NonNull String message, int duration)
     //信息土司带时长是否带图标
     ToastUtils.info(@NonNull Context context, @NonNull String message, int duration, boolean withIcon)
     //成功土司
     ToastUtils.success(@NonNull Context context, @NonNull String message)
     //成功土司带时长
     ToastUtils.success(@NonNull Context context, @NonNull String message, int duration)
     //成功土司带时长是否带图标
     ToastUtils.success(@NonNull Context context, @NonNull String message, int duration, boolean withIcon)
     //错误土司
     ToastUtils.error(@NonNull Context context, @NonNull String message)
     //错误土司带时长
     ToastUtils.error(@NonNull Context context, @NonNull String message, int duration)
     //错误土司带时长是否带图标
     ToastUtils.error(@NonNull Context context, @NonNull String message, int duration, boolean withIcon)
     //自定义土司
     ToastUtils.custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, int duration, boolean withIcon)
     //自定义土司
     ToastUtils.custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint)
     //自定义土司
     ToastUtils.custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint)
     //获取图片资源
     ToastUtils.getDrawable(@NonNull Context context, @DrawableRes int id)
     //获取.9位图
     ToastUtils.tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor)
```

#### 特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5. Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
