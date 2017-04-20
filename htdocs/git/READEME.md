## GIT 使用
-	配置命令
>> 1.显示当前的 Git 配置

>> `git config --list`

>> 2.设置提交仓库时的用户名信息

>> `git config --global user.name “your name”`

>> 3.设置提交仓库时的邮箱信息

>> `git config --global user.email “408542507@qq.com”`

-	在当前目录新建一个 Git 代码库
>> `git init`

-	下载一个项目和它的整个代码历史

 >>url 格式: https://github.com/[userName]/reposName
 
>> `git clone [url]`

- 添加删除文件

>> 添加指定文件到暂存区

>> `git add [file1] [file2]`

>> 删除工作区文件，并且将这次删除放入暂存区

>> 'git rm [file1] [file2]'

>>  改名文件，并且将这个改名放入暂存区

>> `git mv [file-origin] [file-renamed]`

- 代码提交

>>提交暂存区到仓库

>>`
	git commit –m [message]  
	git commit –a –m [message]  
	`
	
