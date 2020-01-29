package crawl.music.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawl.music.entity.Song;

public class HtmlService {
	public static String[] handleSongAndSingerName(String songAndSinger, String type) {
		String[] arrName = null;
		if (type.equals("link")) {
			arrName = songAndSinger.split("_");
		} else {
			arrName = songAndSinger.split("-");
		}
		return arrName;
	}

	public static List<Song> getNewSongsFromHtml(String url) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document doc = conn.get();
		List<Song> listNewSong = new ArrayList<Song>();
		if (url.contains("top100")) {
			Elements listItemSong = doc.select("ul.list_show_chart li");
			for (int idx = 0; idx < listItemSong.size(); idx++) {
				Element divInfor = listItemSong.get(idx).getElementsByClass("box_info_field").first();
				String[] songAndSinger = HtmlService
						.handleSongAndSingerName(divInfor.getElementsByTag("a").attr("title"), "");
				String nameSong = songAndSinger[0].trim();
				String nameSinger = songAndSinger[1].trim();
				String imageLink = divInfor.select("a").first().getElementsByTag("img").first().attr("data-src");
				Element divLink = listItemSong.get(idx).getElementsByClass("box_song_action").first();
				String[] arrLink = HtmlService.handleSongAndSingerName(divLink.getElementsByTag("a").first().attr("id"),
						"link");
				String link = arrLink[1].trim();
				Song newSong = new Song();
				newSong.setNameSong(nameSong);
				newSong.setNameSinger(nameSinger);
				newSong.setImage(imageLink);
				newSong.setLink(link);
				newSong.setDeleteFlg(0);
				newSong.setCreateAt(new Date());
				newSong.setUpdateAt(new Date());
				listNewSong.add(newSong);
			}
		}
		return listNewSong;
	}
}
