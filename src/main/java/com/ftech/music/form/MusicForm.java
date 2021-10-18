package com.ftech.music.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ftech.music.entity.Music;
import com.ftech.music.repository.MusicRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicForm {

	@NotNull
	@NotEmpty
	@Length(min = 2)
	private String band;
	@NotNull
	@NotEmpty
	@Length(min = 2)
	private String name;
	@NotNull
	@NotEmpty
	@Length(min = 2)
	private String released;
	@NotNull
	@NotEmpty
	@Length(min = 2)
	private String lyric;
	@NotNull
	@NotEmpty
	@Length(min = 2)
	private String genre;

	public Music update(Long id, MusicRepository repository) {
		Optional<Music> m = repository.findById(id);
		Music music = m.get();
		music.setBand(this.band);
		music.setGenre(this.genre);
		music.setLyric(this.lyric);
		music.setName(this.name);
		music.setReleased(this.released);
		return music;
	}

}
