# Week3-4 设计文档

## 功能完善与改进

1. **实现分支管理**：实现新建分支、分支切换，分支回滚三个主要功能。并添加重命名分支、分支删除、不允许重名分支创建、不允许存在两个空分支等功能点。
2. **优化存储路径**：将内部管理文件夹分为logs（保存日志文件）、objects（保存key-value object）和refs（保存分支头指针）三个文件夹。
3. **优化blob存储方法**：修复blob类型的对象在恢复时只能恢复txt类型的bug。

## 类的设计

1. 新增Branch类：将每一个分支都作为branch类的一个实例。
    - 构造方法：三个构造方法均调用BranchControl类中的initBranch()方法进行初始化
        - 无参构造方法（默认为分支名为master）
        - 传入分支名称创建分支
        - 传入分支名称创建分支并切换。
    - isBranch(String)：判断b1是否为当前分支名。
    - initBranch(String)：初始化分支（新建分支时）。
    - switchBranch(String)：切换分支。
    - renameBranch(String, String)：重命名分支。
    - printAllBranches()：打印所有分支。
    - getBranchName()：获取分支名。
    
    
    
    - printAllBranches()：打印所有分支
    - switchBranch()：切换分支
    - getCurrentBranchLog()：获取当前分支日志的内容
    - getAllLog()：得到总日志内容
    - isBranch(String b1)：判断string是否为当前分支
    - renameBranch(String branchName, String newBranchName)：分支重命名
    - parentNum(String commit)：获取当前commit的parent个数
    - getBefore(int n)：获取当前commit指定次数的parent
    - commitExists(String commit)：判断commit是否存在当前分支记录中
    - logReset(int n)：回滚n次，修改repository
    - logReset(String commit)：回滚到指定commit，修改repository
    - treeReset(int n)：回滚n次，修改working tree
    - treeReset(String commit)：回滚到指定commit，修改working tree
    
3. 新增FilepathSetting类：
    - 设计思路：由于版本管理中会产生许多文件，如分支和Key-Value类的文件等，设置不同的文件夹区分。该类中以静态变量和静态方法设置所有文件路径。
    - 路径优化：设置当前文件夹（System.getProperty("user.dir")）为需要管理的文件夹。
    - getFilepath()：获取本地仓库文件夹，存放logs，objects，refs文件夹和HEAD，Info文件，HEAD文件记录当前分支，Info文件记录工作区路径。
    - getObjectFilepath()：获取objects文件夹，存放blob，tree，commit文件。
    - getHeadFilepath()：获取refs文件夹，存放各分支文件，记录各分支当前commit的key。
    - getAllLogFilepath()：获取logs文件夹，存放refs文件夹和HEAD文件，HEAD文件记录所有历史记录。
    - getLogFilepath()：获取logs.refs文件夹，存放各分支文件，记录各分支历史记录。
    - InitRepo()：初始化目标文件夹，生成objects文件夹，refs文件夹，logs文件夹，logs.refs文件夹。
    - isInitialized()：检查.git文件夹是否存在
    
4. 修改ObjectStorage类：
    - 重载方法storeFromString()：传入路径参数，使之能保存到指定路径。
    - 新增方法updateFile()：传入File类型参数，更新文件内容。
    - 新增方法deleteDir()：清空整个文件夹
    - 新增方法formatValue()：将之前存储的value格式化成二维数组
    - 新增方法restoreFiles()：将二位数组中的记录的key还原成文件
    - 新增方法getFullName(String hash):  输入commit的key的前几位进行匹配，返回完整的key
    - 修改方法storeFromFile()：通过GZIPOutputStream包对文件进行压缩存储。
    - 新增方法decompress()：通过GZIPOutputStream包对压缩存储的文件进行还原。
    
4. 修改Bug：修改创建tree对象时出现key值不匹配的Bug；修改创建commit对象每次需要传tree对象的参数的问题。

6. 新增Reset类：
    - resetHard(int n)：指定次数的working tree和repository回滚，不可跨分支回滚。
    - resetHard(String commit)：指定commit的working tree和repository回滚，可跨分支回滚，可向后回滚。
    - resetMixed(int n)：指定次数的repository回滚，不可跨分支回滚。
    - resetMixed(String commit)：指定commit的repository回滚，可跨分支回滚，可向后回滚。
    
