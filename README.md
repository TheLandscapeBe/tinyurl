 # 接口列表
1 生成短链接
<br/>
2 打开短链接

# 短链接生成算法
1 ID自增算法
<br/>
2 snowflake算法（尚未实现）
生成ID后，使用62进制进行编码得到短链接key

#算法对比
1 ID自增算法 <br/>
目前ID自增算法基于MySQL ID自增算法，不利于扩展，适用于小型应用<br><br/>
2 snowflake算法<br>
snowflake算法为twitter设计，特点ID有序且为数字，适合中大型应用<br/><br/>

# 进制算法
## 编码核心计算
1 选取字母表ALPHABET=[0-9a-zA-Z],字母表长度ALPHABET_LENGTH=62，所有使用62进制，

```java
private String encode(long number) {    
    StringBuilder chip = new StringBuilder(8);    
    while (number > 0) {        
        chip.append(ALPHABET.charAt((int)(number % ALPHABET_LENGTH)));        
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
1. 存储扩展<br/>
短连接服务设计存在应用概念，每个使用短连接服务的客户端称为应用，不同应用在使用短连接服务前必须申请应用ID和应用密钥，短连接服务会为每个应用生成对应的存储表，以达到分表的目的

2. 性能扩展
使用缓存系统以提高系统性能，目前正在规划redis<br/>
