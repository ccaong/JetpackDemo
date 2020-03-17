# WanAndroid Jetpack+MVVM

## 关键词
Java，MVVM，JetPack

## 前言
先啰嗦几句废话，大家都知道WanAndroid的项目真的太多了，其中好的项目也太多了，我为啥还要再写一个呢？  

其实写这个项目是个偶然的事，看项目名称就能知道，这个项目还叫JetPackDemo。  

写成WanAndroid是因为我在学习JetPack系统组件的过程中，写了个demo。  

在写的过程中，我逐渐发现了JetPack的魅力之一*组件互相之间的配合使用*，各个系统组件之间互相配合才能发挥它最大的效果。于是我在写Demo的中途临时决定把它写成一个完整的项目。  

至于为什么选择了WanAndroid并且投稿到WanAndroid项目中，因为在我学习JetPack的过程中我发现绝大多数的demo都是采用kotlin写的。

后来我发现WanAndroid的项目也是如此，之前大部分的项目都是基于Java+MVP+RxJava+Retrofit的，现在的大部分项目都是基于Kotlin+MVVM+JetPack的。
这就对我这种想要使用MVVM但是kotlin还没有掌握好的人不太友好。于是这个抛砖引玉的项目就诞生了。如果你想要学习MVVM+JetPack,但是kotlin还没掌握，那么你可以参考本项目来开始你的JetPack学习之旅。  

其实我更希望的是希望各位大神看到这个写的可能并不好的项目之后发出一声感叹：写成这样还好意思发出来，不害臊吗？然后再去写一个真正优秀的项目让大家去学习参考，如果真能这样，那写这个项目真的就太值了。  

对了，大神门写完之后可以@一下我，我好去学习一波，最后，kotlin还是大势所趋啊，还是要学的，不过这个项目不会改成kotlin，后续的更新升级依旧会是用java来写的


## 项目介绍
WanAndroid 使用Java语言编写。采用了MVVM+Jetpack中的系统组件。
JetPack系统组件中，使用了Navigation，ViewModel，DataBinding,LifeCycle,LiveData。可以参考本项目学习JetPack系统组件的用法。
后续将继续完善，并将使用paging，room，workManage等系统组件。


## 项目界面

![闪屏页](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_splash.webp)
![登录](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_login.png)
![主页](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_home.png)
![侧边栏](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_navagation.png)
![公众号](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_wechat.png)
![体系](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_sys.png)
![导航](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_nav.png)
![项目](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_project.png)
![todo](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_todo.png)
![收藏](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_collect.png)
![我的](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_mine.png)
![关于](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_about.png)
![更新](https://github.com/ccaong/JetpackDemo/blob/master/image/wanandroid_update.png)


## 功能介绍
好像没啥特别需要介绍的，看代码就好了。

## 更新计划 
- [ ] 添加room数据库，实现部分数据的离线存储功能；
- [ ] 使用paging，完善列表分页功能；
- [ ] 实现WanAndroid提供的所有API；
- [ ] 完成系统设置功能；
- [ ] 优化程序界面，完善app动画效果
- [ ] 提供更换主题功能；


## 用到的开源库

- [JetPack](https://developer.android.google.cn/jetpack)
Jetpack 是一套库、工具和指南，可帮助开发者更轻松地编写优质应用。

- [RxJava](https://github.com/ReactiveX/RxJava)
RxJava是Reactive Extensions的Java VM实现：该库用于通过使用可观察的序列来组成异步和基于事件的程序。

- [Retrofit](https://github.com/square/retrofit)
Type-safe HTTP client for Android and Java by Square, Inc.

- [Glide](https://github.com/bumptech/glide)
Glide是一个快速高效的Android图片加载库，注重于平滑的滚动。

- [glide-transformations](https://github.com/wasabeef/glide-transformations)
一个Android转换库，为Glide提供了各种图像转换。

- [flexbox](https://github.com/google/flexbox-layout)
FlexboxLayout是一个库项目，将CSS Flexible Box Layout Module的类似功能引入了Android。

- [Hawk](https://github.com/orhanobut/hawk)
适用于Android的安全，简单的键值存储库

- [QMUI](https://qmuiteam.com/android)
QMUI Android 的设计目的是用于辅助快速搭建一个具备基本设计还原效果的 Android 项目

- [AgentWeb](https://github.com/Justson/AgentWeb)
gentWeb 是一个基于的 Android WebView ，极度容易使用以及功能强大的库，提供了 Android WebView 一系列的问题解决方案 ，并且轻量和极度灵活

- [smartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
SmartRefreshLayout以打造一个强大，稳定，成熟的下拉刷新框架为目标，并集成各种的炫酷、多样、实用、美观的Header和Footer

- [phoenix](https://github.com/scwang90/SmartRefreshLayout)
Android平台上拍照/录像，图片/视频选择，编辑和压缩的一站式解决方案。

- [VerticalTabLayout](https://github.com/qstumn/VerticalTabLayout)
垂直竖向的Android TabLayout

- [MZBannerView](https://github.com/pinguo-zhouwei/MZBannerView)
图片轮播控件,支持多种模式切换：普通ViewPager使用，普通Banner使用

- [PopsTabView](https://github.com/ccj659/PopsTabView)
PopsTabView是个filter容器,他可以自动,快速,构建不同筛选样式,自由组合成一组tab


## 感谢

*感谢鸿洋大神提供的玩Android API*
