package com.codeup.springblog.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
// connected to Post class ; auto-magic
    // Long represents the ID
    Post findById(long id);

    void deleteById(long id);
}
