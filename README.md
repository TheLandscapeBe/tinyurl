 # 短连接生成服务
 ## 如何使用？
 ### 数据库初始化
 数据库初始化脚本 
```sql
CREATE DATABASE `tiny_urldb` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

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
```

增加测试应用
```sql
 INSERT INTO `application` VALUES ('1', '1594708959736', '测试应用', '2020-07-14 14:43:12', '1594708959736');
```

增加测试域名
```sql
INSERT INTO `domain` VALUES ('1', 'e.example.com');
```
### 服务部署
服务数据可以使用jar部署，docker镜像部署
1. 使用jar包部署 
1.1 代码克隆  
 ```shell script
git clone https://github.com/fofcn/tinyurl.git
 ```
1.2 代码打包为jar 
```shell script
cd tinyurl
mvn clean package -Dmaven.test.skip=true
java -jar -Xms128M -Xmx256M tinyurl.jar
```
2. 编译docker镜像部署  
2.1 代码下载
 ```shell script
git clone https://github.com/fofcn/tinyurl.git
 ```
2.2 代码打包为镜像  
 ```shell script
docker login
docker build -t tinyurl .
docker tag tinyurl ${registry}/name/image-name:${tag}
docker push ${registry}/name/image-name:${tag}

#例如在我的环境中执行命令
docker login 
docker build -t tinyurl .
docker tag tinyurl fofcn/tinyurl:v0.2.0
docker push fofcn/tinyurl
 ```
2.3 使用docker镜像部署  （目前docker镜像已经上传到doker hub,直接使用fofcn/tinyurl获取镜像即可）
2.3.1 编写docker-compose.yml   
```yaml
# MAINTAINER: errorfatal89@gmail.com
version: '3'

networks:
  your-network-name:
    driver: bridge

services:
  tinyurl:
    image: fofcn/tinyurl:latest
    restart: always
    ports:
      - "53000:53000"
    
    volumes:
      - /etc/localtime:/etc/localtime:ro
      - /etc/timezone:/etc/timezone:ro
      - /app/applog/tinyurl:/app/applog/tinyurl

    networks:
      your-network-name:
        
    environment:
      - SERVER_PORT=53000
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_URL=jdbc:mysql://localhost:3306/tiny_urldb?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8
      - SPRING_SHARDINGSPHERE_DATASOURCE_MASTER_USERNAME=tinyurl_user
      - SPRING_SHARDINGSPHERE_DATASOUCE_MASTER_PASSWORD=Yy123456.
      - SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_URL=jdbc:mysql://localhost:3306/tiny_urldb?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8
      - SPRING_SHARDINGSPHERE_DATASOURCE_SLAVE0_USERNAME=tinyurl_user
      - SPRING_SHARDINGSPHERE_DATASOUCE_SLAVE0_PASSWORD=Yy123456.
```
2.3.2 docker-compose启动  
```shell script
docker-compose up -d
```

 # 接口列表

1 生成短链接
2 打开短链接 

# 短链接生成算法
1. ID自增算法
ID自增算法目前使用MySQL数据库自增ID实现，所以生成的短连接会表现出顺序增加特点： 
比如：
根据请求顺序，短连接生成会为以下地址：
https://xxx.xx.com/a 
https://xxx.xx.com/b 
https://xxx.xx.com/c 
...
2. snowflake算法 
生成ID后，使用62进制进行编码得到短链接key，一般会获得9位字符串

#算法对比
1 ID自增算法 
目前ID自增算法基于MySQL ID自增算法，不利于扩展，适用于小型应用
2 snowflake算法 
snowflake算法为twitter设计，特点ID有序且为数字，适合中大型应用

# 进制算法
## 编码核心计算
1 选取字母表ALPHABET=[0-9a-zA-Z],字母表长度ALPHABET_LENGTH=62，所有使用62进制，

```java
private static String encode(long number) {
        StringBuilder chip = new StringBuilder(9);
        while (number > 0) {
            int mod = (int)(number % ALPHABET_LENGTH);
            chip.append(ALPHABET.charAt(mod));
            number -= mod;
            number /= ALPHABET_LENGTH;
        }

        return chip.reverse().toString();
    }


```

## 解码核心计算
```java
private static long decode(String key) {    
    long number = 0L;   
    for (int i = 0; i < key.length(); i++) {        
        long pow = pow(key, i);        
        number += pow * ALPHABET.indexOf(key.charAt(i));    
    }    
    return number;
}

private static long pow(String key, int i) {   
    long pow = 1L;   
    for (int j = 0; j < key.length() - i - 1; j++) {        
        pow *= ALPHABET_LENGTH;    
    }    
    return pow;
}
```

# 系统扩展方向（目前正在设计规划）
1. 存储扩展
短连接服务设计存在应用概念，每个使用短连接服务的客户端称为应用，不同应用在使用短连接服务前必须申请应用ID和应用密钥，短连接服务会为每个应用生成对应的存储表，以达到分表的目的

2. 性能扩展
使用缓存系统以提高系统性能，目前正在规划redis
