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

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupAllRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.fakes.*;
import com.teragrep.cfe_41.host.HostRequest;
import com.teragrep.cfe_41.hostGroup.HostGroupRequest;
import com.teragrep.cfe_41.linkage.LinkageRequest;
import com.teragrep.cfe_41.media.SQLMedia;
import com.teragrep.cnf_01.ArgsConfiguration;
import com.teragrep.cnf_01.Configuration;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImportsqlTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(Importsql.class).verify();
    }

    @Test
    public void importsqlTest() {

        String[] args = new String[] {
                "url=http://localhost:1080", "test=test"
        };
        Configuration cfg = new ArgsConfiguration(args);

        // Create ApiConfig so that request can be made
        ApiConfig apiConfig = Assertions.assertDoesNotThrow(() -> new ApiConfig(cfg.asMap()));

        LinkageRequest linkageRequestFake = new LinkageRequestFake();
        HostGroupRequest hostGroupRequestFake = new HostGroupRequestFake();
        HostRequest hostRequestFake = new HostRequestFake();
        SQLHost sqlHost = Assertions
                .assertDoesNotThrow(() -> new SQLHost(linkageRequestFake, hostGroupRequestFake, hostRequestFake));

        CaptureRequest captureRequestFake = new CaptureRequestFake();
        CaptureGroupRequest captureGroupRequestFake = new CaptureGroupRequestFake();
        SQLCapture sqlCapture = Assertions
                .assertDoesNotThrow(() -> new SQLCapture(captureRequestFake, captureGroupRequestFake));

        CaptureGroupAllRequest captureGroupAllRequestFake = new CaptureGroupAllRequestFake();

        Importsql importsql = new Importsql(captureGroupAllRequestFake, sqlHost, sqlCapture, apiConfig);

        String actual = Assertions.assertDoesNotThrow(() -> importsql.toString());

        SQLMedia expectedSql = new SQLMedia();
        expectedSql.withLogGroup("fake-group1");
        expectedSql.withStream("fake-group1", "fake-index", "fake-sourcetype", "fake-tag");
        expectedSql.withHost("fake-fqhost", "fake-group1");
        expectedSql.withLogGroup("fake-group2");
        expectedSql.withStream("fake-group2", "fake-index", "fake-sourcetype", "fake-tag");
        expectedSql.withHost("fake-fqhost", "fake-group2");
        String expected = expectedSql.asSql();

        Assertions.assertEquals(expected, actual);
    }

}
