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
package com.teragrep.cfe_41.media;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SQLMediaTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(SQLMedia.class).verify();
    }

    @Test
    public void sqlMediaStreamTest() {
        SQLStatementMedia actualMedia = new SQLMedia();
        actualMedia = actualMedia.withStream("group1", "index1", "source1", "tag1");

        List<String> expected = new ArrayList<>();
        expected.add("START TRANSACTION;");
        expected.add("DELETE FROM log_group;");
        expected.add("DELETE FROM stream;");
        expected.add("DELETE FROM host;");
        expected
                .add(
                        "INSERT INTO stream (gid, directory, stream, tag) VALUES ((SELECT id FROM log_group WHERE name = \"group1\"), \"index1\", \"source1\", \"tag1\");"
                );
        expected.add("COMMIT;");
        List<String> actual = actualMedia.asQueries();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sqlMediaLogGroupTest() {
        SQLStatementMedia actualMedia = new SQLMedia();
        actualMedia = actualMedia.withLogGroup("group1");
        List<String> expected = new ArrayList<>();
        expected.add("START TRANSACTION;");
        expected.add("DELETE FROM log_group;");
        expected.add("DELETE FROM stream;");
        expected.add("DELETE FROM host;");
        expected.add("INSERT INTO log_group (name) VALUES (\"group1\");");
        expected.add("COMMIT;");
        List<String> actual = actualMedia.asQueries();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sqlMediaHostTest() {
        SQLStatementMedia actualMedia = new SQLMedia();
        actualMedia = actualMedia.withHost("host1", "group1");
        List<String> expected = new ArrayList<>();
        expected.add("START TRANSACTION;");
        expected.add("DELETE FROM log_group;");
        expected.add("DELETE FROM stream;");
        expected.add("DELETE FROM host;");
        expected
                .add(
                        "INSERT INTO host (name, gid) VALUES (\"host1\", (SELECT id FROM log_group WHERE name = \"group1\"));"
                );
        expected.add("COMMIT;");
        List<String> actual = actualMedia.asQueries();
        Assertions.assertEquals(expected, actual);
    }

}
