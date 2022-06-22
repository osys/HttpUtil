## 1. HttpClient 说明

基于JDK8实现的发送HTTP请求工具类。包含 get, post, put, delete 协议的请求。即拿即用，没有引入其它 jar 依赖。

使用快捷方便。传入参数、请求头的 key 和 value 都是 String 类型。



## 2. 使用例子

### 2.1 方法参数

1. 请求地址

    ```java
    String URL = "https://www.baidu.com";
    ```

    

2. 请求参数：通过 `HttpClient.Entry` 设置

    ```java
    HttpClient.Entry params = new HttpClient.Entry()
            .setKeyValue("paramKey1", "paramValue1")
            .setKeyValue("paramKey2", "paramValue2")
            .setKeyValue("paramKey3", "paramValue3");
    ```

    

3. 请求头：通过 `HttpClient.Entry` 设置

    ```java
    HttpClient.Entry header = new HttpClient.Entry()
            .setKeyValue(HttpClient.Header.ACCEPT_CHARSET.name(), "utf-8")
            .setKeyValue(HttpClient.Header.CONTENT_TYPE.name(), "application/json; charset=utf-8");
    ```

    请求头中的 Key 可以通过 `HttpClient.Header` 获取。如：`HttpClient.Header.ACCEPT_CHARSET.name()` 的值为 `Accept-Charset`。

    

4. 返回结果，回调接口

    ```java
    HttpClient.OnHttpResult onHttpResult = new HttpClient.OnHttpResult() {
        @Override
        public void onSuccess(String result) {
            System.out.format("请求成功：%s", result);
        }
        @Override
        public void onError(String message) {
            System.out.format("请求失败：%s", message);
        }
    };
    ```



### 2.2 进行请求

> GET

```java
HttpClient.get(URL, params);
HttpClient.get(URL, params, header);
HttpClient.get(URL, params, onHttpResult);
HttpClient.get(URL, params, header, onHttpResult);
```



> POST

```java
HttpClient.post(URL, params);
HttpClient.post(URL, params, header);
HttpClient.post(URL, params, onHttpResult);
HttpClient.post(URL, params, header, onHttpResult);
```



> PUT

```java
HttpClient.put(URL, params);
HttpClient.put(URL, params, header);
HttpClient.put(URL, params, onHttpResult);
HttpClient.put(URL, params, header, onHttpResult);
```



> DELETE

```java
HttpClient.delete(URL, params);
HttpClient.delete(URL, params, header);
HttpClient.delete(URL, params, onHttpResult);
HttpClient.delete(URL, params, header, onHttpResult);
```



> 运行举例

1. 例如 `HttpClient.get(URL, params, header, onHttpResult);` 请求结果：

    ```
    请求成功：<!DOCTYPE html>
    <!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> ..............
    ```

    

2. 例如 `HttpClient.get(URL, params);` ：

    * Input:

        ```java
        String result = HttpClient.get(URL, params);
        System.out.println(result);
        ```

    * Output:

        ```
        <!DOCTYPE html>
        <!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=https://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/bdorz/baidu.min.css><title>百度一下，你就知道</title></head> ..............
        ```

        

3. 完整例子

    ```java
    public class HttpTest {
        public static void main(String[] args) {
            // 请求地址
            String URL = "https://www.baidu.com";
            
            // 请求参数
            HttpClient.Entry params = new HttpClient.Entry()
                    .setKeyValue("paramKey1", "paramValue1")
                    .setKeyValue("paramKey2", "paramValue2")
                    .setKeyValue("paramKey3", "paramValue3");
            
            // 请求头
            HttpClient.Entry header = new HttpClient.Entry()
                    .setKeyValue(HttpClient.Header.ACCEPT_CHARSET.name(), "utf-8")
                    .setKeyValue(HttpClient.Header.CONTENT_TYPE.name(), "application/json; charset=utf-8");
            
            // 返回结果，回调接口
            HttpClient.OnHttpResult onHttpResult = new HttpClient.OnHttpResult() {
                @Override
                public void onSuccess(String result) {
                    System.out.format("请求成功：%s", result);
                }
                @Override
                public void onError(String message) {
                    System.out.format("请求失败：%s", message);
                }
            };
            
            // 发送请求
            HttpClient.post(URL, params, header, onHttpResult);
        }
    }
    ```

    
