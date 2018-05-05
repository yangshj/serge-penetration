参考一下资料:

https://www.cnblogs.com/beginmind/p/6380489.html

https://blog.csdn.net/u011580175/article/details/71001796

http://www.cnblogs.com/wunaozai/p/5545150.html



当main类依赖多个jar时，可以把多个jar打包到一个目录，然后用-Djava.ext.dirs指定该目录，引用依赖的多个jar。
java -Djava.ext.dirs=<多个jar包的目录> com.jiuqi.penetration.udp.EchoServer
java -Djava.ext.dirs=./ com.jiuqi.penetration.udp.EchoServer

Linux上运行，把依赖的jar包放到同一目录下
/opt/java/jdk1.7.0_79/bin/java -Djava.ext.dirs=./ com.jiuqi.penetration.udp.EchoServer
