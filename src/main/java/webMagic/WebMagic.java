package webMagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class WebMagic implements PageProcessor{

    private Site site = Site.me().setRetrySleepTime(3).setSleepTime(1000);


    @Override
    public void process(Page page) {


        page.putField("author",page.getUrl().regex());
    }
    @Override
    public Site getSite() {
        return null;
    }
}
