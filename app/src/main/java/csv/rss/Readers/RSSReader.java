package csv.rss.Readers;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import csv.rss.Models.NewsItem;

/**
 * Created by Carl on 2016-03-21.
 */
public class RSSReader {
    private ArrayList<NewsItem> items;

    public RSSReader() {
        items = new ArrayList<NewsItem>();
    }

    public ArrayList<NewsItem> getNewsItems() {
        return items;
    }

    public void parsePage(String url) {
        Feed feed = null;
        try {
            InputStream inputStream = new URL(url).openConnection().getInputStream();
            feed = EarlParser.parseOrThrow(inputStream, 0);
        } catch (XmlPullParserException | IOException | DataFormatException e) {
            e.printStackTrace();
        }
        for (Item item : feed.getItems()) {
            String title = item.getTitle();
            String description = item.getDescription();
            String webPage = item.getLink();
            String content = stripHtml(description);
            String image = extractImgSrc(description);
            items.add(new NewsItem(title, content, webPage, image));
        }
    }

    private String stripHtml(String html) {
        return Jsoup.parse(html).text();
    }

    private String extractImgSrc(String src) {
        String imgRegex = "(?<=<img src=\")[^\"]*";
        Pattern p = Pattern.compile(imgRegex);
        Matcher m = p.matcher(src);
        if (m.find()) {
            return m.group();
        }
        return null;
    }
}
