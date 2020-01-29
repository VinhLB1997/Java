package crawl.music.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import crawl.music.entity.Song;
import crawl.music.utils.HibernateUtil;

public class CrawlService {
	public List<Song> getAllSong() {
		List<Song> listSong = new ArrayList<Song>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		listSong = session.createQuery("from Song", Song.class).list();
		return listSong;
	}

	public boolean insertSong(List<Song> listSongs) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			for (Song song : listSongs) {
				session.save(song);
			}
			session.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
//			session.flush();
			session.close();
		}
		return result;
	}
}
