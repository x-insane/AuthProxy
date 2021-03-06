## AuthProxy

简单的反向代理，用于权限认证。

### 为什么会有这个项目？

我平时喜欢写一些文章，按照不同分类分了好几个站点，然而并不是每个站点每篇文章都能够公开的，虽然这些站点都是我自己在管理，却只提供整站加密的功能，想要给不同类型的子站加上精确到文章的权限控制的工作量实在太大，而且以后可能会有更多的子站需要这样的权限控制系统，所以决定把这个系统独立出来，以代理的方式对外提供服务。

看似简单的需求，其实内部的设计极其复杂，首先要明确以下两点前提：

- 被代理的子站需要配置成在外部不可访问，即使代理系统崩溃也不可被外部访问（安全性）
- 在被代理的子站发生站内跳转（包括HTTP、HTTPS间的跳转）时应做好URL重写（稳定性）

如果只做Token认证，这个项目可能并没有多复杂，但是我希望这个系统是带有用户认证子系统的，这样可以一次登录后保持登录状态，不必每次都带上token才能访问。于是又开始了挖坑过程：

- 被授权的用户可以创建分享链接，这些链接是可以被管理和取消授权的
- 用户是分角色的，不同角色可以拥有不同的权限，低级别用户无法拥有高级别用户权限（初定分为普通用户、管理员和超级管理员）
- 用户的权限应该是分层的，被授权的用户可以创建子账号，并且可以管理自己的子账号
- 权限应做好分类，至少有三类权限：全局通用权限、站点权限、页面权限
- 若用户具有站点权限，则可以访问该站点下所有页面（私有页面除外）
- 权限认证应做好日志记录，可以分类查询认证历史
- 可以控制访问来源和次数限制
- 认证失败时可引导用户登陆或输入Token
- ……

哇，原来权限这个问题这么复杂，给自己挖的坑越来越大（是不是说明我总想把一切都掌握在手中？？）……

**这个项目将是第一个认真按照开发流程一步一步来的项目，希望不再是“突然想到-随意开发-遇到困难-不了了之”的过程，这一次，我要督促自己按照计划一步一步进行，如果进度计划不合理，也应该及时分析原因并修改计划，全程应做好开发文档和过程记录。**

### 站点分布

该系统暂不考虑分布式系统，所有站点能访问同一个数据库。

- 用于用户认证系统的站点一个
- 用于代理的站点每个被代理站点一个

### 认证过程简单描述

认证分为两种，Token认证和用户认证：

- 当用户用带有Token的链接访问被管理的页面时，如果Token有效且该Token具有访问该页面的权限，则内部反向代理到真实服务器
- 当已登录认证系统的用户访问被管理的页面时，如果该用户具有访问该页面的权限，则内部反向代理到真实服务器
- 上述两条规则依次判断，只要有一项认证成功则认证结束（Token认证优先于用户认证），若认证均不成功，则拒绝访问


#### 用户认证过程

由于代理站点有多个，而希望用户在登陆一个代理站点后能记住登录状态，故设置一个独立的用户认证系统。在进行用户认证的时候，通过浏览器URL跳转请求获取登录信息。

这里没有使用共享cookie的方案，因为cookie只能在同一顶级域名及其子域名下（如login.a.com和product.a.com）使用，对于不同的顶级域名（如a.com和b.com），将无法共享cookie。

简单跳转方案如下：

- 首先判断用户在该站点是否有登录态（session判断）
- 如果站点没有登录态，则引导用户到用户认证系统进行授权并附带回调URL，用户认证系统将用户重新引导到原站点并附带加密的登录信息（如果用户在用户认证系统中未登录，不引导用户登录，直接返回原站点并附带用户未登陆的信息）
- 原站点将用户信息存入session并判断该用户是否有权限访问站点页面（如果无登录信息则拒绝访问）

对于登录信息的加密，因为共用数据库，跳转认证不会太频繁，暂时也不考虑性能优化，当然是直接使用一次一密，用户授权系统把用户信息对应一个auth_token存入数据库并将auth_token回传代理站点，在代理站点根据auth_token从数据库中获取用户信息。

值得注意的是，用户认证系统可以提供一定时间保存登录状态的功能，但是代理站点应始终将保存登录态的cookie过期时间设为会话结束时。

### 项目安排和进度计划

时间                    |  任务
----------------------- | -----------------------------------
2018.10.24 - 2018.10.28 | 开发环境，登陆注册，系统配置，通用权限
2018.10.29 - 2018.11.4  | 站点管理，页面管理，站点权限，页面权限
2018.11.5  - 2018.11.11 | 子账号，用户管理，我的分享，Token管理
2018.11.12 - 2018.11.18 | 授权系统，日志系统，联调发布，项目验收

初始时间安排比较紧凑，加上最近其他任务也比较繁重，**任务推迟时间在一周以内时不需要修改计划**。

### 项目性质变更与项目推迟计划

#### 项目性质变更

原定项目单纯为了项目本身，希望能实现出对应的功能。在实现的过程中，逐渐发现了很多可以优化的地方（虽然就项目本身来说并不是很有必要），希望能够基于本项目学到更多知识，而不纯粹是完成任务。

主要扩充方向有以下几点（后期可能还会加）：

- 重设计数据库，使其更加符合现代数据库设计要求
- 引入Redis，分离出Dao层实现数据库缓存
- 了解并尝试使用分布式系统（难度较高且并不是很适合，选做）
- 分离出用户系统和认证系统，以便其他项目可以复用（选做）

重新设计数据库将会引起已经写好的很多类需要修改甚至重写，但是权衡之下这么做利还是大于弊的，虽然不至于追求完美，但还是希望能够将它尽可能的做好。

#### 项目推迟计划

由于近期任务越来越繁重，尤其是有较多的课程设计，以及接近期末需要准备复习，故将项目推迟到期末考试结束后。

**本项目将于2018年11月15日0时正式暂停，恢复时间暂定为2019年1月16日。**
