package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdRepository extends JpaRepository<Ad, Long> {
    // Repository replaces the DAOs in Java
    // auto-magic comes from JpaRepository<Ad, Long>
    // The Ad is the model, entity table that exists
    // The Long is because of it being the primary key, thus being the fastest pint of reference to the object.

//    @Query("from Ad a where a.id = ?1")
    Ad findById(long id);

    @Query("from Ad a where a.title like %:term%")
    Ad findFirstByTitle(String term);
}
