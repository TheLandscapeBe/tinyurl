CREATE TABLE `application` (
  `id` int NOT NULL AUTO_INCREMENT,
  `access_key` varchar(256) NOT NULL DEFAULT '' COMMENT '密钥',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT '应用名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `app_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '应用ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用';

CREATE TABLE `domain` (
  `id` int NOT NULL AUTO_INCREMENT,
  `domain` varchar(256) NOT NULL DEFAULT '' COMMENT '域名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='域名';

CREATE TABLE `url` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `origin_url` varchar(5000) NOT NULL DEFAULT '' COMMENT '原始URL',
  `hash` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '原始URL MD5哈希值',
  `create_time` datetime NOT NULL,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='URL记录';

INSERT INTO `application` VALUES ('1', '1594708959736', '测试应用', '2020-07-14 14:43:12', '1594708959736');

INSERT INTO `domain` VALUES ('1', 'e.example.com');