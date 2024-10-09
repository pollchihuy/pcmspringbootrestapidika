/*
 Navicat Premium Data Transfer

 Source Server         : java-be-1
 Source Server Type    : MySQL
 Source Server Version : 90001
 Source Host           : localhost:3377
 Source Schema         : ptdika

 Target Server Type    : MySQL
 Target Server Version : 90001
 File Encoding         : 65001

 Date: 08/10/2024 06:41:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for map_akses_menu
-- ----------------------------
DROP TABLE IF EXISTS `map_akses_menu`;
CREATE TABLE `map_akses_menu`  (
                                   `id_akses` bigint NOT NULL,
                                   `id_menu` bigint NOT NULL,
                                   INDEX `FK_MAP_TO_MENU`(`id_menu` ASC) USING BTREE,
                                   INDEX `FK_MAP_TO_AKSES`(`id_akses` ASC) USING BTREE,
                                   CONSTRAINT `FK_MAP_TO_AKSES` FOREIGN KEY (`id_akses`) REFERENCES `mst_akses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                   CONSTRAINT `FK_MAP_TO_MENU` FOREIGN KEY (`id_menu`) REFERENCES `mst_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of map_akses_menu
-- ----------------------------
INSERT INTO `map_akses_menu` VALUES (1, 1);
INSERT INTO `map_akses_menu` VALUES (1, 2);
INSERT INTO `map_akses_menu` VALUES (1, 3);
INSERT INTO `map_akses_menu` VALUES (1, 4);
INSERT INTO `map_akses_menu` VALUES (1, 5);
INSERT INTO `map_akses_menu` VALUES (1, 6);
INSERT INTO `map_akses_menu` VALUES (2, 5);
INSERT INTO `map_akses_menu` VALUES (2, 6);

-- ----------------------------
-- Table structure for mst_akses
-- ----------------------------
DROP TABLE IF EXISTS `mst_akses`;
CREATE TABLE `mst_akses`  (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `nama` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `UK2rb153rhoo7w4n4hjhgqiy2cl`(`nama_akses` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mst_akses
-- ----------------------------
INSERT INTO `mst_akses` VALUES (1, 'ADMIN');
INSERT INTO `mst_akses` VALUES (2, 'MEMBER');

-- ----------------------------
-- Table structure for mst_group_menu
-- ----------------------------
DROP TABLE IF EXISTS `mst_group_menu`;
CREATE TABLE `mst_group_menu`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `UKa7k9qg344pf76x43keiro3j3s`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mst_group_menu
-- ----------------------------
INSERT INTO `mst_group_menu` VALUES (2, 'ARTIKEL');
INSERT INTO `mst_group_menu` VALUES (1, 'USER MANAGEMENT');

-- ----------------------------
-- Table structure for mst_menu
-- ----------------------------
DROP TABLE IF EXISTS `mst_menu`;
CREATE TABLE `mst_menu`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `nama` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `path_menu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `id_group_menu` bigint NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `UK6vuc72wn34feiu9ikup3bfv9g`(`nama` ASC) USING BTREE,
                             UNIQUE INDEX `UK7c0lkupd16ujukd6fvd23b2nh`(`path_menu` ASC) USING BTREE,
                             INDEX `FK_TO_GROUP_MENU`(`id_group_menu` ASC) USING BTREE,
                             CONSTRAINT `FK_TO_GROUP_MENU` FOREIGN KEY (`id_group_menu`) REFERENCES `mst_group_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mst_menu
-- ----------------------------
INSERT INTO `mst_menu` VALUES (1, 'GROUP MENU', '/api/group_menu', 1);
INSERT INTO `mst_menu` VALUES (2, 'MENU', '/api/menu', 1);
INSERT INTO `mst_menu` VALUES (3, 'AKSES', '/api/akses', 1);
INSERT INTO `mst_menu` VALUES (4, 'USER', '/api/user', 1);
INSERT INTO `mst_menu` VALUES (5, 'ARTIKEL-1', '/api/artikel-1', 2);
INSERT INTO `mst_menu` VALUES (6, 'ARTIKEL-2', '/api/artikel-2', 2);

-- ----------------------------
-- Table structure for mst_user
-- ----------------------------
DROP TABLE IF EXISTS `mst_user`;
CREATE TABLE `mst_user`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `alamat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `is_registered` bit(1) NULL DEFAULT NULL,
                             `nama_lengkap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `no_hp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `tanggal_lahir` date NULL DEFAULT NULL,
                             `token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `id_akses` bigint NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `UK65ffsa5kj05mltpta94y3sa6n`(`email` ASC) USING BTREE,
                             UNIQUE INDEX `UKm8idrg9l69t1k435r76onei9t`(`no_hp` ASC) USING BTREE,
                             UNIQUE INDEX `UKi81c6oub2xeeue31evpk9g2cp`(`password` ASC) USING BTREE,
                             UNIQUE INDEX `UK76f08iolrai4n77ba0h98eqoi`(`username` ASC) USING BTREE,
                             INDEX `FK_TO_AKSES`(`id_akses` ASC) USING BTREE,
                             CONSTRAINT `FK_TO_AKSES` FOREIGN KEY (`id_akses`) REFERENCES `mst_akses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mst_user
-- ----------------------------
INSERT INTO `mst_user` VALUES (1, 'BogorBogorBogorBogorBogorBogor', 'admin@gmail.com', b'1', 'Admin Tabacan', '6281211111111', '$2a$11$kkx4Q1IKpUitZCagjA8YWOKEyc3kk42yA.XNt1.mTbVikgV6nqR0S', '1995-12-25', '$2a$11$LI5vSNd9y/guWjGA4GmSg.V58JXhrt40HjSuXfA4JLg6C8jQmwYcC', 'admintabacan', 1);

SET FOREIGN_KEY_CHECKS = 1;
