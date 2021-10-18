package com.ftech.music.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftech.music.entity.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

	Page<Music> findByBand(String band, Pageable pagination);}
