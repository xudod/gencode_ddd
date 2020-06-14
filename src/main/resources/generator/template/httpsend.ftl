package ${basePackageValue}.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSend {

	
	public static String sendHttpsGet(String urlStr) throws IOException {
		// 1. 获取访问地址URL
		URL url = new URL(urlStr);
		// 2. 创建HttpURLConnection对象
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		/* 3. 设置请求参数等 */
		// 请求方式
		connection.setRequestMethod("GET");
		// 超时时间
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		// 设置是否输出
		connection.setDoOutput(true);
		// 设置是否读入
		connection.setDoInput(true);
		// 设置是否使用缓存
		connection.setUseCaches(false);
		// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
		connection.setInstanceFollowRedirects(true);
		// 连接
		connection.connect();
		return getReturn(connection);
	}
	
	
	public static String sendHttpsPost(String urlStr, String params) throws IOException {
		// 1. 获取访问地址URL
		URL url = new URL(urlStr);
		// 2. 创建HttpURLConnection对象
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		/* 3. 设置请求参数等 */
		// 请求方式
		connection.setRequestMethod("POST");
		// 设置使用标准编码格式编码参数的名-值对
		connection.setRequestProperty("Content-Type","application/json");
		// 超时时间
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		// 设置是否输出
		connection.setDoOutput(true);
		// 设置是否读入
		connection.setDoInput(true);
		// 设置是否使用缓存
		connection.setUseCaches(false);
		// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
		connection.setInstanceFollowRedirects(true);
		// 连接
		connection.connect();
        OutputStream out = connection.getOutputStream();
        out.write(params.getBytes());
        out.flush();
        out.close();
		return getReturn(connection);
	}
	
	/* 请求url获取返回的内容 */
	private static String getReturn(HttpURLConnection connection) throws IOException {
		StringBuffer buffer = new StringBuffer();
		// 将返回的输入流转换成字符串
		try (
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		) {
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			String result = buffer.toString();
			return result;
		}
	}
}
