# 构建高可用的Nginx集群
> 为并发量高的优质网站量身定制高可用的并发解决方案nginx+keepalived
在这个互联网飞速发展的时代，人们已经离不开网络，其中网购最为常见。在家网购，上班路上网购，吃饭也购物，下班还网购，
> 2017年双十一天猫支付峰值达到了25.6万笔/秒。热点网站中频繁出现的大量并发如何去解决？
本次博文大家就会领略到，使用目前市面上处理并发能力非常强悍的nginx及keepalived如何快速的搭建一个处理高并发并的高可用的服务。

## Nginx
Nginx ("engine x") 是一个高性能的 HTTP 和 反向代理 服务器，也是一个 IMAP/POP3/SMTP 代理服务器 。
Nginx 是由 Igor Sysoev 为俄罗斯访问量第二的Rambler.ru 站点开发的，它已经在该站点运行超过四年多了。
Igor 将源代码以类BSD许可证的形式发布。自Nginx 发布以来，Nginx 已经因为它的稳定性、丰富的功能集、 示例配置文件和低系统资源的消耗而闻名了。
目前国内各大门户网站已经部署了Nginx，如新浪、网易、腾讯。

## Nginx 简介与配置
Nginx官网http://nginx.org/

Nginx目前已经更新到了1.20以后的版本，我们可以直接到官网下载，由于外面服务器大多都使用linux环境作为服务器，所以我们也弄一台linux环境的虚拟机。

```bash
# 安装相关依赖
yum install gcc-c++
yum install -y pcre pcre-devel
yum install -y zlib zlib-devel
yum install -y openssl openssl-devel
```
安装流程如下：
1. 我们把刚才从官网下载的Nginx，文件上传到centos的/usr/local/server目录去。
2. 紧接着我们在server目录下创建nginx目录 把压缩文件解压，进入解压的文件夹。
3. 首先配置nginx安装信息./configure
4. 执行编译和安装make && make install
```bash
# 解压并安装nginx
/home/xjl/software/nginx/nginx-1.13.10.tar.gz

[root@localhost server]# tar -xf nginx-1.13.10.tar.gz
[root@localhost server]# mkdir nginx
[root@localhost server]# cd nginx-1.13.10
[root@localhost nginx-1.13.10]# ./configure --prefix=/usr/local/server/nginx
[root@localhost nginx-1.13.10]# make && make install
```

```shell
–prefix#       #指定部署根目录，默认是/usr/local/nginx.此设置会更改其他配置目录的相对路径
–sbin-path     #可执行文件的路径，默认为/sbin/nginx
–conf-path     #配置文件的路径，默认为/conf/nginx.conf
–pid-path      #pid文件的存放路径，默认存放在/logs/nginx.pid，是一个存放nginx的master进程ID的纯文本文件，刚安装的时候不会生成，nginx启动的时候会自动生成。
–http-log-path #access日志存放位置，每个http的请求在结束的时候都会访问的日志。
–with-ld-opt   #加入第三方链接时需要的参数。编译之后nginx最终的可执行二进制文件是由编译后的目标文件和一些第三方的库链接生成的。如果想要将某个库链接到nginx中，就需要指定–with-ld-opt=目标库名-目标库路径
–with-debug    #将nginx需要打印debug调试级别日志的代码编译进nginx，这样才可以通过修改配置文件将调试日志打印出来，便于定位服务问题 
```
```bash
# 安装第三方模块
./configure --prefix=/usr/local/server/nginx --add-module=/usr/local/server/nginx_module/echo-nginx-module-0.61 --with-debug
```
```bash
# 这时候Nginx已经安全完成，我们进入/usr/local/server/nginx目录查看
[root@localhost nginx-1.13.10]# cd ../nginx
[root@localhost nginx]# ls
conf  html  logs  sbin
[root@localhost nginx]# 
```
```bash
# Nginx安装完成后不要忘了防火墙开放80端口
[root@localhost sbin]# vi /etc/sysconfig/iptables
# Firewall configuration written by system-config-firewall
# Manual customization of this file is not recommended.
*filter
:INPUT ACCEPT [0:0]
:FORWARD ACCEPT [0:0]
:OUTPUT ACCEPT [0:0]
-A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
-A INPUT -p icmp -j ACCEPT
-A INPUT -i lo -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 80 -j ACCEPT
-A INPUT -j REJECT --reject-with icmp-host-prohibited
-A FORWARD -j REJECT --reject-with icmp-host-prohibited
COMMIT

[root@localhost sbin]# service iptables restart
iptables: Setting chains to policy ACCEPT: filter          [  OK  ]
iptables: Flushing firewall rules:                         [  OK  ]
iptables: Unloading modules:                               [  OK  ]
iptables: Applying firewall rules:                         [  OK  ]
[root@localhost sbin]#
```
```bash
# nginx常用命令  
nginx -c /usr/local/server/nginx/conf/nginx.conf  # 启动nginx(windows下start nginx);  
nginx -s quit                                     # 停止ngix  
nginx -s reload                                   # 重新载入nginx(当配置信息发生修改时)  
nginx -v                                          # 查看版本  
nginx -t                                          # 查看nginx的配置文件的目录  
nginx -h                                          # 查看帮助信息  
```

