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

import com.teragrep.cfe_41.fakes.HostFake;
import com.teragrep.cfe_41.host.Host;
import com.teragrep.cfe_41.media.SQLMedia;
import com.teragrep.cfe_41.media.SQLStatementMedia;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SQLMediaHostTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(SQLMediaHost.class).verify();
    }

    @Test
    public void sqlMediaHost() {
        Host host = new HostFake();
        SQLMediaHost sqlMediaHost = new SQLMediaHost(host, "captureGroup1");

        SQLMedia sqlMediaActual = new SQLMedia();
        SQLMedia sqlMediaExpected = new SQLMedia();

        SQLStatementMedia actual = sqlMediaHost.asSql(sqlMediaActual);

        SQLStatementMedia expected = sqlMediaExpected.withHost("fake-fqhost", "captureGroup1");

        Assertions.assertEquals(expected.asSql(), actual.asSql());
    }

}
