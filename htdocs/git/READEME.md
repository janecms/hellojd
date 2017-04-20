## GIT 使用
-	配置命令
>> 1.显示当前的 Git 配置

>> ```git config --list```

>> 2.设置提交仓库时的用户名信息

>> ```git config --global user.name “your name”```

>> 3.设置提交仓库时的邮箱信息

>> ```git config --global user.email “408542507@qq.com”```

> 提交时转换为LF，检出时转换为CRLF，默认设置不用修改

>>``` git config –-global core.autocrlf true```

> 允许提交包含混合换行符的文件

>> ```git config –-global core.safecrlf false```

> 存储凭证

```git config –-global credential.helper wincred
```

-	在当前目录新建一个 Git 代码库
>> ```git init```

-	下载一个项目和它的整个代码历史

 >>url 格式: https://github.com/[userName]/reposName
 
>> ```git clone [url]```

- 添加删除文件

>> 添加指定文件到暂存区

>> ```git add [file1] [file2]```

>> 删除工作区文件，并且将这次删除放入暂存区

>> ```git rm [file1] [file2]```

>>  改名文件，并且将这个改名放入暂存区

>> ```git mv [file-origin] [file-renamed]```

- 代码提交

>>提交暂存区到仓库

 ```shell
git commit –m [message]  
git commit –a –m [message]
```

- 查看信息

> 显示变更信息

```git status```

> 显示当前分支的历史版本

```
git log
git log --oneline
```

>以图形的方式打印 Git 提交日志
```
git log –pretty=format:’%h %ad | %s%d’ –graph –date=short
```

- 同步远程仓库

> 增加远程仓库，并命名

``` git remote add [shortname] [url] ```

> 将本地的提交推送到远程仓库

```
git push [remote] [branch]
```
> 将远程仓库的提交拉下到本地

```git pull [remote] [branch]```

- .gitignore

> 强制添加 .gitignore 忽略的文件

```git add –f <file name>```

> 查看 .gitignore 策略生效行号

``` git check-ignore –v <file name>```

- 协议

> 克隆本地仓库

```git clone /c/wd/test.git```

> 克隆本地仓库，不建议使用 file:// 

```git clone file:///c/wd/test.git```

> 添加远程仓库的链接

``` git remote add origin /c/wd/test.git```

- 克隆远程仓库

```git clone git://server_ip/test.git```

- 添加远程仓库的链接

```git remote add origin git://server_ip/test.git```

- 生成 RSA 密钥对

```ssh-Keygen -t rsa -C "your email"```



## 资源

- https://github.com/wangding/courses