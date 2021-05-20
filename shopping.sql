/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : shopping

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2021-05-19 23:27:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `address`
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地址实体的唯一主键列',
  `u_id` int(11) DEFAULT NULL COMMENT '用户实体的主键属性',
  `a_name` varchar(30) DEFAULT NULL COMMENT '地址的收件人',
  `a_phone` varchar(14) DEFAULT NULL COMMENT '收件人电话',
  `a_detail` varchar(200) DEFAULT NULL COMMENT '收货人详细地址',
  `a_state` int(11) DEFAULT NULL COMMENT '是否是默认地址 0 不是 1是默认地址',
  PRIMARY KEY (`a_id`),
  KEY `FK_u_a_fk` (`u_id`),
  CONSTRAINT `FK_u_a_fk` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='用户个人中心的地址实体，用于存储地址信息';

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', '5', '张伟峰', '13245676843', '广东省广州市番禺区', '0');
INSERT INTO `address` VALUES ('3', '5', 'wilf', '18888888888', '北京市海淀区', '1');
INSERT INTO `address` VALUES ('4', '5', '张三', '13425643487', '广东省肇庆市端州区', '0');
INSERT INTO `address` VALUES ('5', '5', '小神兽', '13535353535', '广东省揭阳市榕城区', '0');
INSERT INTO `address` VALUES ('11', '6', 'mc', '14545454545', '上海市浦东新区', '1');
INSERT INTO `address` VALUES ('12', '6', '小李', '18888888888', '广东省肇庆市端州区黄岗街道肇庆学院主校区', '0');
INSERT INTO `address` VALUES ('13', '9', '武茗', '1963542202', '筋斗花园小区602', '1');
INSERT INTO `address` VALUES ('14', '7', '777', '18218069306', 'ZQXY', '1');
INSERT INTO `address` VALUES ('15', '17', 'wilf', '18888888888', '北京市海淀区', '1');
INSERT INTO `address` VALUES ('16', '17', '张伟峰', '13245676843', '广东省广州市番禺区', '0');
INSERT INTO `address` VALUES ('17', '17', '张三', '13425643487', '广东省肇庆市端州区', '0');

