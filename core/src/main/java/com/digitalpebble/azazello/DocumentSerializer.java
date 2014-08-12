package com.digitalpebble.azazello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class DocumentSerializer extends Serializer<Document> {

    @Override
    public Document read(Kryo kryo, Input input,
            Class<Document> docClass) {
        Document doc = new Document();
        doc.setUri(input.readString());
        int length = input.readInt();
        doc.setBinaryContent(input.readBytes(length));
        doc.setText(input.readString());
        int md = input.readInt();
        HashMap<String, String[]> mdata = new HashMap<String, String[]>(md);
        for (int i = 0; i < md; i++) {
            String key = input.readString();
            int numVals = input.readShortUnsigned();
            String[] vals = new String[numVals];
            for (int j = 0; j < numVals; j++) {
                vals[j] = input.readString();
            }
            mdata.put(key, vals);
        }
        doc.setMetadata(mdata);

        // read annotations
        int annotSize = input.readInt();
        List<Annotation> annotations = new ArrayList<Annotation>(annotSize);
        doc.setAnnotations(annotations);

        for (int i = 0; i < annotSize; i++) {
            Annotation annot = new Annotation();
            annot.setType(input.readString());
            annot.setStart(input.readInt());
            annot.setEnd(input.readInt());
            int featureNum = input.readByteUnsigned();
            Map<String, String> features = new HashMap<String, String>(
                    featureNum);
            for (int j = 0; j < featureNum; j++) {
                String key = input.readString();
                String value = input.readString();
                features.put(key, value);
            }
            if (featureNum > 0)
                annot.setFeatures(features);
            annotations.add(annot);
        }

        return doc;
    }

    @Override
    public void write(Kryo kryo, Output output, Document doc) {
        output.writeString(doc.getUri());

        byte[] content = doc.getBinaryContent();
        int length = 0;
        if (content != null)
            length = content.length;
        output.writeInt(length);
        output.write(content);

        output.writeString(doc.getText());

        // metadata
        int mdSize = 0;

        if (doc.getMetadata() != null)
            mdSize = doc.getMetadata().size();

        output.writeInt(mdSize);

        if (mdSize > 0) {
            Iterator<Entry<String, String[]>> iter = doc.getMetadata()
                    .entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, String[]> md = iter.next();
                output.writeString(md.getKey());
                output.writeShort(md.getValue().length);
                for (String val : md.getValue())
                    output.writeString(val);
            }
        }

        // annotations
        int annotSize = 0;

        if (doc.getAnnotations() != null)
            annotSize = doc.getAnnotations().size();
        output.writeInt(annotSize);

        if (annotSize > 0) {
            Iterator<Annotation> iter = doc.getAnnotations().iterator();
            while (iter.hasNext()) {
                Annotation annotation = iter.next();
                output.writeString(annotation.getType());
                output.writeInt(annotation.getStart());
                output.writeInt(annotation.getEnd());
                output.writeShort(annotation.getFeatureNum());
                Iterator<Entry<String, String>> iterfeat = annotation
                        .getFeatures().entrySet().iterator();
                while (iterfeat.hasNext()) {
                    Entry<String, String> feature = iterfeat.next();
                    output.writeString(feature.getKey());
                    output.writeString(feature.getValue());
                }
            }
        }
    }
}
