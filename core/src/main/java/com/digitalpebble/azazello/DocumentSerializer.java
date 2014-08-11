package com.digitalpebble.azazello;

import java.util.Iterator;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class DocumentSerializer extends Serializer<BehemothDocument> {

	@Override
	public BehemothDocument read(Kryo kryo, Input input,
			Class<BehemothDocument> docClass) {
		BehemothDocument doc = new BehemothDocument();
		return doc;
	}

	@Override
	public void write(Kryo kryo, Output output, BehemothDocument doc) {
		output.writeString(doc.getUri());
		byte[] content = doc.getBinaryContent();
		output.write(content);
		output.writeString(doc.getText());

		// metadata
		int mdSize = 0;

		if (doc.getMetadata() != null)
			mdSize = doc.getMetadata().size();

		if (mdSize > 0) {
			output.writeInt(mdSize);
			Iterator<Entry<String, String[]>> iter = doc.getMetadata()
					.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String[]> md = iter.next();
				output.writeString(md.getKey());
				output.writeInt(md.getValue().length);
				for (String val : md.getValue())
					output.writeString(val);
			}
		}

		// annotations
		int annotSize = 0;

		if (doc.getAnnotations() != null)
			annotSize = doc.getAnnotations().size();
		output.writeInt(annotSize);

		// TODO write the annotations
	}
}
