-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1:3306
-- 生成日期： 2018-10-26 14:20:18
-- 服务器版本： 5.7.19
-- PHP 版本： 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `auth_proxy`
--

-- --------------------------------------------------------

--
-- 表的结构 `auth_apply`
--

DROP TABLE IF EXISTS `auth_apply`;
CREATE TABLE IF NOT EXISTS `auth_apply` (
  `apply_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '申请号',
  `site_id` int(10) UNSIGNED DEFAULT NULL COMMENT '申请的站点id',
  `page_id` int(10) UNSIGNED DEFAULT NULL COMMENT '申请的页面id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '申请人的用户id',
  `notify_email` varchar(255) DEFAULT NULL COMMENT '通知邮箱',
  `notify_phone` varchar(255) DEFAULT NULL COMMENT '通知手机号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态：0-未处理，1-已通过，2-已拒绝',
  `handle_user_id` int(10) UNSIGNED DEFAULT NULL COMMENT '处理者用户id',
  `handle_time` timestamp NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`apply_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_global`
--

DROP TABLE IF EXISTS `auth_global`;
CREATE TABLE IF NOT EXISTS `auth_global` (
  `auth_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `auth_key` varchar(255) NOT NULL COMMENT '权限唯一识别key',
  `auth_name` varchar(255) NOT NULL COMMENT '权限名',
  `auth_description` text NOT NULL COMMENT '权限描述',
  `auth_parent` json NOT NULL COMMENT '权限前提',
  `auth_children` json NOT NULL COMMENT '权限包含',
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `auth_key` (`auth_key`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `auth_global`
--

INSERT INTO `auth_global` (`auth_id`, `auth_key`, `auth_name`, `auth_description`, `auth_parent`, `auth_children`) VALUES
(1, 'login', '登陆权限', '若用户没有该权限，则无法登陆', '[]', '[]'),
(2, 'manager', '管理员权限', '管理员权限是很多权限的前提', '[]', '[]'),
(3, 'superuser', '超级管理员权限', '超级管理员权限是敏感权限的前提', '[]', '[2]'),
(4, 'invite', '邀请权限', '拥有该权限的用户可以邀请新用户注册', '[]', '[]'),
(5, 'edit_sites', '全局站点管理权限', '拥有该权限的用户可以全局管理站点（站点的增删查改与授权）', '[2]', '[]'),
(10, 'view_manager', '查看所有管理员权限', '拥有该权限的用户可以查看所有管理员用户', '[3]', '[]'),
(6, 'view_token', '全局Token查看权限', '拥有该权限的用户可以查看所有Token', '[3]', '[]'),
(7, 'edit_token', '全局Token管理权限', '拥有该权限的用户可以查看和修改所有Token', '[3]', '[6]'),
(8, 'view_user', '查看所有用户权限', '拥有该权限的用户可以查看所有普通用户', '[2]', '[]'),
(9, 'edit_user', '管理所有用户权限', '拥有该权限的用户可以查看和修改所有普通用户', '[2]', '[8]'),
(11, 'edit_manager', '管理所有管理员权限', '拥有该权限的用户可以查看和修改所有管理员', '[3]', '[10]'),
(12, 'config', '系统配置权限', '拥有该权限的用户可以配置系统属性', '[3]', '[]');

-- --------------------------------------------------------

--
-- 表的结构 `auth_global_user`
--

DROP TABLE IF EXISTS `auth_global_user`;
CREATE TABLE IF NOT EXISTS `auth_global_user` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `auth_id` int(10) UNSIGNED NOT NULL COMMENT '权限id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_id` (`auth_id`,`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_page`
--

DROP TABLE IF EXISTS `auth_page`;
CREATE TABLE IF NOT EXISTS `auth_page` (
  `page_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '页面id',
  `site_id` int(10) UNSIGNED NOT NULL COMMENT '所属站点id',
  `page_path` varchar(255) NOT NULL COMMENT '页面路径，以/开头',
  `auth_type` int(11) NOT NULL COMMENT '0-默认；1-公开；2-私有',
  PRIMARY KEY (`page_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_page_user`
--

DROP TABLE IF EXISTS `auth_page_user`;
CREATE TABLE IF NOT EXISTS `auth_page_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `page_id` int(10) UNSIGNED NOT NULL COMMENT '页面id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `shareable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可分享',
  `view_count` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `view_count_limit` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '浏览次数限制，默认为0不限制',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_site`
--

DROP TABLE IF EXISTS `auth_site`;
CREATE TABLE IF NOT EXISTS `auth_site` (
  `site_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '站点id',
  `site_name` varchar(255) NOT NULL COMMENT '站点名',
  `site_url` varchar(255) NOT NULL COMMENT '站点URL，末尾不带/，格式：http(s)://site',
  `site_description` text NOT NULL COMMENT '站点描述',
  PRIMARY KEY (`site_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_site_user`
--

DROP TABLE IF EXISTS `auth_site_user`;
CREATE TABLE IF NOT EXISTS `auth_site_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `site_id` int(10) UNSIGNED NOT NULL COMMENT '站点id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `shareable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可分享',
  `manageable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可管理',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_token`
--

DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE IF NOT EXISTS `auth_token` (
  `token_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'token id',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '创建者用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expire_time` timestamp NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`token_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_token_page`
--

DROP TABLE IF EXISTS `auth_token_page`;
CREATE TABLE IF NOT EXISTS `auth_token_page` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `token_id` int(10) UNSIGNED NOT NULL COMMENT 'token id',
  `page_id` int(10) UNSIGNED NOT NULL COMMENT '页面id',
  `view_count` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `view_count_limit` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '浏览次数限制，默认0不限制',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `auth_token_site`
--

DROP TABLE IF EXISTS `auth_token_site`;
CREATE TABLE IF NOT EXISTS `auth_token_site` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `token_id` int(10) UNSIGNED NOT NULL COMMENT 'token id',
  `site_id` int(10) UNSIGNED NOT NULL COMMENT '站点id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `config`
--

DROP TABLE IF EXISTS `config`;
CREATE TABLE IF NOT EXISTS `config` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` text NOT NULL,
  `description` text COMMENT '配置项描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `config`
--

INSERT INTO `config` (`id`, `name`, `value`, `description`) VALUES
(1, 'open_register', 'true', '是否开放注册');

-- --------------------------------------------------------

--
-- 表的结构 `login_auth_token`
--

DROP TABLE IF EXISTS `login_auth_token`;
CREATE TABLE IF NOT EXISTS `login_auth_token` (
  `token_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'token id',
  `token_code` varchar(255) NOT NULL COMMENT 'token code',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `expire_time` timestamp NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `token_code` (`token_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='登录状态token';

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '登录密码',
  `email` varchar(255) DEFAULT NULL COMMENT '用户电子邮箱（可用于找回密码）',
  `phone` varchar(255) DEFAULT NULL COMMENT '用户手机号（可用于找回密码）',
  `inviter_id` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '邀请者用户id',
  `parent_id` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '父帐号用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_invitation`
--

DROP TABLE IF EXISTS `user_invitation`;
CREATE TABLE IF NOT EXISTS `user_invitation` (
  `invite_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '邀请id',
  `invite_code` varchar(255) NOT NULL COMMENT '邀请码',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '邀请者用户id',
  `bind_child` tinyint(1) NOT NULL COMMENT '是否绑定为子账号',
  `auth_global` json NOT NULL COMMENT '授予全局权限',
  `auth_site` json NOT NULL COMMENT '授予页面权限',
  `auth_page` json NOT NULL COMMENT '授予页面权限',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expire_time` timestamp NOT NULL COMMENT '过期时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：0-未使用，1-已使用，2-已取消，3-已过期',
  PRIMARY KEY (`invite_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
