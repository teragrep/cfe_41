package com.teragrep.cfe_41.sourcetypes;

import com.teragrep.cfe_41.media.JsonMedia;
import com.teragrep.cfe_41.target.PrintableCaptures;
import jakarta.json.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public final class SourcetypesFile {
    private final PrintableCaptures printableCaptures;
    private final String folderPath;
    private final String captureGroupName;

    public SourcetypesFile(final PrintableCaptures printableCaptures, final String captureGroupName) {
        this(printableCaptures, captureGroupName, "");
    }

    public SourcetypesFile(final PrintableCaptures printableCaptures, final String captureGroupName, final String folderPath) {
        this.printableCaptures = printableCaptures;
        this.captureGroupName = captureGroupName;
        this.folderPath = folderPath;
    }

    public void save() throws IOException {
        final JsonObject json = printableCaptures.print(new JsonMedia()).asJson();

        final String filename = captureGroupName.concat("_sourcetypes.json");

        final File file;
        if (folderPath.isEmpty()) {
            file = new File(filename);
        } else {
            file = new File(folderPath, filename);
        }

        Files.write(file.toPath(), json.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourcetypesFile that = (SourcetypesFile) o;
        return Objects.equals(printableCaptures, that.printableCaptures) && Objects.equals(folderPath, that.folderPath) && Objects.equals(captureGroupName, that.captureGroupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(printableCaptures, folderPath, captureGroupName);
    }
}
