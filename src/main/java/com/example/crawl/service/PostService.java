package com.example.crawl.service;

import org.springframework.stereotype.Service;

import com.example.crawl.views.PostCrawler;

@Service
public interface PostService {
	public void crawl(PostCrawler crawler);
}
