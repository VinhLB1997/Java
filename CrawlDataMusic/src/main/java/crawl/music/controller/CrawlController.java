package crawl.music.controller;

import java.io.IOException;
import java.util.List;

import crawl.music.entity.Song;
import crawl.music.service.CrawlService;
import crawl.music.service.HtmlService;

public class CrawlController {
	public static void main(String[] args) throws IOException {
		CrawlService crawlService = new CrawlService();
		List<Song> listNewSong = HtmlService
				.getNewSongsFromHtml("https://www.nhaccuatui.com/top100/top-100-nhac-tre.m3liaiy6vVsF.html");
		if (crawlService.insertSong(listNewSong)) {
			System.out.println("Insert Success");
		} else {
			System.out.println("Error");
		}
		List<Song> listSong = crawlService.getAllSong();
		for (Song song : listSong) {
			System.out.println(song.getNameSong());
		}
	}
}
