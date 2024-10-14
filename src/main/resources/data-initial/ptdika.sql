/*
 Navicat Premium Data Transfer

 Source Server         : java-be-1
 Source Server Type    : MySQL
 Source Server Version : 90001
 Source Host           : localhost:3377
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 90001
 File Encoding         : 65001

 Date: 14/10/2024 01:02:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_group_menu
-- ----------------------------
DROP TABLE IF EXISTS `log_group_menu`;
CREATE TABLE `log_group_menu`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `created_at` datetime(6) NOT NULL,
                                   `created_by` bigint NOT NULL,
                                   `flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `id_group_menu` bigint NULL DEFAULT NULL,
                                   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_group_menu
-- ----------------------------

-- ----------------------------
-- Table structure for map_akses_menu
-- ----------------------------
DROP TABLE IF EXISTS `map_akses_menu`;
CREATE TABLE `map_akses_menu`  (
                                   `id_akses` bigint NOT NULL,
                                   `id_menu` bigint NOT NULL,
                                   INDEX `fk_to_map_menu`(`id_menu` ASC) USING BTREE,
                                   INDEX `fk_to_map_akses`(`id_akses` ASC) USING BTREE,
                                   CONSTRAINT `fk_to_map_akses` FOREIGN KEY (`id_akses`) REFERENCES `mst_akses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                   CONSTRAINT `fk_to_map_menu` FOREIGN KEY (`id_menu`) REFERENCES `mst_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of map_akses_menu
-- ----------------------------
INSERT INTO `map_akses_menu` VALUES (1, 1);
INSERT INTO `map_akses_menu` VALUES (1, 2);
INSERT INTO `map_akses_menu` VALUES (1, 3);
INSERT INTO `map_akses_menu` VALUES (1, 4);
INSERT INTO `map_akses_menu` VALUES (1, 5);
INSERT INTO `map_akses_menu` VALUES (1, 6);
INSERT INTO `map_akses_menu` VALUES (1, 7);
INSERT INTO `map_akses_menu` VALUES (1, 8);
INSERT INTO `map_akses_menu` VALUES (1, 9);
INSERT INTO `map_akses_menu` VALUES (1, 10);
INSERT INTO `map_akses_menu` VALUES (2, 5);
INSERT INTO `map_akses_menu` VALUES (2, 6);

-- ----------------------------
-- Table structure for mst_akses
-- ----------------------------
DROP TABLE IF EXISTS `mst_akses`;
CREATE TABLE `mst_akses`  (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `nama` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `UK7vh06dyln4rg7wuslip6xsads`(`nama` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mst_akses
-- ----------------------------
INSERT INTO `mst_akses` VALUES (1, 'ADMIN');
INSERT INTO `mst_akses` VALUES (2, 'MEMBER');

-- ----------------------------
-- Table structure for mst_gorup_menu
-- ----------------------------
DROP TABLE IF EXISTS `mst_gorup_menu`;
CREATE TABLE `mst_gorup_menu`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `created_at` datetime(6) NULL DEFAULT NULL,
                                   `created_by` bigint NOT NULL,
                                   `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `updated_at` datetime(6) NULL DEFAULT NULL,
                                   `updated_by` bigint NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `UKlqoamxym9icfixm2nbna7ekct`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mst_gorup_menu
-- ----------------------------
INSERT INTO `mst_gorup_menu` VALUES (1, NULL, 1, 'USER MANAGEMENT', NULL, NULL);
INSERT INTO `mst_gorup_menu` VALUES (2, NULL, 1, 'ARTIKEL', NULL, NULL);
INSERT INTO `mst_gorup_menu` VALUES (3, NULL, 1, 'DATA-OLAHAN-1', NULL, NULL);
INSERT INTO `mst_gorup_menu` VALUES (4, NULL, 1, 'DATA-OLAHAN-2', NULL, NULL);

-- ----------------------------
-- Table structure for mst_menu
-- ----------------------------
DROP TABLE IF EXISTS `mst_menu`;
CREATE TABLE `mst_menu`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `nama` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `id_group_menu` bigint NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `UKdevynj15ore5d9ra1kpgxaxyk`(`nama` ASC) USING BTREE,
                             UNIQUE INDEX `UKq0lx3118qnxqjcsh9kqrwabq8`(`path` ASC) USING BTREE,
                             INDEX `fk_to_group_menu`(`id_group_menu` ASC) USING BTREE,
                             CONSTRAINT `fk_to_group_menu` FOREIGN KEY (`id_group_menu`) REFERENCES `mst_gorup_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mst_menu
-- ----------------------------
INSERT INTO `mst_menu` VALUES (1, 'GROUP-MENU', '/group-menu', 1);
INSERT INTO `mst_menu` VALUES (2, 'MENU', '/menu', 1);
INSERT INTO `mst_menu` VALUES (3, 'AKSES', '/akses', 1);
INSERT INTO `mst_menu` VALUES (4, 'USER', '/user', 1);
INSERT INTO `mst_menu` VALUES (5, 'ARTIKEL-1', '/artikel-1', 2);
INSERT INTO `mst_menu` VALUES (6, 'ARTIKEL-2', '/artikel-2', 2);
INSERT INTO `mst_menu` VALUES (7, 'DATA-NOMOR', '/data-nomor', 3);
INSERT INTO `mst_menu` VALUES (8, 'DATA-PROFILE', '/data-profile', 3);
INSERT INTO `mst_menu` VALUES (9, 'DATA-TAMBAHAN', '/data-tambahan', 4);
INSERT INTO `mst_menu` VALUES (10, 'DATA-KREDENSIAL', '/data-kredensial', 4);

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
                             INDEX `fk_to_akses`(`id_akses` ASC) USING BTREE,
                             CONSTRAINT `fk_to_akses` FOREIGN KEY (`id_akses`) REFERENCES `mst_akses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mst_user
-- ----------------------------
INSERT INTO `mst_user` VALUES (1, 'BogorBogorBogorBogorBogorBogor', 'admin@gmail.com', b'1', 'Admin Tabacan', '6281211111111', '$2a$11$x0ckuYIUZ0I8K3Qkevd/lulot09e3DmyLRsdEzSo19P6vZ1V1NkaC', '1995-12-25', '$2a$11$DOuqsRo3geagTDCfRPl0seuwz9kRDvVM7syCn.uBm4bfnUB5PmJsO', 'admintabacan', 1);

-- ----------------------------
-- View structure for v_m_contoh
-- ----------------------------
DROP VIEW IF EXISTS `v_m_contoh`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_m_contoh` AS select `mst_contoh`.`id` AS `id`,`mst_contoh`.`contoh_int` AS `contoh_int`,`mst_contoh`.`contoh_double` AS `contoh_double`,`mst_contoh`.`contoh_float` AS `contoh_float`,`mst_contoh`.`contoh_string` AS `contoh_string`,`mst_contoh`.`contoh_date` AS `contoh_date`,`mst_contoh`.`contoh_boolean` AS `contoh_boolean` from `mst_contoh`;

SET FOREIGN_KEY_CHECKS = 1;