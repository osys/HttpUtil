# HttpUtil
基于JDK8实现的发送HTTP请求工具类。包含 get, post, put, delete 协议的请求。

# 使用例子
```java
public class HttpTest {
    public static void main(String[] args) {
        // 请求地址
        String URL = "https://www.baidu.com";
        // 请求参数
        HttpClient.Entry params = new HttpClient
                .Entry()
                .setKeyValue("uuid", "123")
                .setKeyValue("a", "l")
                .setKeyValue("b", "y")
                .setKeyValue("c", "h");
        // 请求头
        HttpClient.Entry header = new HttpClient
                .Entry()
                .setKeyValue(HttpClient.Header.ACCEPT_CHARSET.name(), "utf-8")
                .setKeyValue(HttpClient.Header.CONTENT_TYPE.name(), "application/json; charset=utf-8")
                .setKeyValue(HttpClient.Header.USER_AGENT.name(), "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
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
