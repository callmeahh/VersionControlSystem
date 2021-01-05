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
    - parentNum(String commit)：获取当前commit的parent个数
    - getBefore(int n)：获取当前commit指定次数的parent
    - commitExists(String commit)：判断commit是否存在当前分支记录中
    - logReset(int n)：回滚n次，修改repository
    - logReset(String commit)：回滚到指定commit，修改repository
    - treeReset(int n)：回滚n次，修改working tree
    - treeReset(String commit)：回滚到指定commit，修改working tree
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
    - 新增方法getFullName(String hash):  输入commit的key的前几位进行匹配，返回完整的key
5. 修改Bug：修改创建tree对象时出现key值不匹配的Bug；修改创建commit对象每次需要传tree对象的参数的问题。
6. 新增Reset类：
    - resetHard(int n)：指定次数的working tree和repository回滚，不可跨分支回滚。
    - resetHard(String commit)：指定commit的working tree和repository回滚，可跨分支回滚，可向后回滚。
    - resetMixed(int n)：指定次数的repository回滚，不可跨分支回滚。
    - resetMixed(String commit)：指定commit的repository回滚，可跨分支回滚，可向后回滚。

## 单元测试

1. 初始化仓库：创建所有所需的文件夹。

2. 设置需要管理的路径

3. 分支操作：
    1. 新建默认分支。仓库中出现中出现master分支。
    2. 提交第一次commit
3. 修改文件后，提交第二次commit
    
    4. 创建切换分支testing，修改文件后，提交第三次commit
    
4. 回滚操作：
    1. working tree和repository回滚1次，回滚成功，当前分支的commit，日志和全部日志均回滚，工作区文件也回滚。
    2. working tree和repository回滚1次，回滚失败，提示“该分支不存在此commit，请输入正确的回滚次数”。
    3. repository回滚到4d765（跨分支向前回滚），回滚成功，当前分支的commit，日志和全部日志均回滚，工作区文件未回滚。
    
    4. working tree和repository回滚到7089c（跨分支向后回滚），回滚成功，当前分支的commit，日志和全部日志均回滚，工作区文件也回滚。
    