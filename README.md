# smile_health
## 一、需求分析

是一款应用于健康管理机构的业务系统，采用Maven分模块开发方式，后台管理系统有会员管理、预约管理、健 康评估、健康干预等功能。移动端有会员管理、体检预约、体检报告等功能。

## 二、项目结构
系统技术选型
![WechatIMG1201](https://github.com/kiligsmile/smile_health/assets/69560716/d8d24146-8db3-4e67-ac46-de169ee1ac72)
<br/>
本项目采用maven分模块开发方式，即对整个项目拆分为几个maven工程。
<br/>
各模块职责定位：
![WechatIMG1202](https://github.com/kiligsmile/smile_health/assets/69560716/1b736982-a4f8-423a-9895-69bc8f29a125)
<br/>
## 三、后台
### 1.预约管理
#### 1.1 预约设置
![image](https://github.com/kiligsmile/smile_health/assets/69560716/be1c9b90-bf97-4bc8-aafd-a6a4b5578976)
<br/>
### 1.2 套餐管理
![image](https://github.com/kiligsmile/smile_health/assets/69560716/28dc09da-d6a0-4b4e-8719-263ee642c19e)
<br/>
#### 1.3 检查组管理
![image](https://github.com/kiligsmile/smile_health/assets/69560716/02168e80-f58c-43db-a0a9-6bfda090240a)
<br/>
#### 1.4 检查项管理
![image](https://github.com/kiligsmile/smile_health/assets/69560716/cfc6353c-d61f-46d0-8781-856104703ef7)
<br/>
### 2.统计分析
#### 2.1 会员数量统计
![image](https://github.com/kiligsmile/smile_health/assets/69560716/b05e7b9c-24a7-47c2-a5b8-712e231c227c)
<br/>
#### 2.2 套餐预约占比
![image](https://github.com/kiligsmile/smile_health/assets/69560716/4402df9b-d10d-449c-9191-0debeea30574)
<br/>
#### 2.3 运营数据统计
![image](https://github.com/kiligsmile/smile_health/assets/69560716/9ffd5481-1ace-4743-b438-dc10ab15c9f1)

## 四、移动端
#### 1.首页
![image](https://github.com/kiligsmile/smile_health/assets/69560716/43c77a76-359f-49bd-96db-e2597b481232)
<br/>
#### 2.体检预约
![image](https://github.com/kiligsmile/smile_health/assets/69560716/2f39b234-ce46-4ffa-9a1a-48b90e5c072c)
<br/>
![image](https://github.com/kiligsmile/smile_health/assets/69560716/827daed5-ff0b-4543-bfa3-389061a69de0)

## tips
**后台登录账号密码**：
- admin/1234
<br/>

**zookeeper启动**：
- cd /usr/local/zookeeper-3.4.6/bin
- sudo ./zkServer.sh start
<br/>

**Redis启动**
- redis-server /usr/local/etc/redis.conf
<br/>

**后台系统登录地址**
- http://localhost:82/login.html
<br/>

**移动端地址**
- http://localhost/
<br/>
