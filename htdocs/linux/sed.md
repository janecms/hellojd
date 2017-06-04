﻿## Sed 命令
### 文本间隔
- 在每一行后面增加一空行

	```sed G```

- 将原来的所有空行删除并在每一行后面增加一空行。

	``` sed '/^$/d;G'```

- 删除所有偶数行	
	```
	sed 'n;d'
	```
 - 在匹配式样“regex”的行之前插入一空行
 
	```sed '/regex/{x;p;x;}'```
	
- 在匹配式样“regex”的行之后插入一空行

	```sed '/regex/G'```
- 在匹配式样“regex”的行之前和之后各插入一空行

	```sed '/regex/{x;p;x;G;}'```

### 编号
- 每一行进行编号(左对齐方式)
	```
	sed = filename | sed 'N;s/\n/\t/'
	```	
 - 对文件中的所有行编号，但只显示非空白行的行号。
 
	```sed '/./=' filename | sed '/./N; s/\n/ /'```

 - 计算行数 （模拟 "wc -l"）
 
	```sed -n '$='```

	
### 文本转换和替代
 - Unix环境：转换DOS的新行符（CR/LF）为Unix格式。
	 ```
	 sed 's/.$//'                     # 假设所有行以CR/LF结束
	 sed 's/^M$//'                    # 在bash/tcsh中，将按Ctrl-M改为按Ctrl-V
	 sed 's/\x0D$//'                  # ssed、gsed 3.02.80，及更高版本
	```
 - Unix环境：转换Unix的新行符（LF）为DOS格式。
	 ```
	 sed "s/$/`echo -e \\\r`/"        # 在ksh下所使用的命令
	 sed 's/$'"/`echo \\\r`/"         # 在bash下所使用的命令
	 sed "s/$/`echo \\\r`/"           # 在zsh下所使用的命令
	 sed 's/$/\r/'                    # gsed 3.02.80 及更高版本
	 sed "s/\r//" infile >outfile     # UnxUtils sed v4.0.7 或更高版本
	 tr -d \r <infile >outfile        # GNU tr 1.22 或更高版本

	```
 - 将每一行前导的“空白字符”（空格，制表符）删除
	```
	 sed 's/^[ \t]*//'                # 见本文末尾关于'\t'用法的描述
	```
 - 将每一行拖尾的“空白字符”（空格，制表符）删除
	```
	 sed 's/[ \t]*$//'                # 见本文末尾关于'\t'用法的描述
	```
 - 在每一行开头处插入5个空格（使全文向右移动5个字符的位置）
 ```sed 's/^/     /'```

 - 以79个字符为宽度，将所有文本右对齐
 ```sed -e :a -e 's/^.\{1,78\}$/ &/;ta'  # 78个字符外加最后的一个空格	```

 - 以79个字符为宽度，使所有文本居中。
 ```
 在方法1中，为了让文本居中每一行的前头和后头都填充了空格。 
 sed  -e :a -e 's/^.\{1,77\}$/ & /;ta'                     # 方法1
 在方法2中，在居中文本的过程中只在文本的前面填充空格，并且最终这些空格将有一半会被删除。此外每一行的后头并未填充空格。
 sed  -e :a -e 's/^.\{1,77\}$/ &/;ta' -e 's/\( *\)\1/\1/'  # 方法2
 ```
 - 在每一行中查找字串“foo”，并将找到的“foo”替换为“bar”
```
 sed 's/foo/bar/'                 # 只替换每一行中的第一个“foo”字串
 sed 's/foo/bar/4'                # 只替换每一行中的第四个“foo”字串
 sed 's/foo/bar/g'                # 将每一行中的所有“foo”都换成“bar”
 sed 's/\(.*\)foo\(.*foo\)/\1bar\2/' # 替换倒数第二个“foo”
 sed 's/\(.*\)foo/\1bar/'            # 替换最后一个“foo”
 ```
 
  - 只在行中出现字串“baz”的情况下将“foo”替换成“bar”
 ```sed '/baz/s/foo/bar/g'```

 - 将“foo”替换成“bar”，并且只在行中未出现字串“baz”的情况下替换
 ```sed '/baz/!s/foo/bar/g'```

 - 不管是“scarlet”“ruby”还是“puce”，一律换成“red”
 ```
 sed 's/scarlet/red/g;s/ruby/red/g;s/puce/red/g'  #对多数的sed都有效
 gsed 's/scarlet\|ruby\|puce/red/g'               # 只对GNU sed有效
```
 - 倒置所有行，第一行成为最后一行，依次类推（模拟“tac”）。
```
 sed '1!G;h;$!d'               # 方法1
 sed -n '1!G;h;$p'             # 方法2
```
 - 将行中的字符逆序排列，第一个字成为最后一字，……（模拟“rev”）
 sed '/\n/!G;s/\(.\)\(.*\n\)/&\2\1/;//D;s/.//'

 - 将每两行连接成一行（类似“paste”）
 ```sed '$!N;s/\n/ /'```

 - 如果当前行以反斜杠“\”结束，则将下一行并到当前行末尾并去掉原来行尾的反斜杠
 ```sed -e :a -e '/\\$/N; s/\\\n//; ta'```

 - 如果当前行以等号开头，将当前行并到上一行末尾 并以单个空格代替原来行头的“=”
 ```sed -e :a -e '$!N;s/\n=/ /;ta' -e 'P;D'```

 - 为数字字串增加逗号分隔符号，将“1234567”改为“1,234,567”
 ```
 gsed ':a;s/\B[0-9]\{3\}\>/,&/;ta'                     # GNU sed
 sed -e :a -e 's/\(.*[0-9]\)\([0-9]\{3\}\)/\1,\2/;ta'  # 其他sed
```
 - 为带有小数点和负号的数值增加逗号分隔符（GNU sed）
 ```gsed -r ':a;s/(^|[^0-9.])([0-9]+)([0-9]{3})/\1\2,\3/g;ta'```

 - 在每5行后增加一空白行 （在第5，10，15，20，等行后增加一空白行）
 ```
 gsed '0~5G'                      # 只对GNU sed有效
 sed 'n;n;n;n;G;'                 # 其他sed
 
 ```
 ### 选择性地显示特定行
 
 ### 选择性地删除特定行：
[sed1line](http://sed.sourceforge.net/sed1line_zh-CN.html)
[sed](https://www.gnu.org/software/sed/manual/sed.html)


	