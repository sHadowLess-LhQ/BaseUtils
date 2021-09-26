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

2.  BaseFragment：直接继承

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
                    public void getWindowView(View view) {
                        //回调自定义View对象
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

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)