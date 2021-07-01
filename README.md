樱桃商城-电商平台
====================

![Spring Cloud Hoxton.SR1](https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR1-9cf.svg)
![Spring Boot 2](https://img.shields.io/badge/Spring%20Boot-2.2.6-blue.svg)
![JDK 1.8](https://img.shields.io/badge/JDK-1.8-brightgreen.svg)
![gradle](https://img.shields.io/badge/gradle-6.7-important.svg)
![license](https://img.shields.io/badge/license-GPL-orange.svg)

### 项目简介
```
目标：
    模拟商城，完整的购物流程、后端运营平台对前端业务的支撑，和对项目的运维，有各项的监控指标和运维指标。
技术点：
    核心框架：Spring Cloud、Spring Boot
    安全框架：Spring Security Oauth2
    网关框架：Spring Cloud Gateway
    持久层框架：MyBatis Plus、PageHelper
    数据库连接池：Alibaba Druid
    中间件：Redis、RocketMQ
    日志管理：Logback
    小程序框架：uniapp
```

### 目录结构说明
```
├─youngtao-admin----------------------------商家管理后台
|
├─youngtao-app------------------------------uniapp(待开发)
|
├─youngtao-api------------------------------系统后端
│  │
│  ├─youngtao-gateway-----------------------微服务网关中心
│  │  │
│  │  ├─youngtao-gateway-app----------------app、web网关
│  │
│  ├─youngtao-common------------------------微服务依赖中心
│  │  │
│  │  ├─youngtao-common-core----------------核心依赖
│  │  │
│  │  ├─youngtao-common-web-----------------web层依赖
│  │
│  ├─youngtao-provider----------------------微服务服务中心 - Spring Cloud版本
│  │  │
│  │  ├─youngtao-provider-gmc---------------商品管理中心
│  │  │
│  │  ├─youngtao-provider-gpc---------------商品促销中心
│  │  │
│  │  ├─youngtao-provider-omc---------------订单管理中心
│  │  │
│  │  ├─youngtao-provider-opc---------------支付管理中心
│  │  │
│  │  ├─youngtao-provider-uac---------------用户授权中心（待开发）
│  │  │
│  │  ├─youngtao-provider-umc---------------用户管理中心
│  │
│  ├─youngtao-provider-api------------------微服务API依赖
```

### 实现的功能
```
```

### 效果图

### 说明
项目持续开发中...