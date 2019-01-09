package httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WeChat {
    public static void main(String[] args) throws Exception {
        String url = "http://www.wubupua.com/html/7203.html";
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        CloseableHttpResponse response = aDefault.execute(httpGet);


        HttpEntity entity = response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");

        Document parse = Jsoup.parse(str);
        Elements img = parse.select("img");


        int name = 1;
        for (Element element : img) {
            String src = element.attr("src");
            if (src.indexOf("upload") > 0) {
                URL url1 = new URL(src);
                URLConnection urlConnection = url1.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/hasheng/myApplication/spider-test-picture", String.valueOf(name)) + ".jpg");
                int i;
                byte[] bytes = new byte[1024];
                while ((i = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes,0,i);
                }
                fileOutputStream.close();
                inputStream.close();
                System.out.println("已经将第" + name + "张图片下载到本地");
                name++;
                Thread.sleep(10);
            }

        }
        System.out.printf("所有图片下载完成");
    }

}
