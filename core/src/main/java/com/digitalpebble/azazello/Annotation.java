package com.digitalpebble.azazello;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of an Annotation. Has a type , metadata and start and end
 * offsets referring to the position in the text of a @class BehemothDocument.
 **/
public class Annotation implements Comparable<Annotation> {

	private String type;

	private int start;

	private int end;

	private Map<String, String> features;

	public Annotation() {
		type = "";
		start = -1;
		end = -1;
	}

	public int getFeatureNum() {
		if (this.getFeatures() == null)
			return 0;
		return this.getFeatures().size();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public Map<String, String> getFeatures() {
		if (features == null)
			features = new HashMap<String, String>();
		return features;
	}

	public void setFeatures(Map<String, String> features) {
		this.features = features;
	}

	// sort by start offset then type
	public int compareTo(Annotation target) {
		long diff = this.start - target.start;
		if (diff != 0)
			return (int) diff;
		diff = this.type.compareTo(target.type);
		if (diff != 0)
			return (int) diff;
		diff = this.end - target.end;
		if (diff != 0)
			return (int) diff;
		// eventually compare based on the features
		diff = this.getFeatureNum() - target.getFeatureNum();
		if (diff != 0)
			return (int) diff;
		// TODO compare the features one by one?
		return 0;
	}

	/** Returns a String representation of the Annotation **/
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.type).append("\t").append(start).append("\t")
				.append(end);
		if (features != null) {
			Iterator<String> keysiter = features.keySet().iterator();
			while (keysiter.hasNext()) {
				String key = keysiter.next();
				String value = features.get(key).toString();
				buffer.append("\t").append(key).append("=").append(value);
			}
		}
		return buffer.toString();
	}

}
