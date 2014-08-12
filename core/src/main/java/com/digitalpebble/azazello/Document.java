package com.digitalpebble.azazello;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Document {

    private String uri;
    private byte[] binaryContent;
    private String text;
    private Map<String, String[]> metadata;
    private List<Annotation> annotations;

    public Document() {

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String[]> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String[]> metadata) {
        this.metadata = metadata;
    }

    public List<Annotation> getAnnotations() {
        return getAnnotations(false);
    }

    public List<Annotation> getAnnotations(boolean create) {
        if (annotations == null && create)
            return new ArrayList<Annotation>();
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

}
