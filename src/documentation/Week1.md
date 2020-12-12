# Week1设计文档

## 类的设计

1. 构建GetHashSHA1类：

    - 传入file类，获得哈希值

    - 传入字符串，获得哈希值

2. 构建Key-Value类
    - 记录blob对象：传入文件，生成key值
    - 记录tree对象：传入字符串（tree对象的value），生成key值

3. blob类（继承Key-Value类）：
    - 通过传入的文件路径初始化blob对象
4. tree类（继承Key-Value类）：
    - 通过传入的文件夹路径初始化tree对象
5. ObjectStorage类
    - 以key-value的形式生成Object文件，存储在本地仓库
    - 通过key查找value：传入key值，在本地仓库查找是否有匹配的文件，返回并打印value

## 单元测试

1. 创建Blob对象
2. 创建Tree对象
3. 通过存在的哈希值查找value
4. 通过不存在的哈希值查找value