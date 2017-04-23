- 匹配行数
```
[root@hadoop1 ~]# grep  -v "ProcessThread" zookeeper.out |wc -l 
359
[root@hadoop1 ~]# grep  -v -c  "ProcessThread" zookeeper.out 
359

-v:排除
-c:统计行数
```

- 重点标记
```
 grep   "ProcessThread" zookeeper.out --color=auto
```
- 匹配多个样式

```
grep -e "pattern1" -e "pattern2"
```

- 递归搜索java 文件
```
grep "Thread" . -r --include=*.{c,out} --color=auto


###	xargs

-文件相关
```
 find . -type f -name "*.out" -print0 | xargs -0 rm -f
 find . -type f -name "*.php" -print0 | xargs -0  wc -l
 #xargs -0将\0作为定界符。

```
-  复制所有图片文件到 /data/images 目录下

```
ls *.jpg | xargs -n1 -I cp {} /data/images
```
