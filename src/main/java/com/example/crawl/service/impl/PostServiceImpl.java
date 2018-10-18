package com.example.crawl.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crawl.model.Post;
import com.example.crawl.repository.PostRepository;
import com.example.crawl.service.PostService;
import com.example.crawl.views.PostCrawler;

@Service
public class PostServiceImpl implements PostService {

	private Set<String> links = new HashSet<>();

	@Autowired
	private PostRepository postRepository;

	@Override
	public void crawl(PostCrawler crawler) {
		this.getPageLinks(crawler.getUrl());
		this.getArticles(crawler);
	}

	private void getPageLinks(String url) {

		try {
			Document document = Jsoup.connect(url).get();

			Elements linksOnPage = document.select("a[href^=\"/p/\"]");

			for (Element page : linksOnPage) {
				getPageLinks(page.attr("abs:href"));
				if (!links.contains(page.attr("abs:href"))) {
					links.add(page.attr("abs:href"));
				}
			}
		} catch (IOException e) {
			System.err.println("For '" + url + "': " + e.getMessage());
		}
	}

	public void getArticles(PostCrawler crawler) {
		links.forEach(x -> {
			Document document;

			try {
				document = Jsoup.connect(x).get();
				if (!postRepository.findByLink(x).isPresent()) {
					if (!document.select("article.post-content").first().text().matches("^.*?(java).*$")) {
						Post post = new Post();
						post.setLink(x);
						post.setTitle(document.select(crawler.getTitleClass()).first().text());
						post.setContent(document.select(crawler.getContentClass()).first().text());
						post.setAuthor(document.select(crawler.getAuthorClass()).first().child(0).attr("abs:href"));

						postRepository.save(post);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
	}

}
