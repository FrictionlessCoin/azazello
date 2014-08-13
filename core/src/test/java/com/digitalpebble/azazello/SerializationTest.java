/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.digitalpebble.azazello;

import java.io.IOException;
import java.util.HashMap;

import junit.framework.TestCase;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializationTest extends TestCase {

    private Kryo kryo;

    @Override
    protected void setUp() throws Exception {
        kryo = new Kryo();

        DocumentSerializer serializer = new DocumentSerializer();

        kryo.addDefaultSerializer(Document.class, serializer);
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testSerialization() throws IOException {
        Document doc = new Document();
        doc.setUri("test");
        String tcontent = "This is home";
        doc.setBinaryContent(tcontent.getBytes());
        doc.setText(tcontent);

        HashMap<String, String[]> metadata = new HashMap<String, String[]>();
        doc.setMetadata(metadata);
        metadata.put("testKey", new String[] { "testValue" });

        // doc.setContentType("txt");
        Annotation annot = new Annotation();
        annot.setStart(0);
        annot.setEnd(12);
        annot.setType("annotType");
        doc.getAnnotations(true).add(annot);

        // serialize

        byte[] byteArray = new byte[100000];
        Output output = new Output(byteArray);
        kryo.writeObject(output, doc);
        output.close();

        // deserialize

        Input input = new Input(byteArray);
        Document document2 = kryo.readObject(input, Document.class);
        input.close();

        // compare
        assertEquals(doc.getUri(), document2.getUri());
        assertEquals(doc.getText(), document2.getText());
        assertEquals(doc.getBinaryContent().length, document2.getBinaryContent().length);
        // compare metadata and annotations
        assertEquals(doc.getMetadata().size(), document2.getMetadata().size());
        assertEquals(doc.getAnnotations().size(), document2.getAnnotations().size());
    }

    public void testSerialization2() throws IOException {
        Document doc = new Document();
        String tcontent = "This is home";
        doc.setBinaryContent(tcontent.getBytes());

        // serialize

        byte[] byteArray = new byte[100000];
        Output output = new Output(byteArray);
        kryo.writeObject(output, doc);
        output.close();

        // deserialize

        Input input = new Input(byteArray);
        Document document2 = kryo.readObject(input, Document.class);
        input.close();

        // compare
        assertEquals(doc.getUri(), document2.getUri());
        assertEquals(doc.getText(), document2.getText());
        assertEquals(doc.getBinaryContent().length, document2.getBinaryContent().length);
        // compare metadata and annotations
        assertEquals(doc.getMetadata(true).size(), document2.getMetadata(true).size());
        assertEquals(doc.getAnnotations(true).size(), document2.getAnnotations(true).size());
    }
}
