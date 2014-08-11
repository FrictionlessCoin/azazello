package com.digitalpebble.azazello;

import java.util.Map;
import java.util.Set;

public class BehemothDocument {

	private String uri;
	private byte[] binaryContent;
	private String text;
	private Map<String, String[]> metadata;
	private Set<Annotation> annotations;

	public BehemothDocument() {

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

	public Set<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Set<Annotation> annotations) {
		this.annotations = annotations;
	}

}
