package com.digitalpebble.azazello.tika;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.spark.api.java.function.Function;
import org.apache.tika.Tika;

import com.digitalpebble.azazello.Document;

public class TikaFunction implements
		Function<Document, Document> {

	private static Tika tika;

	static {
		tika = new Tika();
	}

	@Override
	public Document call(Document doc) throws Exception {
		// enrich the behemoth document by adding its text + other metadata
		// obtained from Tika

		String mimeType = tika.detect(doc.getBinaryContent(), doc.getUri());

		InputStream is = new ByteArrayInputStream(doc.getBinaryContent());

		doc.setText(tika.parseToString(is));

		is.close();

		return doc;
	}

}
