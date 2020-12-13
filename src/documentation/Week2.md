# Week2设计文档

## 类的设计

1. 更新tree类（继承Key-Value类）：

   - 更新getValue方法

2. 更新ObjectStorage类

   - 更新storeFromString方法：增加参数boolean append，判断是追加写入还是覆盖写入
   - 增加final String Head静态变量
   - searchHead方法：在本地仓库查找Head文件，返回file
   - getParent方法：读取Head文件的第一个tree的key作为parent
   - stringFromFile方法：将file转换成string
   - getHead方法

3. commit类（继承Key-Value类）：

   - 传入tree类的key初始化commit对象
   - getValue方法

4. Head类：

   - 调用storeFromString方法直接初始化Head对象，生成Head文件

   - getValue方法：调用searchHead方法查找Head文件，将Head文件中的内容赋给value

## 单元测试

1. 创建Tree对象
2. 创建Commit对象
3. 创建或更新Head对象