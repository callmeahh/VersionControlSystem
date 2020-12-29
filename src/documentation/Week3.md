# Week3设计文档

## 功能完善与改进

1. 实现分支管理：实现新建分支、分支切换，分支回滚三个主要功能。并添加重命名分支、分支删除、不允许重名分支创建、不允许存在两个空分支等功能点。
2. 优化存储路径：将内部管理文件夹分为logs（保存日志文件）、objects（保存key-value object）和refs（保存分支头指针）三个文件夹。

## 类的设计

1. 新增Branch类：将每一个分支都作为branch类的一个实例。
    - 构造方法：三个构造方法均调用BranchControl类中的initBranch()方法进行初始化
        - 无参构造方法（默认为分支名为master）
        - 传入分支名称创建分支
        - 传入分支名称创建分支并切换。
    - 方法：获得分支名称；获得分支提交日志；分支重命名。
2. 新增BranchControl类：
    - 将上周的Head类中方法进行优化和修改，整合所有与分支控制相关的方法。
    - printAllBranches()：打印所有分支
    - switchBranch()：切换分支
    - getCurrentBranchLog()：获取当前分支日志的内容
    - getAllLog()：得到总日志内容
3. 新增FilepathSetting类：
    - 设计思路：由于版本管理中会产生许多文件，如分支和Key-Value类的文件等，设置不同的文件夹区分。该类中以静态变量和静态方法设置所有文件路径。
    - 路径优化
    - InitRepo()：初始化仓库，创建所需文件夹，避免后续产生异常。
    - setTargetFilepath()和getTargetFilepath()方法：记录需要被管理的路径，并存储在Info文件中。
4. 修改ObjectStorage类：
    - 重载方法storeFromString()：传入路径参数，使之能保存到指定路径。
    - 新增方法updateFile()：传入File类型参数，更新文件内容。
    - 新增方法deleteDir()：清空整个文件夹
    - 新增方法formatValue()：将之前存储的value格式化成二维数组
    - 新增方法restoreFiles()：将二位数组中的记录的key还原成文件
5. 修改Bug：修改创建tree对象时出现key值不匹配的Bug；修改创建commit对象每次需要传tree对象的参数的问题。

## 单元测试

1. 初始化仓库：创建所有所需的文件夹。

2. 设置需要管理的路径

3. 分支操作：
    1. 新建默认分支，成功。仓库中出现中出现master分支。
    2. 在没有commit的情况（空分支）创建第二个分支，失败。

4. 第一次提交commit，并创建分支：
    1. 提交commit后创建分支testing，成功。仓库中出现testing分支。
    2. 创建分支testing2并切换，成功。仓库中出现testing分支，并且头指针指向testing2。

5. 修改文件后，第二次提交commit：testing2分支的日志和头指针均发生改变

6. 回滚到testing分支：文件夹恢复成testing分支时的状态。