## keepalived 简介与配置
Keepalived 是一种高性能的服务器高可用或热备解决方案，Keepalived 可以用来防止服务器单点故障的发生，
通过配合 Nginx 可以实现 web 前端服务的高可用。Keepalived 以 VRRP 协议为实现基础，
用 VRRP 协议来实现高可用性(HA)。VRRP(Virtual Router Redundancy Protocol)协议是用于实现路由器冗余的协议，
VRRP 协议将两台或多台路由器设备虚拟成一个设备，对外提供虚拟路由器 IP(一个或多个)，而在路由器组内部，
如果实际拥有这个对外 IP 的路由器如果工作正常的话就是 MASTER，或者是通过算法选举产生，
MASTER 实现针对虚拟路由器 IP 的各种网络功能，如 ARP 请求，ICMP，以及数据的转发等；
其他设备不拥有该虚拟 IP，状态是 BACKUP，除了接收 MASTER 的VRRP 状态通告信息外，
不执行对外的网络功能。当主机失效时，BACKUP 将接管原先 MASTER 的网络功能。

VRRP 协议使用多播数据来传输 VRRP 数据，VRRP 数据使用特殊的虚拟源 MAC 地址发送数据而不是自身网卡的 MAC 地址，
VRRP 运行时只有 MASTER 路由器定时发送 VRRP 通告信息，表示 MASTER 工作正常以及虚拟路由器 IP(组)，BACKUP 只接收 VRRP 数据，
不发送数据，如果一定时间内没有接收到 MASTER 的通告信息，各 BACKUP 将宣告自己成为 MASTER，发送通告信息，重新进行 MASTER 选举状态。

```bash
# 将文件上传到服务器,然后解压安装
cd /usr/local/server
tar -zxvf keepalived-1.2.18.tar.gz
cd keepalived-1.2.18
./configure --prefix=/usr/local/server/keepalived
make && make install
```

```bash
# 因为没有使用 keepalived 的默认路径安装（默认是/usr/local）,安装完成之后，需要做一些工作复制默认配置文件到默认路径
mkdir /etc/keepalived
cp /usr/local/server/keepalived/etc/keepalived/keepalived.conf /etc/keepalived/
# 复制 keepalived 服务脚本到默认的地址
cp /usr/local/server/keepalived/etc/rc.d/init.d/keepalived /etc/init.d/
cp /usr/local/server/keepalived/etc/sysconfig/keepalived /etc/sysconfig/
ln -s /usr/local/sbin/keepalived /usr/sbin/
ln -s /usr/local/server/keepalived/sbin/keepalived /sbin/
# 设置 keepalived 服务开机启动
chkconfig keepalived on
```

## nginx+tomcat集群 搭建高可用服务
当我们网站并发量高的时候，一台tomcat无法承受大量并发，可以考虑Nginx+Tomcat集群来实现。
我们这里准备3台tomcat，端口分别是8081、8082、8083，针对同一个域名，每次用Nginx实现不同的转发，
分别在每个tomcat的webapps目录下创建ROOT目录，并创建index.html，分别在html的body里标记1/2/3以示区分。

针对任何站点，几乎都要访问图片，而一个网页里面几乎有好些张图片，这时候会占据大量tomcat连接，造成大量并发，
我们可以通过Nginx配置直接访问硬盘里的图片，绕开tomcat。

