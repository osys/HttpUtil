import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p><b>{@link HttpClient} Description</b>: 这是一个 Http 请求工具类，是 http 请求方法的一个入口类。
 * 包含 {@code get}, {@code post}, {@code put}, {@code delete} 协议的请求。{@link HttpBase} 是基本逻辑类，
 * 关于请求的基本逻辑在此类中。{@link OnHttpResult} 是一个回调接口，请求成功或者失败后，返回信息传入接口。
 * {@link Header} 是请求头中的key，通过枚举列出这些key，方便获取使用。
 * {@link Entry} 为传入接口数据的实体类，请求头数据，参数数据都是以该实体类为载体。
 * </p>
 *
 * <p>关于 {@link HttpClient} 的使用例子：
 * <pre>
 * {@code
 *
 * public class HttpTest {
 *     public static void main(String[] args) {
 *         // 请求地址
 *         String URL = "https://www.baidu.com";
 *         // 请求参数
 *         HttpClient.Entry params = new HttpClient
 *                 .Entry()
 *                 .setKeyValue("uuid", "lyh")
 *                 .setKeyValue("a", "l")
 *                 .setKeyValue("b", "y")
 *                 .setKeyValue("c", "h");
 *         // 请求头
 *         HttpClient.Entry header = new HttpClient
 *                 .Entry()
 *                 .setKeyValue(HttpClient.Header.ACCEPT_CHARSET.name(), "utf-8")
 *                 .setKeyValue(HttpClient.Header.CONTENT_TYPE.name(), "application/json; charset=utf-8")
 *                 .setKeyValue(HttpClient.Header.USER_AGENT.name(), "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
 *         // 返回结果，回调接口
 *         HttpClient.OnHttpResult onHttpResult = new HttpClient.OnHttpResult() {
 *             @Override
 *             public void onSuccess(String result) {
 *                 System.out.format("请求成功：%s", result);
 *             }
 *             @Override
 *             public void onError(String message) {
 *                 System.out.format("请求失败：%s", message);
 *             }
 *         };
 *         // 发送请求
 *         HttpClient.post(URL, params, header, onHttpResult);
 *     }
 * }
 *
 * }
 * </pre>
 * </p>
 * <p>Create by lyh on 2022/06/17 10:12.</p>
 */
public class HttpClient {

    private static Map<String, String> entryToMap(Entry entry) {
        HashMap<String, String> map = new HashMap<>();
        Entry tep = entry;
        while (entry != null) {
            map.put(entry.key, entry.value);
            entry = entry.next;
        }
        entry = tep;
        return map;
    }

    /**
     * get请求
     * @param url       请求地址
     * @param params    请求参数
     * @return          返回结果
     */
    public static String get(String url, Entry params) {
        return HttpBase.get(url, entryToMap(params));
    }

    /**
     * get请求
     * @param url       请求地址
     * @param params    请求参数
     * @param headers   请求头
     * @return          返回结果
     */
    public static String get(String url, Entry params, Entry headers) {
        return HttpBase.get(url, entryToMap(params), entryToMap(headers));
    }

    /**
     * get请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param onHttpResult  请求回调
     */
    public static void get(String url, Entry params, OnHttpResult onHttpResult) {
        HttpBase.getAsyn(url, entryToMap(params), onHttpResult);
    }

    /**
     * get请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param onHttpResult  请求回调
     */
    public static void get(String url, Entry params, Entry headers, OnHttpResult onHttpResult) {
        HttpBase.getAsyn(url, entryToMap(params), entryToMap(headers), onHttpResult);
    }

    /**
     * post请求
     * @param url       请求地址
     * @param params    请求参数
     * @return          返回结果
     */
    public static String post(String url, Entry params) {
        return HttpBase.post(url, entryToMap(params));
    }

    /**
     * post请求
     * @param url       请求地址
     * @param params    请求参数
     * @param headers   请求头
     * @return          返回结果
     */
    public static String post(String url, Entry params, Entry headers) {
        return HttpBase.post(url, entryToMap(params), entryToMap(headers));
    }

    /**
     * post请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param onHttpResult  请求回调
     */
    public static void post(String url, Entry params, OnHttpResult onHttpResult) {
        HttpBase.postAsyn(url, entryToMap(params), onHttpResult);
    }

    /**
     * post请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param onHttpResult  请求回调
     */
    public static void post(String url, Entry params, Entry headers, OnHttpResult onHttpResult) {
        HttpBase.postAsyn(url, entryToMap(params), entryToMap(headers), onHttpResult);
    }

    /**
     * put请求
     * @param url       请求地址
     * @param params    请求参数
     * @return          返回结果
     */
    public static String put(String url, Entry params) {
        return HttpBase.put(url, entryToMap(params));
    }

    /**
     * put请求
     * @param url       请求地址
     * @param params    请求参数
     * @param headers   请求头
     * @return          返回结果
     */
    public static String put(String url, Entry params, Entry headers) {
        return HttpBase.put(url, entryToMap(params), entryToMap(headers));
    }

    /**
     * put请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param onHttpResult  请求回调
     */
    public static void put(String url, Entry params, OnHttpResult onHttpResult) {
        HttpBase.putAsyn(url, entryToMap(params), onHttpResult);
    }

    /**
     * put请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param onHttpResult  请求回调
     */
    public static void put(String url, Entry params, Entry headers, OnHttpResult onHttpResult) {
        HttpBase.putAsyn(url, entryToMap(params), entryToMap(headers), onHttpResult);
    }

    /**
     * delete请求
     * @param url       请求地址
     * @param params    请求参数
     * @return          返回结果
     */
    public static String delete(String url, Entry params) {
        return HttpBase.delete(url, entryToMap(params));
    }

    /**
     * delete请求
     * @param url       请求地址
     * @param params    请求参数
     * @param headers   请求头
     * @return          返回结果
     */
    public static String delete(String url, Entry params, Entry headers) {
        return HttpBase.delete(url, entryToMap(params), entryToMap(headers));
    }

    /**
     * delete请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param onHttpResult  请求回调
     */
    public static void delete(String url, Entry params, OnHttpResult onHttpResult) {
        HttpBase.deleteAsyn(url, entryToMap(params), onHttpResult);
    }

    /**
     * delete请求，请求结果传递给 OnHttpResult 接口进行处理
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param onHttpResult  请求回调
     */
    public static void delete(String url, Entry params, Entry headers, OnHttpResult onHttpResult) {
        HttpBase.deleteAsyn(url, entryToMap(params), entryToMap(headers), onHttpResult);
    }

    /** http 基础请求类 */
    private static class HttpBase {

        private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

        /**
         * get请求
         * @param url       请求地址
         * @param params    请求参数
         * @return          返回结果
         */
        private static String get(String url, Map<String, String> params) {
            return get(url, params, null);
        }

        /**
         * get请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         * @return          返回结果
         */
        private static String get(String url, Map<String, String> params, Map<String, String> headers) {
            return request(mapToString(url, params, "?"), null, headers, "GET");
        }

        /**
         * get请求
         * @param url           请求地址
         * @param params        请求参数
         * @param onHttpResult  请求回调
         */
        private static void getAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
            getAsyn(url, params, null, onHttpResult);
        }

        /**
         * get请求
         * @param url           请求地址
         * @param params        请求参数
         * @param headers       请求头
         * @param onHttpResult  请求回调
         */
        private static void getAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
            requestAsyn(mapToString(url, params, "?"), null, headers, "GET", onHttpResult);
        }

        /**
         * post请求
         * @param url       请求地址
         * @param params    请求参数
         * @return          返回结果
         */
        private static String post(String url, Map<String, String> params) {
            return post(url, params, null);
        }

        /**
         * post请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         * @return          返回结果
         */
        private static String post(String url, Map<String, String> params, Map<String, String> headers) {
            return request(url, mapToString(null, params, null), headers, "POST");
        }

        /**
         * post请求
         * @param url       请求地址
         * @param params    请求参数
         */
        private static void postAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
            postAsyn(url, params, null, onHttpResult);
        }

        /**
         * post请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         */
        private static void postAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
            requestAsyn(url, mapToString(null, params, null), headers, "POST", onHttpResult);
        }

        /**
         * put请求
         * @param url       请求地址
         * @param params    请求参数
         * @return          返回结果
         */
        private static String put(String url, Map<String, String> params) {
            return put(url, params, null);
        }

        /**
         * put请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         * @return          返回结果
         */
        private static String put(String url, Map<String, String> params, Map<String, String> headers) {
            return request(url, mapToString(null, params, null), headers, "PUT");
        }

        /**
         * put请求
         * @param url       请求地址
         * @param params    请求参数
         */
        private static void putAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
            putAsyn(url, params, null, onHttpResult);
        }

        /**
         * put请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         */
        private static void putAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
            requestAsyn(url, mapToString(null, params, null), headers, "PUT", onHttpResult);
        }

        /**
         * delete请求
         * @param url       请求地址
         * @param params    请求参数
         * @return          返回结果
         */
        private static String delete(String url, Map<String, String> params) {
            return delete(url, params, null);
        }

        /**
         * delete请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         * @return          返回结果
         */
        private static String delete(String url, Map<String, String> params, Map<String, String> headers) {
            return request(mapToString(url, params, "?"), null, headers, "DELETE");
        }

        /**
         * delete请求
         * @param url       请求地址
         * @param params    请求参数
         */
        private static void deleteAsyn(String url, Map<String, String> params, OnHttpResult onHttpResult) {
            deleteAsyn(url, params, null, onHttpResult);
        }

        /**
         * delete请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         */
        private static void deleteAsyn(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
            requestAsyn(mapToString(url, params, "?"), null, headers, "DELETE", onHttpResult);
        }

        /**
         * 表单请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         * @param method    请求方式
         * @return          返回结果
         */
        private static String request(String url, String params, Map<String, String> headers, String method) {
            return request(url, params, headers, method, "application/x-www-form-urlencoded");
        }

        /**
         * http请求
         * @param url       请求地址
         * @param params    请求参数
         * @param headers   请求头
         * @param method    请求方式
         * @param mediaType 参数类型,application/json,application/x-www-form-urlencoded
         * @return          返回结果
         */
        private static String request(String url, String params, Map<String, String> headers, String method, String mediaType) {
            String result = null;
            if (url == null || url.trim().isEmpty()) {
                return null;
            }
            method = method.toUpperCase();
            OutputStreamWriter outputStreamWriter = null;
            InputStream inputStream = null;
            ByteArrayOutputStream resOut = null;
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
                if ("POST".equals(method) || "PUT".equals(method)) {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setUseCaches(false);
                }
                httpURLConnection.setReadTimeout(8000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod(method);
                httpURLConnection.setRequestProperty(Header.ACCEPT_CHARSET.getValue(), "utf-8");
                httpURLConnection.setRequestProperty(Header.CONTENT_TYPE.getValue(), mediaType);

                // 添加请求头
                if (headers != null) {
                    for (String key : headers.keySet()) {
                        httpURLConnection.setRequestProperty(key, headers.get(key));
                    }
                }
                // 添加参数
                if (params != null) {
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
                    outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    outputStreamWriter.write(params);
                    outputStreamWriter.flush();
                }
                // 判断连接状态
                if (httpURLConnection.getResponseCode() >= 300) {
                    throw new RuntimeException("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
                }
                // 获取返回数据
                inputStream = httpURLConnection.getInputStream();
                resOut = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    resOut.write(bytes, 0, len);
                }
                result = resOut.toString();
                // 断开连接
                httpURLConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (resOut != null) {
                    try {
                        resOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStreamWriter != null) {
                    try {
                        outputStreamWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        /**
         * 表单请求
         * @param url           请求地址
         * @param params        请求参数
         * @param headers       请求头
         * @param method        请求方式
         * @param onHttpResult  请求回调
         */
        private static void requestAsyn(String url, String params, Map<String, String> headers, String method, OnHttpResult onHttpResult) {
            requestAsyn(url, params, headers, method, "application/x-www-form-urlencoded", onHttpResult);
        }

        /**
         * http请求
         * @param url           请求地址
         * @param params        求参数
         * @param headers       请求头
         * @param method        请求方式
         * @param mediaType     参数类型,application/json,application/x-www-form-urlencoded
         * @param onHttpResult  请求回调
         */
        private static void requestAsyn(String url, String params, Map<String, String> headers, String method, String mediaType, OnHttpResult onHttpResult) {
            EXECUTOR_SERVICE.submit(() -> {
                try {
                    onHttpResult.onSuccess(request(url, params, headers, method, mediaType));
                } catch (Exception e) {
                    onHttpResult.onError(e.getMessage());
                }
            });
        }

        /** map转成string */
        private static String mapToString(String url, Map<String, String> params, String first) {
            StringBuilder stringBuilder;
            if (url != null) {
                stringBuilder = new StringBuilder(url);
            } else {
                stringBuilder = new StringBuilder();
            }
            if (params != null) {
                boolean isFirst = true;
                for (String key : params.keySet()) {
                    if (isFirst) {
                        if (first != null) {
                            stringBuilder.append(first);
                        }
                        isFirst = false;
                    } else {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(key);
                    stringBuilder.append("=");
                    stringBuilder.append(params.get(key));
                }
            }
            return stringBuilder.toString();
        }

    }

    /** 请求头 */
    public enum Header {
        AUTHORIZATION("Authorization"),
        PROXY_AUTHORIZATION("Proxy-Authorization"),
        DATE("Date"),
        CONNECTION("Connection"),
        MIME_VERSION("MIME-Version"),
        TRAILER("Trailer"),
        TRANSFER_ENCODING("Transfer-Encoding"),
        UPGRADE("Upgrade"),
        VIA("Via"),
        CACHE_CONTROL("Cache-Control"),
        PRAGMA("Pragma"),
        CONTENT_TYPE("Content-Type"),
        HOST("Host"),
        REFERER("Referer"),
        ORIGIN("Origin"),
        USER_AGENT("User-Agent"),
        ACCEPT("Accept"),
        ACCEPT_LANGUAGE("Accept-Language"),
        ACCEPT_ENCODING("Accept-Encoding"),
        ACCEPT_CHARSET("Accept-Charset"),
        COOKIE("Cookie"),
        CONTENT_LENGTH("Content-Length"),
        WWW_AUTHENTICATE("WWW-Authenticate"),
        SET_COOKIE("Set-Cookie"),
        CONTENT_ENCODING("Content-Encoding"),
        CONTENT_DISPOSITION("Content-Disposition"),
        ETAG("ETag"),
        LOCATION("Location");

        private final String value;
        private Header(String value) {
            this.value = value;
        }
        public String getValue() {
            return this.value;
        }
        public String toString() {
            return this.getValue();
        }

    }

    /** 请求回调 */
    public interface OnHttpResult {
        void onSuccess(String result);
        void onError(String message);
    }

    /** 为了方便使用 http 接口，通过 Entry 实体类进行参数传入 */
    public static class Entry {
        private String value;
        private String key;
        private Entry next;
        public boolean hasNext () {
            return next != null;
        }
        public Entry last() {
            Entry tmp = this;
            while (tmp.hasNext()) {
                tmp = tmp.next;
            }
            return tmp;
        }
        public Entry setKeyValue(String key, String value) {
            if (this.key== null || this.key.length() == 0) {
                this.key = key;
                this.value = value;
            } else {
                this.last().next = new Entry().setKeyValue(key, value);
            }
            return this;
        }
        @Override
        public String toString() {
            return "Entry{" +
                    "value='" + value + '\'' +
                    ", key='" + key + '\'' +
                    ", next=" + next +
                    '}';
        }
    }

    /** 这些方法不对外提供，请求参数，请求头传入，使用 map 进行传入 */
    private static String delete(String url, Map<String, String> params) {
        return HttpBase.delete(url, params);
    }
    private static String delete(String url, Map<String, String> params, Map<String, String> headers) {
        return HttpBase.delete(url, params, headers);
    }
    private static void delete(String url, Map<String, String> params, OnHttpResult onHttpResult) {
        HttpBase.deleteAsyn(url, params, onHttpResult);
    }
    private static void delete(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
        HttpBase.deleteAsyn(url, params, headers, onHttpResult);
    }
    private static String get(String url, Map<String, String> params) {
        return HttpBase.get(url, params);
    }
    private static String get(String url, Map<String, String> params, Map<String, String> headers) {
        return HttpBase.get(url, params, headers);
    }
    private static void get(String url, Map<String, String> params, OnHttpResult onHttpResult) {
        HttpBase.getAsyn(url, params, onHttpResult);
    }
    private static void get(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
        HttpBase.getAsyn(url, params, headers, onHttpResult);
    }
    private static String post(String url, Map<String, String> params) {
        return HttpBase.post(url, params);
    }
    private static String post(String url, Map<String, String> params, Map<String, String> headers) {
        return HttpBase.post(url, params, headers);
    }
    private static void post(String url, Map<String, String> params, OnHttpResult onHttpResult) {
        HttpBase.postAsyn(url, params, onHttpResult);
    }
    private static void post(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
        HttpBase.postAsyn(url, params, headers, onHttpResult);
    }
    private static String put(String url, Map<String, String> params) {
        return HttpBase.put(url, params);
    }
    private static String put(String url, Map<String, String> params, Map<String, String> headers) {
        return HttpBase.put(url, params, headers);
    }
    private static void put(String url, Map<String, String> params, OnHttpResult onHttpResult) {
        HttpBase.putAsyn(url, params, onHttpResult);
    }
    private static void put(String url, Map<String, String> params, Map<String, String> headers, OnHttpResult onHttpResult) {
        HttpBase.putAsyn(url, params, headers, onHttpResult);
    }
}
