package org.task2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"date", "copyright", "hdurl", "media_type", "service_version", "title"})
public class Content {

    private final String explanation;
    private final String url;

    public Content(@JsonProperty("explanation") String explanation, @JsonProperty("url") String url) {
        this.explanation = explanation;
        this.url = url;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Content " +
                "\n explanation='" + explanation + '\'' +
                "\n url='" + url + '\'';
    }
}
