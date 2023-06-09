# 🎈LWord

![pass](https://camo.githubusercontent.com/0fc669f83d8e7dbdc505f25d885b1ab89b1fbd2b7352e0c8d07f72455e470109/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6275696c64696e672d706173732d677265656e) ![pass](https://camo.githubusercontent.com/526b2dc161c12115d53634671fbfae732820586f4f360fcdc9f085fd3df7bc39/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f636865636b732d706173732d677265656e) ![coverage](https://img.shields.io/badge/Coverage-91%25-green) ![lincense](https://img.shields.io/badge/license-MIT-brightgreen)

摸鱼单词本

## ✨功能

- 背单词（单词跳转时隐藏释义）
- 收藏单词（实时渲染到收藏列表）
- 可自行配置单词数据（excel)
- 自动数据持久化（本地文件IO，速度快）

![uTools1685939165333](http://typora.fengxiangrui.top/1685939170.png)

## 🎉下载与安装

- 已经将win32版本发布到release : https://github.com/lluckyboi/LWord/releases
- 下载源码，自行打包编译



## 🛠设计

#### 类图

![uTools_1686274944058](http://typora.fengxiangrui.top/1686274950.png)

#### 时序图

![image-20230609090023554](http://typora.fengxiangrui.top/1686272424.png)

#### 代码结构

```powershell
LWord
├─src
│  ├─main		
│  │  ├─java	
│  │  │  └─Data.java 		#导入excel数据的类
│  │  │  └─main				#主类
│  │  │  └─Word     		#单词类
│  │  │  └─WordBook			#单词本类
│  │  │  └─WordTool 		#单词工具类，用于对单词类进行操作
│  │  │  └─YamlReader       #数据读写类
│  │  └─resources			#存放资源文件
│  └─test
│      └─java			   #单元测试代码
```

#### 设计思路

##### 数据管理

- 通过excel进行数据批量导入，yaml文件作为本地缓存
- 支持自定义数据源，但是第一行单元格字段需依次为`序号`、`单词`、`音标`、`释义`
- 考虑到应用场景是本地小工具，因此选用本地文件IO而不是数据库作为存储系统，这样做有几个优点：
  - 由于数据存储在内存中，所以读写速度很快
  - 无需配置数据库
  - 无需网络连接

##### 逻辑实现

- 单词推送

  - 每次从数据源中取出10个单词形成推荐列表

  - 推荐列表中包含新单词与忘记了的旧单词

  - 忘记的单词会在一段时间后加入到推荐列表中再次推送

- 界面
  - 界面一次展示一个词和它的音标
  - 点击`认识`按钮会展示下一个单词
  - 点击`忘记`按钮会展示下一个单词，并且将该词添加到忘记列表，一段时间后加入推荐列表
  - 点击`查看释义`按钮会显示该词释义
  - 点击`收藏`按钮会将词语添加到收藏列表，然后更新收藏按钮
  - 点击`查看收藏`按钮会弹出收藏窗口，展示所有已经收藏的词汇，并且会在收藏列表变动时刷新
- 数据
  - 启动时导入excel的数据，存储在Object类型的二维数组中
  - 启动时构造YamlReader类，读入conf.yaml文件，作为缓存存储在类中
  - 关闭窗口时会先将YamlReader的数据写回文件中



## 🎯TODO

- [ ] 完善的日志系统
- [ ] 优化UI（比如高亮已收藏的词汇）
- [ ] 更全面的单元测试
- [ ] 优化错误处理