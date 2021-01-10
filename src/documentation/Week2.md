# Week2设计文档

## 类的设计

1. 更新key-value（KeyValueObject）类

   - 将record()方法合并，并设置为抽象方法
   - 将generateKey()方法设置为抽象方法
   - 新增getValue()抽象方法
   - 修改部分方法的可见性为protected

2. 更新Blob/Tree类（继承Key-Value类）

   - 实现父类中的抽象方法

   - 更新getValue()方法
   - 使用try-catch处理异常

3. 新增Commit类（继承Key-Value类）：

    - 构造方法：传入Tree类型的对象初始化commit对象；通过Tree类型的对象和commit注释初始化commit对象
    - 在构造方法中，增加更新HEAD文件的方法
    - 实现父类中的抽象方法

4. 更新ObjectStorage类
    - 更新storeFromString()方法：增加参数boolean append，判断是追加写入还是覆盖写入
    - 新增getString()方法：读取文件中的内容并返回String
    - 新增setFilePath()，getFilepath()方法

5. 新增Head类：

    - Head类中所有的方法和变量都为静态方法或静态变量，无需对其进行实例化

    - init()方法：查找是否已经存在HEAD文件，如果不存在则创建。返回boolean类型
    - updateKey()：如果当前HEAD文件为空，将parent设置为0000000000000000000000000000000000000000，否则为HEAD文件中的哈希值，并将parent和新commit的哈希值一起追加存入HEADLog中，随后将commit的哈希值覆盖存入HEAD中
    - getHeadFile()/getHeadLogFile()：返回HEAD文件/HEADLog文件
    - getHead()/getHeadlog()：返回HEAD文件中的内容/HEADLog文件中的内容

## 单元测试

1. 创建两个Tree对象
2. 通过Tree1对象创建commit对象，观察HEAD文件和HEADLog文件中存储的内容
3. 通过Tree2对象创建commit对象，观察HEAD文件和HEADLog文件中存储的内容



时间：2020.12.20