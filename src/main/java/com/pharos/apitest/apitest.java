package com.pharos.apitest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class apitest {
//	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		String basicURL = "https://is.pharos.com:9443/";
		apitest http = new apitest();
		System.out.println("SSL 인증서 무시");
		http.disableSslVerication();

		System.out.println("GET으로 데이터 가져오기");
//		http.sendGet("https://naver.com");

		http.getUserByID(basicURL + "scim2/Users/b388b0dc-a0b4-4172-8f88-b78d2136b138");

		System.out.println("유저 생성(POST)");
		
		JSONParser parser = new JSONParser();
		Reader createJson = new FileReader("C:\\Users\\pharos_1\\Downloads\\apitest\\apitest\\create.json");
		//json 파일 불러와서 파싱
		
		JSONObject createJsonObject = (JSONObject) parser.parse(createJson);
		//파싱한 값을 jsonObject에 담음
//		String createParameters = "";
//
//		http.createUser(basicURL + "scim2/Users/", createJsonObject.toString());
		//object 타입을 문자열로 치환
		
		System.out.println("유저 업데이트(UPDATE)");
		Reader updateJson = new FileReader("C:\\Users\\pharos_1\\Downloads\\apitest\\apitest\\update.json");
		
		JSONObject updateJsonObject = (JSONObject) parser.parse(updateJson);
		http.updateUser(basicURL + "scim2/Users/af2df6d1-5a4d-4a53-8968-ab19779b340e?attributes=familyName&excludedAttributes=employeeNumber", 
				updateJsonObject.toString());
		
		
		System.out.println("유저 삭제(DELETE)");
		http.deleteUser(basicURL + "scim2/Users/af2df6d1-5a4d-4a53-8968-ab19779b340e");
	}

	private void getUserByID(String targetUrl) throws Exception {

		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		String base64 = "Basic YWRtaW46YWRtaW4="; // admin,admin을 base64로 인코딩한 값
		String accept = "application/scim+json"; // application/scim+json 타입만 허용하겠다는 뜻

		con.setRequestMethod("GET"); // GET 방식 명시
		con.setRequestProperty("Authorization", base64);
		con.setRequestProperty("Accept", accept);

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println("HTTP 응답 코드 : " + responseCode);
		System.out.println("HTTP body : " + response.toString());
	}

	private void createUser(String targetUrl, String parameters) throws Exception {
		URL url = new URL(targetUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		String base64 = "Basic YWRtaW46YWRtaW4="; // admin,admin을 base64로 인코딩한 값
		String accept = "application/scim+json"; // application/scim+json 타입만 허용하겠다는 뜻

		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Authorization", base64);
		con.setRequestProperty("Accept", accept);
		con.setRequestProperty("Content-Type", accept);
		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		// 스트림에 parameter의 내용을 반복해서 한글자씩 쌓음
		wr.flush();
		// 스트림 데이터 출력
		wr.close();

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		// URLConnection.getInputStream 메소드로부터 얻어진 InputStream으로부터 직접 데이터를 읽어오는 것이 아니라
		// InputStreamReader를 거쳐 데이터를 읽어온다
		// 바이트 단위로 데이터를 읽기 때문에 아스키 코드를 제외한 한글은 깨져서 나타나기 때문에 꼭 Reader를 통해서 가져온다

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
				
		in.close();
		
		System.out.println("응답코드 " + responseCode);
		System.out.println("HTTP body : " + response.toString());
	}
	
	private void updateUser(String targetUrl, String parameters) throws IOException {
		URL url = new URL(targetUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		String base64 = "Basic YWRtaW46YWRtaW4="; // admin,admin을 base64로 인코딩한 값
		String accept = "application/scim+json"; // application/scim+json 타입만 허용하겠다는 뜻

		con.setRequestMethod("PUT");
//		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Authorization", base64);
		con.setRequestProperty("Accept", accept);
		con.setRequestProperty("Content-Type", accept);
		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		// 스트림에 parameter의 내용을 반복해서 한글자씩 쌓음
		wr.flush();
		// 스트림 데이터 출력
		wr.close();

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
				
		in.close();
		
		System.out.println("응답코드 " + responseCode);
		System.out.println("HTTP body : " + response.toString());
	}
	
	private void deleteUser(String targetUrl) throws Exception {

		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		String base64 = "Basic YWRtaW46YWRtaW4="; 
		String accept = "application/scim+json"; 

		con.setRequestMethod("DELETE");
		con.setRequestProperty("Authorization", base64);
		con.setRequestProperty("Accept", accept);
		con.setRequestProperty("Content-Type", accept);
		con.setDoOutput(true);

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println("HTTP 응답 코드 : " + responseCode);
		System.out.println("HTTP body : " + response.toString());
	}

	// SSL 인증서를 무시하게 만드는 메서드
	private void disableSslVerication() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		}

		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
