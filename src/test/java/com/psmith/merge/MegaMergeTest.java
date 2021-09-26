package com.psmith.merge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.psmith.merge.exception.MergeException;

public class MegaMergeTest {

	private static final String INPUT_FILE_FOLDER = "src/test/resources/input";
	private static final String OUTPUT_FILE_FOLDER = "src/test/resources/output";

	@Test
	public void testMerge() throws Exception {
		final String lineEnding = "\n";
		Path path = Paths.get(OUTPUT_FILE_FOLDER + "/result_output.csv");
		
		Stream<String> lines = Files.lines(path);
	    
		String expectedCsv = lines.collect(Collectors.joining(lineEnding)) + lineEnding;
	    lines.close();
		
		Merger merger = new MegaMerge();
		String csv = merger.merge(INPUT_FILE_FOLDER);
		assertEquals(expectedCsv, csv);

	}
	
	@Test
	public void testMergeFail() {
		Merger merger = new MegaMerge();
		assertThrows(MergeException.class, () -> merger.merge(OUTPUT_FILE_FOLDER));
	}

}
