 # 接口列表
1 生成短链接
2 打开短链接

# 短链接生成算法
1 进制算法

# 进制算法
## 编码
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

## 解码
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
