package com.digitalpebble.azazello.tika;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.spark.api.java.function.Function;
import org.apache.tika.Tika;

import com.digitalpebble.azazello.BehemothDocument;

public class TikaFunction implements
		Function<BehemothDocument, BehemothDocument> {

	private static Tika tika;

	static {
		tika = new Tika();
	}

	@Override
	public BehemothDocument call(BehemothDocument doc) throws Exception {
		// enrich the behemoth document by adding its text + other metadata
		// obtained from Tika

		String mimeType = tika.detect(doc.getBinaryContent(), doc.getUri());

		InputStream is = new ByteArrayInputStream(doc.getBinaryContent());

		doc.setText(tika.parseToString(is));

		is.close();

		return doc;
	}

}
