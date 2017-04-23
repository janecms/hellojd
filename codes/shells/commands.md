-	1设置不可修改文件

	chattr +i file
	
-	2.生成空白文件

	touch filename
-	3.创建长路径

	mkdir -p

-	4.文本文件的交集与差集

	comm
	
-	5.查找链接文件	
	```linux
	ls -l ~ |grep "^l" |awk '{print $9}'	
	find ~ -type l -print
	```
	
-	6.打印文件类型信息
```
	file -b filename #不包括文件名
	file filename
```

-	7. 遍历命令输出
```
    while read line;
     do
		#statement
     done < <(find $path -type f -print)
```
## 数组

- 删除操作
```
　　　　清除某个元素：unset arr_number[1]，这里清除下标为1的数组；
　　　　清空整个数组：unset arr_number;
```
- 数组长度
	 ${#a[@]}

- 数组元素
	```
	${my_array[*]}
	${my_array[@]}
	```	
- 分片访问

　　　　分片访问形式为：${数组名[@或*]:开始下标:结束下标}，注意，不包括结束下标元素的值。

　　　　例如：${arr_number[@]:1:4}，这里分片访问开始下标为1到结束下标为4的值结果输出为新数组，但不包括下标为4的值。

- 模式替换

　　　　形式为：${数组名[@或*]/模式/新值}
　　　　例如：${arr_number[@]/2/98}	

- 获取key值
	```
	 ${!arr[@]}
	 ${!arr[*]}	 
	```