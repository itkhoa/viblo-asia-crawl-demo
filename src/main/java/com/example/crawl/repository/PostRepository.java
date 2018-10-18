package com.example.crawl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.crawl.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	public Optional<Post> findByLink(String link);
	
}