-- ----------------------------
-- Table structure for `cart`
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车的唯一标识',
  `u_id` int(11) DEFAULT NULL COMMENT '用户实体的主键属性',
  `p_id` int(11) DEFAULT NULL COMMENT '商品的唯一主键',
  `c_count` decimal(12,2) DEFAULT NULL COMMENT '购物车小计',
  `c_num` int(11) DEFAULT NULL COMMENT '购物车商品数量',
  PRIMARY KEY (`c_id`),
  KEY `FK_p_c_fk` (`p_id`),
  KEY `FK_u_c_fk` (`u_id`),
  CONSTRAINT `FK_p_c_fk` FOREIGN KEY (`p_id`) REFERENCES `product` (`p_id`),
  CONSTRAINT `FK_u_c_fk` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='购物车实体';

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('19', '6', '36', '5299.00', '1');
INSERT INTO `cart` VALUES ('22', '6', '27', '2299.00', '1');
INSERT INTO `cart` VALUES ('23', '6', '29', '1799.00', '1');
INSERT INTO `cart` VALUES ('24', '5', '28', '2998.00', '2');

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `i_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单项的唯一标识',
  `o_id` varchar(64) DEFAULT NULL COMMENT '订单编号是字符串类型但是也是唯一标识',
  `p_id` int(11) DEFAULT NULL COMMENT '商品的唯一主键',
  `i_count` decimal(12,2) DEFAULT NULL COMMENT '订单项的小计',
  `i_num` int(11) DEFAULT NULL COMMENT '订单项的数量',
  PRIMARY KEY (`i_id`),
  KEY `FK_o_i_fk` (`o_id`),
  KEY `FK_p_i_fk` (`p_id`),
  CONSTRAINT `FK_o_i_fk` FOREIGN KEY (`o_id`) REFERENCES `orders` (`o_id`),
  CONSTRAINT `FK_p_i_fk` FOREIGN KEY (`p_id`) REFERENCES `product` (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='订单内部的具体商品展示项';

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('40', '20201214135412709', '27', '2299.00', '1');
INSERT INTO `item` VALUES ('41', '20201214154112891', '33', '70.00', '1');
INSERT INTO `item` VALUES ('42', '20201214154112891', '28', '2998.00', '2');
INSERT INTO `item` VALUES ('43', '20201214154714115', '36', '5299.00', '1');
INSERT INTO `item` VALUES ('44', '20201214163930157', '28', '7495.00', '5');
INSERT INTO `item` VALUES ('45', '20201214163930157', '36', '26495.00', '5');
INSERT INTO `item` VALUES ('46', '20201214163930157', '30', '9996.00', '4');
INSERT INTO `item` VALUES ('47', '20201215164122020', '27', '2299.00', '1');
INSERT INTO `item` VALUES ('48', '20201215164122020', '33', '70.00', '1');
INSERT INTO `item` VALUES ('49', '20210519223402529', '30', '2499.00', '1');
INSERT INTO `item` VALUES ('50', '20210519232454368', '36', '5299.00', '1');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `o_id` varchar(64) NOT NULL COMMENT '订单编号是字符串类型但是也是唯一标识',
  `u_id` int(11) DEFAULT NULL COMMENT '用户实体的主键属性',
  `a_id` int(11) DEFAULT NULL COMMENT '地址实体的唯一主键列',
  `o_count` decimal(12,2) DEFAULT NULL COMMENT '订单的总金额',
  `o_time` datetime DEFAULT NULL COMMENT '订单的详细时间',
  `o_state` int(11) DEFAULT NULL COMMENT '订单状态 0 未付款，1已经付款未发货 2发货待收货 3 收货待评价 4订单完成 5 退货状态',
  PRIMARY KEY (`o_id`),
  KEY `FK_a_o_fk` (`a_id`),
  KEY `FK_u_o_fk` (`u_id`),
  CONSTRAINT `FK_a_o_fk` FOREIGN KEY (`a_id`) REFERENCES `address` (`a_id`),
  CONSTRAINT `FK_u_o_fk` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单模块的订单实体';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('20201214135412709', '5', '4', '2299.00', '2020-12-14 13:54:12', '4');
INSERT INTO `orders` VALUES ('20201214154112891', '6', '12', '3068.00', '2020-12-14 15:41:12', '4');
INSERT INTO `orders` VALUES ('20201214154714115', '5', '1', '5299.00', '2020-12-14 15:47:14', '-1');
INSERT INTO `orders` VALUES ('20201214163930157', '9', '13', '43986.00', '2020-12-14 16:39:30', '4');
INSERT INTO `orders` VALUES ('20201215164122020', '7', '14', '2369.00', '2020-12-15 16:41:22', '3');
INSERT INTO `orders` VALUES ('20210519223402529', '17', '16', '2499.00', '2021-05-19 22:34:02', '2');
INSERT INTO `orders` VALUES ('20210519232454368', '17', '15', '5299.00', '2021-05-19 23:24:54', '1');

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品的唯一主键',
  `t_id` int(11) DEFAULT NULL COMMENT '类别的主键id',
  `p_name` varchar(50) DEFAULT NULL COMMENT '商品的名称',
  `p_time` date DEFAULT NULL COMMENT '商品的上市时间',
  `p_image` varchar(100) DEFAULT NULL COMMENT '商品图片的路径',
  `p_price` decimal(12,2) DEFAULT NULL COMMENT '商品的价格',
  `p_state` int(11) DEFAULT NULL COMMENT '商品的热门指数',
  `p_info` varchar(200) DEFAULT NULL COMMENT '商品的描述',
  PRIMARY KEY (`p_id`),
  KEY `FK_t_p_fk` (`t_id`),
  CONSTRAINT `FK_t_p_fk` FOREIGN KEY (`t_id`) REFERENCES `type` (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='商品模块的商品实体';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('27', '1', '小米5s', '2020-12-14', 'image/liebiao_xiaomi5s.jpg', '2299.00', '5', '如果你爱这个时代的日新月异，你一定也会爱上与你一起探索的全新小米5s。');
INSERT INTO `product` VALUES ('28', '1', '小米5c', '2020-12-14', 'image/liebiao_xiaomi5c.jpg', '1499.00', '3', '小米5c，又轻又薄的拍照手机');
INSERT INTO `product` VALUES ('29', '1', '小米5', '2020-12-14', 'image/liebiao_xiaomi5.jpg', '1799.00', '4', '小米手机5，十余项黑科技，很轻狠快');
INSERT INTO `product` VALUES ('30', '1', '小米6', '2020-12-14', 'image/liebiao_xiaomi6.jpg', '2499.00', '5', '小米 6，变焦双摄，拍人更美');
INSERT INTO `product` VALUES ('31', '2', '小米6手机钢化膜', '2020-12-14', 'image/peijian4.jpg', '12.00', '4', '抗蓝光防爆抗指纹');
INSERT INTO `product` VALUES ('32', '2', '小米5手机壳', '2020-12-14', 'image/peijian2.jpg', '20.00', '4', '小米5手机壳，用心呵护你的手机');
INSERT INTO `product` VALUES ('33', '2', '小米支架式自拍杆', '2020-12-14', 'image/peijian5.jpg', '70.00', '5', '小米支架式自拍杆蓝牙遥控迷你拍照神器\r\n集自拍、三脚架于一身');
INSERT INTO `product` VALUES ('34', '1', '小米10', '2020-12-14', 'image/liebiao_xiaomi10.jpg', '5299.00', '5', '小米10 至尊纪念版，由小米智能工厂打造');
INSERT INTO `product` VALUES ('36', '3', 'RedmiG游戏本', '2020-12-14', 'image/liebiao_RedmiGyouxiben.jpg', '5299.00', '5', 'Redmi G 游戏本全能高手');

-- ----------------------------
-- Table structure for `type`
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别的主键id',
  `t_name` varchar(20) DEFAULT NULL COMMENT '类别的名称',
  `t_info` varchar(200) DEFAULT NULL COMMENT '类别的描述',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商品模块的类别实体';

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES ('1', '手机', '小米手机， 为发烧而生! ');
INSERT INTO `type` VALUES ('2', '配件', '小米手机专用配件， 爱护你的手机! ');
INSERT INTO `type` VALUES ('3', '笔记本', '全新一代小米笔记本');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户实体的主键属性',
  `u_name` varchar(20) NOT NULL COMMENT '用户账号',
  `u_password` varchar(64) NOT NULL COMMENT '用户密码',
  `u_email` varchar(50) NOT NULL COMMENT '用户的邮箱！用于激活使用！',
  `u_sex` varchar(4) DEFAULT NULL COMMENT '用户性别！',
  `u_status` int(11) DEFAULT NULL COMMENT '用户的激活状态 0 未激活 1 激活',
  `u_code` varchar(64) DEFAULT NULL COMMENT '邮件激活码',
  `u_role` int(11) DEFAULT NULL COMMENT '用户 0 管理员 1',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='用户模块的用户实体';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', '1', '1909179312@qq.com', '男', '1', '202012012135439952zx', '1');
INSERT INTO `user` VALUES ('5', '张三1234', '202cb962ac59075b964b07152d234b70', 'zzx10506@163.com', '女', '1', '202012052335339952c0', '0');
INSERT INTO `user` VALUES ('6', '李雷1234', '202cb962ac59075b964b07152d234b70', 'zzx10506@163.com', '男', '1', '202012052359155141fd', '0');
INSERT INTO `user` VALUES ('7', '777', 'fcea920f7412b5da7be0cf42b8c93759', '1246615666@qq.com', '女', '1', '202012090833455683cf', '0');
INSERT INTO `user` VALUES ('8', 'zxl', '202cb962ac59075b964b07152d234b70', '745989704@qq.com', '女', '1', '20201214162627347281', '0');
INSERT INTO `user` VALUES ('9', '123456', 'e10adc3949ba59abbe56e057f20f883e', '862042264@qq.com', '女', '1', '202012141628386291d2', '0');
INSERT INTO `user` VALUES ('10', 'zxl123', '202cb962ac59075b964b07152d234b70', '745989704@qq.com', '女', '0', '2020121416332972122e', '0');
INSERT INTO `user` VALUES ('11', '2018', '202cb962ac59075b964b07152d234b70', 'zzx10506@163.com', '女', '0', '202012142137556882bd', '0');
INSERT INTO `user` VALUES ('12', '201824121145', '202cb962ac59075b964b07152d234b70', 'zzx10506@163.com', '女', '1', '202012142150290562ca', '0');
INSERT INTO `user` VALUES ('13', '1234', '202cb962ac59075b964b07152d234b70', '862042264@qq.com', '女', '1', '202012142152482012bd', '0');
INSERT INTO `user` VALUES ('14', '123', 'fcea920f7412b5da7be0cf42b8c93759', '1246615666@qq.com', '女', '0', '20201215163518708ce', '0');
INSERT INTO `user` VALUES ('15', '12', '202cb962ac59075b964b07152d234b70', 'zzx10506@163.com', '女', '1', '20201216082010776168', '0');
INSERT INTO `user` VALUES ('16', '2019', 'fcea920f7412b5da7be0cf42b8c93759', 'zzx10506@163.com', '男', '0', '202105192203133841e2', '0');
INSERT INTO `user` VALUES ('17', '2021', 'fcea920f7412b5da7be0cf42b8c93759', 'zzx10506@163.com', '男', '1', '2021051922073147125d', '0');