我们在D盘创建一个nginx_images/images目录，然后在images目录放入一些图片，再在nginx的nginx.conf配置里配置一个虚拟机来访问。
```bash
server {
    listen       80;
    server_name  localhost;

	#所有带有images访问的路径直接取D盘:/nginx_images目录下查找
    location / {
        root D:/nginx_images;
      }
  	}
```

## keepalived+nginx 搭建高可用服务
再厉害的软件我们也不能保证它一定不挂，为了防止Nginx挂了导致整个服务无法使用的灾难发生，
我们这里可以考虑使用Keepalived+Nginx集群实现高可用。

### 集群资源配置

		VIP      |     IP         |       主机名             |      主从
	                 | 192.168.25.128 |       192.168.25.128    |     backup
	 192.168.25.150 |----------------|-------------------------|--------------
	                 | 192.168.25.140 |      192.168.25.140     |    master

### 配置主节点keepalived
```bash
global_defs {
	 ## keepalived 自带的邮件提醒需要开启 sendmail 服务。建议用独立的监控或第三方 SMTP 
	 router_id 192.168.25.140                      ## 标识本节点的字条串，通常为 hostname
}
	# keepalived 会定时执行脚本并对脚本执行的结果进行分析，动态调整 vrrp_instance 的优先级。如果
	# 脚本执行结果为 0，并且 weight 配置的值大于 0，则优先级相应的增加。如果脚本执行结果非 0，并且 weight
	# 配置的值小于 0，则优先级相应的减少。其他情况，维持原本配置的优先级，即配置文件中 priority 对应值。
	vrrp_script chk_nginx {
	 script "/etc/keepalived/nginx_check.sh"       ## 检测 nginx 状态的脚本路径
	 interval 2                                    ## 检测时间间隔
	 weight -20                                    ## 如果条件成立，权重-20
	}
	## 定义虚拟路由，VI_1 为虚拟路由的标示符，自己定义名称
	vrrp_instance VI_1 {
	 state MASTER                                  ## 主节点为 MASTER，对应的备份节点为 BACKUP
	 interface eth1                                ## 绑定虚拟 IP 的网络接口，与本机 IP 地址所在的网络接口相同，我的是 eth1
	 virtual_router_id 140                         ## 虚拟路由的 ID 号，两个节点设置必须一样，可选 IP 最后一段使用, 相同的 VRID 为一个组，他将决定多播的 MAC 地址
	 mcast_src_ip 192.168.25.140                   ## 本机 IP 地址
	 priority 100                                  ## 节点优先级，值范围 0-254，MASTER 要比 BACKUP 高
	nopreempt                                      ## 优先级高的设置 nopreempt 解决异常恢复后再次抢占的问题
	advert_int 1                                   ## 组播信息发送间隔，两个节点设置必须一样，默认 1s
	## 设置验证信息，两个节点必须一致
	authentication {
	 auth_type PASS
	 auth_pass 1111                                ## 真实生产，按需求对应该过来
	}
	## 将 track_script 块加入 instance 配置块
	 track_script {
	 chk_nginx                                     ## 执行 Nginx 监控的服务
	}
	## 虚拟 IP 池, 两个节点设置必须一样
	 virtual_ipaddress {
	 	192.168.199.131                            ## 虚拟 ip，可以定义多个
	 }
	}
```

### 配置从节点keepalived
```bash
# 找到130注解keepalived的配置文件keepalived.conf
global_defs {
	   router_id 192.168.25.128
}
	
vrrp_script chk_nginx {
 script "/etc/keepalived/nginx_check.sh"
 interval 2
 weight -20
}
	
vrrp_instance VI_1 {
    state BACKUP
    interface eth2
    virtual_router_id 128
    priority 90
    mcast_src_ip 192.168.211.128
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    track_script {
    chk_nginx
    }
    virtual_ipaddress {
        192.168.211.131
    }
}
```

### Nginx检查脚本
```bash
# 在/etc/keepalived目录下创建nginx_check.sh文件
#!/bin/bash
A=`ps -C nginx –no-header |wc -l`
if [ $A -eq 0 ];then
    /usr/local/server/nginx/sbin/nginx
    sleep 2
    if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then
        killall keepalived
    fi
fi
```

## 使用的docker构建nginx集群
 你可以参考我的仓库的docker相关的原理的仓库。里面涉及到docker中的多种配置。
