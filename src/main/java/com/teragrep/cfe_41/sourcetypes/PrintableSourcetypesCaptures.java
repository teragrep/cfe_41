package com.teragrep.cfe_41.sourcetypes;

import com.teragrep.cfe_41.capture.Capture;
import com.teragrep.cfe_41.media.Media;
import com.teragrep.cfe_41.target.PrintableCaptures;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrintableSourcetypesCaptures implements PrintableCaptures {
    private final List<Capture> captures;

    public PrintableSourcetypesCaptures() {
        this(new ArrayList<>());
    }

    public PrintableSourcetypesCaptures(final List<Capture> captures) {
        this.captures = captures;
    }

    @Override
    public List<Capture> captures() {
        return captures;
    }

    @Override
    public PrintableCaptures withCapture(final Capture capture) {
        final List<Capture> newCaptures = new ArrayList<>(captures);
        newCaptures.add(capture);
        return new PrintableSourcetypesCaptures(newCaptures);
    }

    @Override
    public Media print(final Media media) {
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (final Capture capture : captures) {
            arrayBuilder.add(
                    Json.createObjectBuilder().add("index", capture.tag()).add("value", capture.source_type())
            );
        }
        return media.with("table", arrayBuilder.build());
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrintableSourcetypesCaptures that = (PrintableSourcetypesCaptures) o;
        return Objects.equals(captures, that.captures);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(captures);
    }
}