6.   新增FileOperation类

    - getAllBranchName()：遍历refs.分支，获取分支名列表。
    - getCurrentBranchFile()：获取HEAD文件。
    - getCurrentBranch()：通过HEAD文件，获取当前分支名。
    - getHeadFile()：获取refs.分支的当前分支文件。
    - getHeadLogFile()：获取logs.refs.分支的当前分支文件。
    - getAllLogFile()：获取logs.HEAD文件。
    - getHead()：通过refs.分支的当前分支文件，获取当前分支commit的key。
    - getHead(String)：通过refs.分支的指定分支文件，获取指定分支commit的key。
    - updateKey(String, String)：由key和message更新logs.refs.分支的当前分支文件，logs.HEAD文件，refs.分支的当前分支文件。

7. 新增log类

    - getCurrentBranchLog()：获取logs.refs.分支的当前分支文件的内容。
    - getAllLog()：获取logs.HEAD文件的内容。

8. 新增CLI类：

    - 使用Jcommander包解析参数，进行命令行交互。

## 单元测试

### 分支管理

1. 初始化仓库：创建所有所需的文件夹。
2. 设置需要管理的路径
3. 分支操作：
    1. 新建默认分支。仓库中出现中出现master分支。
    2. 提交第一次commit
4. 修改文件后，提交第二次commit
    1. 创建切换分支testing，修改文件后，提交第三次commit
5. 回滚操作：
    1. working tree和repository回滚1次，回滚成功，当前分支的commit，日志和全部日志均回滚，工作区文件也回滚。
    2. working tree和repository回滚1次，回滚失败，提示“该分支不存在此commit，请输入正确的回滚次数”。
    3. repository回滚到4d765（跨分支向前回滚），回滚成功，当前分支的commit，日志和全部日志均回滚，工作区文件未回滚。
    4. working tree和repository回滚到7089c（跨分支向后回滚），回滚成功，当前分支的commit，日志和全部日志均回滚，工作区文件也回滚。
6. 分支重命名：
   1. 输入不存在的分支test时，重命名失败。
    2. 输入已存在的分支master时，重命名失败。
    3. 输入存在的分支testing和不存在的新命名test1时，重命名成功。

### 命令行交互

测试方法：复制以下代码，粘贴进CLI.java的提示位置。

```java
commander.parse("--help"); //查看帮助
commander.parse("--init"); //初始化仓库
commander.parse("branch","-n","testing"); //在空分支上新建分支，失败
commander.parse("commit"); //提交commit
commander.parse("commit","-m","commit1"); //（修改文件）提交带注释的commit
commander.parse("branch","-n","testing"); //创建testing分支
commander.parse("branch","-n","testing"); //重复创建testing分支，失败
commander.parse("branch","-n","testing2","-s"); //创建testing2分支并切换
commander.parse("commit","-m","commit2"); //（修改文件）提交带注释的commit
commander.parse("switch","testing"); //切换至testing分支
commander.parse("switch","testing0"); //切换至不存在的分支，失败
commander.parse("commit","-m","commit3"); //（修改文件）提交带注释的commit
commander.parse("reset","-HARD","-t","1"); //回滚一次commit
commander.parse("reset","-HARD","-t","5"); //回滚次数超过当前分支commit次数，失败
commander.parse("reset","-HARD","-c","commitID"); //回滚到指定commit
commander.parse("reset","-HARD","-c","notExist"); //回滚到不存在commit，失败
commander.parse("reset","-t","1"); //回滚一次commit
commander.parse("reset","-c","commitID"); //回滚到指定commit
commander.parse("branch","-a"); //查看所有分支
commander.parse("branch","-c"); //查看当前分支
commander.parse("rename","testing","testing2"); //重命名至已存在分支，失败
commander.parse("rename","testing0","testing2"); //重命名的分支不存在，失败
commander.parse("rename","testing","testing3"); //重命名分支，成功
commander.parse("log","-a"); //查看全部日志
commander.parse("log","-c"); //查看当前分支日志
```



时间：2021.1.7