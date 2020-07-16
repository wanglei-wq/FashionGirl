/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.182.182
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.182.182:3306
 Source Schema         : FashionGirl

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 31/10/2019 10:46:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adminUser
-- ----------------------------
DROP TABLE IF EXISTS `adminUser`;
CREATE TABLE `adminUser`  (
  `id` int(11) NOT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `power` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adminUser
-- ----------------------------
INSERT INTO `adminUser` VALUES (1, 'admin', 'admin', 1);
INSERT INTO `adminUser` VALUES (2, '1000', '1000', 2);
INSERT INTO `adminUser` VALUES (3, '10086', '10086', 2);

-- ----------------------------
-- Table structure for alipayorder
-- ----------------------------
DROP TABLE IF EXISTS `alipayorder`;
CREATE TABLE `alipayorder`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `trade_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付宝交易号',
  `total_amount` decimal(65, 0) NOT NULL COMMENT '总金额',
  `user_id` int(11) NOT NULL COMMENT '绑定用户的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alipayorder
-- ----------------------------
INSERT INTO `alipayorder` VALUES (1, '157234589531908056437', '2019102922001475671000233174', 19998, 1);
INSERT INTO `alipayorder` VALUES (2, '157234824320910525309', '2019102922001475671000235978', 998, 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '护肤彩妆');
INSERT INTO `category` VALUES (2, '精品女装');
INSERT INTO `category` VALUES (3, '高档首饰');
INSERT INTO `category` VALUES (4, '潮流女包');

-- ----------------------------
-- Table structure for categorySecond
-- ----------------------------
DROP TABLE IF EXISTS `categorySecond`;
CREATE TABLE `categorySecond`  (
  `csid` int(11) NOT NULL AUTO_INCREMENT,
  `csname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category_cid` int(11) NOT NULL,
  PRIMARY KEY (`csid`) USING BTREE,
  INDEX `category_cid`(`category_cid`) USING BTREE,
  CONSTRAINT `categorySecond_ibfk_1` FOREIGN KEY (`category_cid`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categorySecond
-- ----------------------------
INSERT INTO `categorySecond` VALUES (1, '美容护肤', 1);
INSERT INTO `categorySecond` VALUES (2, '强效保养', 1);
INSERT INTO `categorySecond` VALUES (3, '超值彩妆', 1);
INSERT INTO `categorySecond` VALUES (4, '换季保养', 1);
INSERT INTO `categorySecond` VALUES (5, '初冬羽绒', 2);
INSERT INTO `categorySecond` VALUES (6, '毛绒大衣', 2);
INSERT INTO `categorySecond` VALUES (7, '温暖毛衣', 2);
INSERT INTO `categorySecond` VALUES (8, '精品女鞋', 2);
INSERT INTO `categorySecond` VALUES (9, '女士上装', 2);
INSERT INTO `categorySecond` VALUES (10, '浪漫裙装', 2);
INSERT INTO `categorySecond` VALUES (11, '女士下装', 2);
INSERT INTO `categorySecond` VALUES (12, '钻戒', 3);
INSERT INTO `categorySecond` VALUES (13, '项链', 3);
INSERT INTO `categorySecond` VALUES (14, '手镯', 3);
INSERT INTO `categorySecond` VALUES (15, '耳环', 3);
INSERT INTO `categorySecond` VALUES (16, '雀钗', 3);
INSERT INTO `categorySecond` VALUES (17, '手表', 3);
INSERT INTO `categorySecond` VALUES (18, '女士钱包', 4);
INSERT INTO `categorySecond` VALUES (19, '单肩包', 4);
INSERT INTO `categorySecond` VALUES (20, '斜挎包', 4);
INSERT INTO `categorySecond` VALUES (21, '手提包', 4);
INSERT INTO `categorySecond` VALUES (22, '化妆包', 4);
INSERT INTO `categorySecond` VALUES (23, '古驰GUCCI ', 4);
INSERT INTO `categorySecond` VALUES (24, '啄木鸟', 4);
INSERT INTO `categorySecond` VALUES (25, '千姿百袋', 4);
INSERT INTO `categorySecond` VALUES (26, '伊米妮', 4);
INSERT INTO `categorySecond` VALUES (27, '稻草人', 4);
INSERT INTO `categorySecond` VALUES (28, '纽芝兰', 4);

-- ----------------------------
-- Table structure for evaluate
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate`  (
  `eid` int(11) NOT NULL,
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `p_pid` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  PRIMARY KEY (`eid`) USING BTREE,
  INDEX `p_pid`(`p_pid`) USING BTREE,
  INDEX `u_id`(`u_id`) USING BTREE,
  CONSTRAINT `evaluate_ibfk_1` FOREIGN KEY (`p_pid`) REFERENCES `products` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `evaluate_ibfk_2` FOREIGN KEY (`u_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for face
-- ----------------------------
DROP TABLE IF EXISTS `face`;
CREATE TABLE `face`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `faceid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of face
-- ----------------------------
INSERT INTO `face` VALUES (2, '1234657879', 0);
INSERT INTO `face` VALUES (3, '2b9b2c2bb8d9e060adb4075a17ca1dc458d10592a46832', 0);

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `itemid` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL,
  `subtotal` decimal(65, 0) NOT NULL,
  `p_pid` int(11) NOT NULL,
  `o_oid` int(11) NOT NULL,
  PRIMARY KEY (`itemid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES (1, 1, 19998, 2, 1);
INSERT INTO `orderitem` VALUES (2, 1, 998, 4, 2);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `money` double(50, 0) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ordertime` datetime(0) NULL,
  `state` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '23464754612346', '张先生', '17349839232', 19998, '河北省武安市武安镇南小河村', '2019-10-29 16:23:10', 1, 1, 1);
INSERT INTO `orders` VALUES (2, '66997876646424', '刘女士', '15233892325', 666, '北京市大兴区', '2019-10-17 19:19:40', 1, 2, 2);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `market_price` double(20, 2) NULL DEFAULT NULL,
  `shop_price` double(20, 2) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pdate` datetime(0) NULL DEFAULT NULL,
  `pnum` int(11) NULL DEFAULT NULL,
  `cid` int(11) NOT NULL,
  `csid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, '洗面奶', 66.00, 39.00, NULL, '专属你的洗面奶，洗出你的美', '2019-10-22 11:49:19', 99, 1, 1, 1);
INSERT INTO `products` VALUES (2, '补水套装', 199.00, 99.00, NULL, '你的肌肤缺水了吗，来一套补补水', '2019-10-10 11:50:57', 30, 1, 4, 1);
INSERT INTO `products` VALUES (3, '貂皮大衣', 19998.00, 6668.00, NULL, '整貂皮大衣外套中长款收腰立领长袖进口真皮草裘皮', '2019-10-10 11:52:02', 10, 2, 6, 1);
INSERT INTO `products` VALUES (4, '韩版毛呢外套女韩范秋冬装厚中长款冬季呢子', 1988.00, 998.00, NULL, '今年韩国首尔爆款，超高端定制羊毛呢大衣，版型超美，不挑身材，不挑年龄，上身非常漂亮哦，适合任何场合，这个秋冬MM必备款哦。几乎人手一件，美丽时尚的你怎么能少了这件呢，现秋冬预热加十一到来，只需99元包邮，只限今天，今天过后马上涨价，早买早划算哦~', '2019-10-03 11:56:10', 66, 2, 8, 1);
INSERT INTO `products` VALUES (5, '蓝玛瑙钻戒', 159999.00, 128888.00, NULL, '美国专属蓝宝石，一个用户只能定制一次，送给你的爱人，蓝玛瑙，你值得拥有', '2019-10-10 11:58:33', 88, 3, 12, 1);
INSERT INTO `products` VALUES (6, '红宝石项链', 78998.00, 68886.00, NULL, '红宝石项链，由一颗颗属于中国内蒙生产，颗颗晶莹剔透，闪耀你的美', '2019-10-09 12:01:09', 11, 3, 13, 1);
INSERT INTO `products` VALUES (7, '纽芝兰', 6666.00, 2899.00, NULL, 'NUCELLE-纽芝兰是源自于美国时尚文化的新锐箱包品牌，它不断从古典式的美国传统中获取灵感，并融合当今美式潮流，演绎着经典与流行的完美平衡。 NUCELLE-纽芝兰倡导气质天成、时尚典雅的设计理念，集结顶级时尚箱包品牌经典款式之精华，精选高品质的面料，努力汲取欧美每一季的流行元素并融入到NUCELLE品牌的设计中。她与当下年轻女性追求的自信而不张扬、妩媚而不乏独立的生活态度完美结合，成为众多时尚女性的娇宠。', '2019-10-10 12:02:09', 19, 4, 28, 1);
INSERT INTO `products` VALUES (8, '千姿百袋', 19999.00, 13899.00, NULL, '千姿百袋，网络时尚女包品牌，品牌名称来自“千姿百袋”每一个字的第一个拼音字母Q、Z、B、D，喻比千姿百袋女包时尚、优雅简约、多姿，“千姿百袋”形容包包款式多种多样和种类十分丰富，意在改变传统女性一包背到底的老旧观念，倡导包包也可以琢衣服一样绰约多姿、生动而包罗万象，与不同款式的衣服合理搭配，给女性带来全新的气质诠释。', '2019-09-10 12:03:34', 6, 4, 25, 1);
INSERT INTO `products` VALUES (9, '去角质', 111.00, 111.00, NULL, '专属你的洗面奶，洗出你的美', '2019-10-26 16:18:34', 88, 1, 1, 1);
INSERT INTO `products` VALUES (10, '护发素', 22.00, 11.00, NULL, '洗发水', '2019-10-26 16:19:22', 11, 1, 1, 1);
INSERT INTO `products` VALUES (11, '面霜', 44.00, 22.00, NULL, '面霜', '2019-10-16 18:53:30', 66, 1, 2, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT 0,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `faceid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '张先生', '男', '18866888866', 1, '北京市大兴区', 'cc261919@163.com', NULL);
INSERT INTO `user` VALUES (2, '1000', '1000', '刘女士', '女', '13699468894', 1, '北京市朝阳区', '33669955@qq.com', NULL);
INSERT INTO `user` VALUES (3, '123456', '123456', '清清', '女', '16656885499', 0, '河北邯郸市', '778855669@qq.com', NULL);
INSERT INTO `user` VALUES (4, '123456999', '123456', 'aaaa', 'aaa', '15933687954', 0, 'aaaa', 'aaaa', NULL);
INSERT INTO `user` VALUES (5, 'cccc', 'ccccc', 'ccccccc', 'ccccc', '1316446146', 0, 'cccc', 'ccccc', NULL);
INSERT INTO `user` VALUES (6, 'admintt', '123456', 'tt', '女', '18716050938', 0, '北京市', '1961418326@qq.com', NULL);
INSERT INTO `user` VALUES (7, '1046633051', 'liuhongru258046', '柳鸿儒', '男', '18235988818', 0, '北京大兴区', '1046633051@qq.com', NULL);
INSERT INTO `user` VALUES (8, '10086', '10086', '张帅', '男', '15233819232', 0, '北京市', 'cc261919@163.com', NULL);
INSERT INTO `user` VALUES (9, '1111', '1111', '1111', '男', '3164653131', 0, 'gdojhdl', 'cc261919@163.com', NULL);
INSERT INTO `user` VALUES (10, '22222', '1111', '22222', '男', 'fafafaff', 0, '22222', 'cc261919@163.com', NULL);
INSERT INTO `user` VALUES (11, '33333', '3333', '333333', '男', '13465131', 0, '股东会过两年', '1987130622@qq.com', NULL);
INSERT INTO `user` VALUES (29, '111', '333', '111', '111', '15233819232', 0, '北京市大兴区', 'cc261919@163.com', '58fa7d7c9cf587ff27741f3a196e5f5ef0d388c1ed3fa4');
INSERT INTO `user` VALUES (30, 'jjking', '123', '王佳俊', '男', '18273727827', 0, '健康的减肥开始复苏', '188123231312@163.com', '51ea32d34893cb3705c4f7898f493cad468f43a2b8a91b');
INSERT INTO `user` VALUES (31, '99999999', '123', '123', NULL, '17349839232', 0, '北京市大兴区', 'cc261919@163.com', '');
INSERT INTO `user` VALUES (32, '666', '123', 'af;amf;', NULL, '17349839232', 0, '北京市大兴区', 'cc261919@163.com', '');
INSERT INTO `user` VALUES (33, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '3ce3c855aef4bd8f9674ef4a1d0af73dac64d0a38c80ea');
INSERT INTO `user` VALUES (34, '123', '123456', 'aaaa', NULL, '18730084225', 0, '北京市大兴区', '1850161523@qq.com', '');

-- ----------------------------
-- Table structure for user_addr
-- ----------------------------
DROP TABLE IF EXISTS `user_addr`;
CREATE TABLE `user_addr`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人的地址',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人的名字',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人的电话',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '映射user表那个用户创建的收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_addr
-- ----------------------------
INSERT INTO `user_addr` VALUES (1, '北京市大兴区', '', '', NULL);
INSERT INTO `user_addr` VALUES (2, '宿舍', '', '', NULL);
INSERT INTO `user_addr` VALUES (3, '课工场9号楼', '', '', NULL);
INSERT INTO `user_addr` VALUES (4, '中科电商谷6号楼', '', '', NULL);
INSERT INTO `user_addr` VALUES (5, '胡夫金字塔顶端', '张三', '12222333456', 1);
INSERT INTO `user_addr` VALUES (6, '胡夫金字塔顶端', '张三', '12222333456', 1);

SET FOREIGN_KEY_CHECKS = 1;
