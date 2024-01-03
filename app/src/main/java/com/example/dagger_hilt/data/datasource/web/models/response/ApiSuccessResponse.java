package com.example.dagger_hilt.data.datasource.web.models.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public class ApiSuccessResponse<T> extends ApiResponse {

    private final T body;
    private final Map<String, String> links;

    public ApiSuccessResponse(T body, String linkHeader) {
        this.body = body;
        this.links = linkHeader != null ? extractLinks(linkHeader) : new HashMap<>();
    }

    public T getBody() {
        return body;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    private Map<String, String> extractLinks(String header) {
        Map<String, String> links = new HashMap<>();
        Pattern pattern = Pattern.compile("<([^>]*)>\\s*;\\s*rel=\"([a-zA-Z0-9]+)\"");
        Matcher matcher = pattern.matcher(header);

        while (matcher.find()) {
            int count = matcher.groupCount();
            if (count == 2) {
                links.put(matcher.group(2), matcher.group(1));
            }
        }
        return links;
    }

    public Integer getNextPage() {
        Integer nextPage = null;
        if (links.containsKey("next")) {
            String next = links.get("next");
            Pattern pattern = Pattern.compile("\\bpage=(\\d+)");
            assert next != null;
            Matcher matcher = pattern.matcher(next);

            if (matcher.find() && matcher.groupCount() == 1) {
                try {
                    nextPage = Integer.parseInt(Objects.requireNonNull(matcher.group(1)));
                } catch (NumberFormatException ex) {
                    Timber.w("Cannot parse next page from %s", next);
                }
            }
        }
        return nextPage;
    }
}
