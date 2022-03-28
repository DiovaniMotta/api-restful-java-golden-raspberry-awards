package br.com.diovani.golden.raspberry.awards.services;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import br.com.diovani.golden.raspberry.awards.entities.Movie;
import br.com.diovani.golden.raspberry.awards.entities.Producer;
import br.com.diovani.golden.raspberry.awards.entities.Studio;
import br.com.diovani.golden.raspberry.awards.repositories.MovieRepository;
import br.com.diovani.golden.raspberry.awards.repositories.ProducerRepository;
import br.com.diovani.golden.raspberry.awards.repositories.StudioRepository;

@Service
public class ImportedMovieService {

	private static final char SEPARATOR = ';';

	@Value("${api.restful.golden.raspberry.awards.file.data}")
	private String fileDataCSV;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ProducerRepository producerRepository;

	@Autowired
	private StudioRepository studioRepository;

	private Set<Producer> producersCache = new LinkedHashSet<>();
	private Set<Studio> studiosCache = new LinkedHashSet<>();

	@Transactional
	public void load() throws IOException {
		File csv = ResourceUtils.getFile(fileDataCSV);

		CsvMapper mapper = new CsvMapper();
		CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(SEPARATOR);
		MappingIterator<Movie> readValues = mapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE).//
				readerFor(Movie.class).with(bootstrapSchema).readValues(csv);
		List<Movie> allMovies = readValues.readAll();

		cleanCaches();
		saveMovies(allMovies);
	}

	protected void saveMovies(List<Movie> allMovies) {
		Iterator<Movie> iterator = movieRepository.saveAll(allMovies).iterator();
		while (iterator.hasNext()) {
			Movie movie = iterator.next();
			saveProducers(movie);
			saveStudios(movie);
		}
	}

	protected void saveProducers(Movie movie) {
		Iterator<Producer> it = movie.getProducers().iterator();
		while (it.hasNext()) {
			Producer producer = it.next();
			final String name = producer.getName();
			if (producersCache.contains(producer)) {
				producer = producersCache.stream().filter(ps -> ps.getName().equals(name)).findFirst().get();
				movie.add(producer);
				continue;
			}
			producerRepository.save(producer);
			movie.add(producer);
			producersCache.add(producer);
		}
	}

	protected void saveStudios(Movie movie) {
		Iterator<Studio> it = movie.getStudios().iterator();
		while (it.hasNext()) {
			Studio studio = it.next();
			final String name = studio.getName();
			if (studiosCache.contains(studio)) {
				studio = studiosCache.stream().filter(s -> s.getName().equals(name)).findFirst().get();
				movie.add(studio);
				continue;
			}
			studioRepository.save(studio);
			movie.add(studio);
			studiosCache.add(studio);
		}
	}

	protected void cleanCaches() {
		producersCache.clear();
		studiosCache.clear();
	}
}
