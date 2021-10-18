package com.ftech.music.dto;

import org.springframework.data.domain.Page;

import com.ftech.music.entity.Music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicDto {

	private Long id;
	private String band;
	private String name;
	private String released;
	private String lyric;
	private String genre;

	public MusicDto(Music music) {
		super();
		this.id = music.getId();
		this.band = music.getBand();
		this.name = music.getName();
		this.released = music.getReleased();
		this.lyric = music.getLyric();
		this.genre = music.getGenre();
	}

	public static Page<MusicDto> convert(Page<Music> musics) {
		return musics.map(MusicDto::new);
	}

}
