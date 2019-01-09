package httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Spider {
    public static void main(String[] args) throws Exception {
        String url = "http://www.wubupua.com/html/7203.html";
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
        CloseableHttpResponse response = aDefault.execute(httpGet);


        HttpEntity entity = response.getEntity();
        String htmlStr = EntityUtils.toString(entity, "UTF-8");


        Document document = Jsoup.parse(htmlStr);
        Elements elements = document.select("img");


        int nameIndex = 1;
        for (Element element : elements) {
            String imgUrl = element.attr("src");
            if(imgUrl.indexOf("upload")>0 ){
                URL url1 = new URL(imgUrl);
                URLConnection urlConnection = url1.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/hasheng/myApplication/spider-test-picture", String.valueOf(nameIndex) + ".jpg"));
                byte[] bytes = new byte[1024];
                int l;
                while ((l = inputStream.read(bytes))!=-1){
                    fileOutputStream.write(bytes,0,l);
                }
                fileOutputStream.close();
                inputStream.close();
                System.out.println("已经将第"+nameIndex+"张图片下载到了本地");
                nameIndex++;
                Thread.sleep(10);
            }
        }
        System.out.printf("所有图片下载完成");
    }


}
