package com.digitalpebble.azazello;

import java.util.HashMap;
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
        doc.setUri(input.readString());
        int length = input.readInt();
        doc.setBinaryContent(input.readBytes(length));
        doc.setText(input.readString());
        int md = input.readInt();
        HashMap<String, String[]> mdata = new HashMap<String, String[]>(md);
        for (int i = 0; i < md; i++) {
            String key = input.readString();
            int numVals = input.readInt();
            String[] vals = new String[numVals];
            for (int j = 0; j< numVals; j++){
                vals[j]= input.readString();         
            }
            mdata.put(key, vals);
        }
        doc.setMetadata(mdata);

        // TODO read annotations
        
        return doc;
    }

    @Override
    public void write(Kryo kryo, Output output, BehemothDocument doc) {
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

        if (annotSize > 0) {
            Iterator<Annotation> iter = doc.getAnnotations().iterator();
            while (iter.hasNext()) {
                Annotation annotation = iter.next();
                output.writeString(annotation.getType());
                output.writeInt(annotation.getStart());
                output.writeInt(annotation.getEnd());
                output.writeInt(annotation.getFeatureNum());
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
