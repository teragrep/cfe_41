/*
 * Integration Command-line tool for Teragrep
 * Copyright (C) 2025  Suomen Kanuuna Oy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://github.com/teragrep/teragrep/blob/main/LICENSE>.
 *
 *
 * Additional permission under GNU Affero General Public License version 3
 * section 7
 *
 * If you modify this Program, or any covered work, by linking or combining it
 * with other code, such other code is not for that reason alone subject to any
 * of the requirements of the GNU Affero GPL version 3 as long as this Program
 * is the same Program as licensed from Suomen Kanuuna Oy without any additional
 * modifications.
 *
 * Supplemented terms under GNU Affero General Public License version 3
 * section 7
 *
 * Origin of the software must be attributed to Suomen Kanuuna Oy. Any modified
 * versions must be marked as "Modified version of" The Program.
 *
 * Names of the licensors and authors may not be used for publicity purposes.
 *
 * No rights are granted for use of trade names, trademarks, or service marks
 * which are in The Program if any.
 *
 * Licensee must indemnify licensors and authors for any liability that these
 * contractual assumptions impose on licensors and authors.
 *
 * To the extent this program is licensed as part of the Commercial versions of
 * Teragrep, the applicable Commercial License may apply to this file if you as
 * a licensee so wish it.
 */
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
    private final String directoryPath;
    private final String captureGroupName;

    public SourcetypesFile(final PrintableCaptures printableCaptures, final String captureGroupName) {
        this(printableCaptures, captureGroupName, "");
    }

    public SourcetypesFile(
            final PrintableCaptures printableCaptures,
            final String captureGroupName,
            final String directoryPath
    ) {
        this.printableCaptures = printableCaptures;
        this.captureGroupName = captureGroupName;
        this.directoryPath = directoryPath;
    }

    public void save() throws IOException {
        final JsonObject json = printableCaptures.print(new JsonMedia()).asJson();

        final String filename = captureGroupName.concat("_sourcetypes.json");

        final File file;
        if (directoryPath.isEmpty()) {
            file = new File(filename);
        }
        else {
            file = new File(directoryPath, filename);
        }

        Files.write(file.toPath(), json.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourcetypesFile that = (SourcetypesFile) o;
        return Objects.equals(printableCaptures, that.printableCaptures) && Objects.equals(directoryPath, that.directoryPath)
                && Objects.equals(captureGroupName, that.captureGroupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(printableCaptures, directoryPath, captureGroupName);
    }
}
