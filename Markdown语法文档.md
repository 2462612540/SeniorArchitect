# Markdown的基本语法

# 摘要
Markdown是一种可以使用普通文本编辑器编写的标记语言，通过简单的标记语法，它可以使普通文本内容具有一定的格式。
，作为一种轻量级的标记语言，能在非常短的时间内掌握。而且不仅可以用于写文档，还可以写博客、写简书、做一些随手笔记。
Markdown文件以.md结尾，可以导出为html和PDF（需要使用的工具支持）。它是一种语法（个人认为是简化版的html），
但是和html不同的是，各种解析器对其会有不同的表现。比如我的IDEA Markdown插件会把分割线显示成一条细灰线，
Cmd Markdown则是显示一条虚线。所以建议保持使用一种Markdown工具和尽量使用标准语法。

# 一、Markdown基本语法

## 1.1  标题
> # 一级标题
> ## 二级标题
> ### 三级标题
> #### 四级标题
> ##### 五级标题
> ###### 最小只有六级标题

## 1.2 加粗
> 我没有被加粗了

> **我被加粗了**
## 1.3 斜体
> 我倾斜了了

> *我倾斜了了*

## 1.4 高亮显示
> 我高亮了

> ==我高亮了==

## 1.5上标
> 2^2^

## 1.6 下标
> H~2~o

## 1.7 代码引用
> hello markdown!

## 1.8 代码引用

```python
print('hello nick')
```

```shell
print('hello nick')
```

`print('hello nick')`

## 1.9 插入链接

<https://blog.csdn.net/weixin_41605937?type=blog>

[庄小焱的博客](https://blog.csdn.net/weixin_41605937?type=blog "庄小焱的博客")

## 1.10  插入图片（链接）

![数据类型总结-搞笑结束.jpg?x-oss-process=style/watermark](https://imgmd.oss-cn-shanghai.aliyuncs.com/Python从入门到放弃/数据类型总结-搞笑结束.jpg?x-oss-process=style/watermark '好厉害')

## 1.11 有序列表
1. one
   1. 1.1
   2. 1.2
   3. 1.3
2. two 
3. three

## 1.12 无序列表
* one
  * 1.1
  * 1.2
* two
* three

## 1.13 分割线

---

## 1.14 表格
表格而且第二行必须得有，并且第二行的冒号代表对齐格式，分别为居中；右对齐；左对齐）

name | age | sex
:-:|:-|-:
tony|20|男
lucy|18|女

## 1.15 数学公式

内嵌数学公式：

$\sum_{i=1}^{10}f(i)\,\,\text{thanks}$

数学公式（块状）:

$$
\sum_{i=1}^{10}f(i)\,\,\text{thanks}
$$

# 博文参考