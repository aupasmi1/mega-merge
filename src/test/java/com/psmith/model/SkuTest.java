package com.psmith.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.psmith.merge.model.Sku;

public class SkuTest {
	
	private static final String skuId1 = "aaa-bbb-ccc";
	private static final String skuDesc1 = "Test sku 1";
	private static final String group1 = "A";
	
	private static final String skuId2 = "ddd-eee-fff";
	private static final String skuDesc2 = "Test sku 2";
	
	private static final String barcode1_1 = "barcode_1_1";
	private static final String barcode1_2 = "barcode_1_2";
	
	private static final String barcode2_1 = "barcode_2_1";
	private static final String barcode2_2 = "barcode_2_2";
	
	@Test
	public void testEqualsIdenticalEmptyBarcodes() {
		Sku sku1 = new Sku(skuId1,skuDesc1,group1);
		Sku sku2 = new Sku(skuId1,skuDesc1,group1);
		assertEquals(sku1, sku2);
	}
	
	@Test
	public void testEqualsIdenticalWithBarcodes() {
		Sku sku1 = new Sku(skuId1,skuDesc1,group1);
		sku1.getBarcodes().add(barcode1_1);
		Sku sku2 = new Sku(skuId1,skuDesc1,group1);
		sku2.getBarcodes().add(barcode1_1);
		assertEquals(sku1, sku2);
	}
	
	@Test
	public void testEqualsSingleMatchingBarcode() {
		Sku sku1 = new Sku(skuId1,skuDesc1,group1);
		sku1.getBarcodes().add(barcode1_1);
		Sku sku2 = new Sku(skuId1,skuDesc2,group1);
		sku2.getBarcodes().add(barcode1_2);
		sku2.getBarcodes().add(barcode2_2);
		sku2.getBarcodes().add(barcode2_1);
		sku2.getBarcodes().add(barcode1_1);
		assertEquals(sku1, sku2);
	}
	
	@Test
	public void testEqualsIgnoresGroupEmptyBarcodes() {
		Sku sku1 = new Sku(skuId1,skuDesc1,null);
		Sku sku2 = new Sku(skuId1,skuDesc1,group1);
		assertEquals(sku1, sku2);
	}

	@Test
	public void testNotEqualDifferentDesc() {
		Sku sku1 = new Sku(skuId1,skuDesc1,group1);
		Sku sku2 = new Sku(skuId2,skuDesc2,group1);
		assertNotEquals(sku1, sku2);
	}
	
	@Test
	public void testNotEqualNoMatchingBarcode() {
		Sku sku1 = new Sku(skuId1,skuDesc1,group1);
		sku1.getBarcodes().add(barcode1_1);
		Sku sku2 = new Sku(skuId1,skuDesc2,group1);
		sku2.getBarcodes().add(barcode1_2);
		sku2.getBarcodes().add(barcode2_2);
		sku2.getBarcodes().add(barcode2_1);
		assertNotEquals(sku1, sku2);
	}
}
