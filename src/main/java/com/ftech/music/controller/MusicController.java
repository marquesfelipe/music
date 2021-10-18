package com.ftech.music.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ftech.music.dto.MusicDto;
import com.ftech.music.entity.Music;
import com.ftech.music.form.MusicForm;
import com.ftech.music.repository.MusicRepository;

@RestController
@RequestMapping("/musics")
public class MusicController {

	@Autowired
	private MusicRepository repository;

	@GetMapping
	// @Cacheable(value = "musicsList")
	public Page<MusicDto> findAll(@RequestParam(required = false) String band,
			@PageableDefault(sort = "released", direction = Direction.DESC, page = 0, size = 10) Pageable pagination) {

		if (band == null) {
			Page<Music> musics = repository.findAll(pagination);
			return MusicDto.convert(musics);
		} else {
			Page<Music> musics = repository.findByBand(band, pagination);
			return MusicDto.convert(musics);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<MusicDto> findById(@PathVariable Long id) {
		Optional<Music> Music = repository.findById(id);
		if (Music.isPresent()) {
			return ResponseEntity.ok(new MusicDto(Music.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	// @CacheEvict(value = "musicsList", allEntries = true)
	public ResponseEntity<MusicDto> create(@RequestBody @Valid MusicForm form, UriComponentsBuilder uriBuilder) {
		Music music = Music.builder().lyric(form.getLyric()).name(form.getName())
				.band(form.getBand()).genre(form.getGenre()).released(form.getReleased()).build();
		repository.save(music);

		URI uri = uriBuilder.path("/musics/{id}").buildAndExpand(music.getId()).toUri();
		return ResponseEntity.created(uri).body(new MusicDto(music));
	}

	@PutMapping("/{id}")
	@Transactional
	// @CacheEvict(value = "musicsList", allEntries = true)
	public ResponseEntity<MusicDto> atualizar(@PathVariable Long id, @RequestBody @Valid MusicForm form) {
		Optional<Music> optional = repository.findById(id);
		if (optional.isPresent()) {
			Music Music = form.update(id, repository);
			return ResponseEntity.ok(new MusicDto(Music));
		}
		return ResponseEntity.notFound().build();
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	@Transactional
	// @CacheEvict(value = "musicsList", allEntries = true)
	public ResponseEntity delete(@PathVariable Long id) {
		Optional<Music> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
