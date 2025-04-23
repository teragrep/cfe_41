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
package com.teragrep.cfe_41.importsql;

import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupAllRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.fakes.*;
import com.teragrep.cfe_41.host.HostRequest;
import com.teragrep.cfe_41.hostGroup.HostGroupRequest;
import com.teragrep.cfe_41.linkage.LinkageRequest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportsqlFileTest {

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(ImportsqlFile.class);
    }

    @Test
    public void testSavingImportSqlFile() {
        final String expectedFileLocation = "src/test/resources/import.sql";

        final LinkageRequest linkageRequestFake = new LinkageRequestFake();
        final HostGroupRequest hostGroupRequestFake = new HostGroupRequestFake();
        final HostRequest hostRequestFake = new HostRequestFake();
        final LinkedHosts linkedHosts = Assertions
                .assertDoesNotThrow(() -> new LinkedHosts(linkageRequestFake, hostGroupRequestFake, hostRequestFake));

        final CaptureRequest captureRequestFake = new CaptureRequestFake();
        final CaptureGroupRequest captureGroupRequestFake = new CaptureGroupRequestFake();
        final LinkedCaptures linkedCaptures = Assertions
                .assertDoesNotThrow(() -> new LinkedCaptures(captureRequestFake, captureGroupRequestFake));

        final CaptureGroupAllRequest captureGroupAllRequestFake = new CaptureGroupAllRequestFake();

        final Importsql importsql = new ImportsqlImpl(captureGroupAllRequestFake, linkedHosts, linkedCaptures);

        final ImportsqlFile importsqlFile = new ImportsqlFile("src/test/resources", importsql);

        Assertions.assertDoesNotThrow(() -> importsqlFile.createFile());

        final String actual = Assertions.assertDoesNotThrow(() -> Files.readString(Paths.get(expectedFileLocation)));

        final String expected = "START TRANSACTION;\n" + "DELETE FROM log_group;\n" + "DELETE FROM stream;\n"
                + "DELETE FROM host;\n" + "INSERT INTO log_group (name) VALUES (\"fake-group1\");\n"
                + "INSERT INTO stream (gid, directory, stream, tag) VALUES ((SELECT id FROM log_group WHERE name = \"fake-group1\"), \"fake-index\", \"fake-sourcetype\", \"fake-tag\");\n"
                + "INSERT INTO host (name, gid) VALUES (\"fake-fqhost\", (SELECT id FROM log_group WHERE name = \"fake-group1\"));\n"
                + "INSERT INTO log_group (name) VALUES (\"fake-group2\");\n"
                + "INSERT INTO stream (gid, directory, stream, tag) VALUES ((SELECT id FROM log_group WHERE name = \"fake-group2\"), \"fake-index\", \"fake-sourcetype\", \"fake-tag\");\n"
                + "INSERT INTO host (name, gid) VALUES (\"fake-fqhost\", (SELECT id FROM log_group WHERE name = \"fake-group2\"));\n"
                + "COMMIT;\n";

        Assertions.assertEquals(expected, actual);
    }

}
