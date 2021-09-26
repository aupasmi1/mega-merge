package com.psmith.merge;

import static com.psmith.merge.Messages.*;

import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.psmith.merge.exception.MergeException;
import com.psmith.merge.model.Sku;

public class MegaMerge implements Merger {

	final String group1 = "A";
	final String group2 = "B";
	final String supplierFile = "suppliers";
	final String catalogFile = "catalog";
	final String barcodesFile = "barcodes";
	
	/**
	 * Operation to merge two groups of files and write the output
	 * to the specified file
	 * 
	 * @param input The fully qualified path to the input folder
	 * @param output The fully qualified path to the output file
	 * @throws MergeException
	 */
	@Override
	public void mergeWrite(String input, String output) throws MergeException {
		writeFile(output, mergeInternal(input));
	}

	/**
	 * Operation to merge two groups of files and write the output
	 * to a String
	 * 
	 * @param input The fully qualified path to the input folder
	 * @return A string containing the merged content
	 * @throws MergeException
	 */
	@Override
	public String merge(String input) throws MergeException {
		return writeString(mergeInternal(input));
	}
	
	private List<Sku> mergeInternal(String input) throws MergeException {
		Map<String, Sku> mapOne = new LinkedHashMap<>();
		Map<String, Sku> mapTwo = new LinkedHashMap<>();
		
		loadList(group1, input, mapOne);
		loadList(group2, input, mapTwo);
		
		List<Sku> mergedProducts = new LinkedList<>();
		List<Sku> listOne = new LinkedList<>(mapOne.values());
		Queue<Sku> listTwo = new LinkedList<>(mapTwo.values());
		for (Sku sku : listOne) {
			if(listTwo.contains(sku)) {
				listTwo.remove(sku);
			} else {
				mergedProducts.add(listTwo.poll());
			}
			mergedProducts.add(sku);
		}
		return mergedProducts;
	}
	
	private void loadList(String group, String input, Map<String, Sku> listOne) throws MergeException {
		loadCatalog(group, input, listOne);
		loadBarcodes(group, input, listOne);
	}

	private void loadCatalog(String group, String input, Map<String, Sku> listOne) throws MergeException {
		try {
			CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(input + "/" + catalogFile + group + ".csv"));
			String[] record;
			while((record = reader.readNext()) != null) {
				String sku = record[0];
				listOne.put(sku, new Sku(sku, record[1], group));
			}
			
		} catch (Exception e) {
			throw new MergeException(CSV_CATALOG_LOAD_ERROR_MSG, e);
		}
	}

	private void loadBarcodes(String group, String input, Map<String, Sku> listOne) throws MergeException {
		try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(input + "/" + barcodesFile + group + ".csv"))) {
			String[] record;
			while((record = reader.readNext()) != null) {
				String sku = record[1];
				listOne.get(sku).getBarcodes().add(record[2]);
			}
			
		} catch (Exception e) {
			throw new MergeException(CSV_BARCODES_LOAD_ERROR_MSG, e);
		}
	}
	
	private void writeFile(String output, List<Sku> results) throws MergeException {
		try (Writer writer = Files.newBufferedWriter(Paths.get(output));
				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, 
						CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
						CSVWriter.DEFAULT_LINE_END);) {
			extracted(results, csvWriter);
			
		} catch (Exception e) {
			throw new MergeException(CSV_FILE_WRITE_ERROR_MSG, e);
		}
	}
	
	private String writeString(List<Sku> results) throws MergeException {
		try (Writer writer = new StringWriter();
				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, 
						CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, 
						CSVWriter.DEFAULT_LINE_END);) {
			extracted(results, csvWriter);
			return writer.toString();
		} catch (Exception e) {
			throw new MergeException(CSV_FILE_WRITE_ERROR_MSG, e);
		}
	}

	private void extracted(List<Sku> results, CSVWriter csvWriter) {
		String[] headerRecord = { "SKU","Description","Source" };
		csvWriter.writeNext(headerRecord);
		for (Sku sku : results) {
			csvWriter.writeNext(new String[] {sku.getId(), sku.getDescription(), sku.getSupplierGroup()});
		}
	}



}
