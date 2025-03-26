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
import com.teragrep.cfe_41.captureGroup.CaptureGroupAllRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupAllRequestImpl;
import com.teragrep.cfe_41.captureGroup.CaptureGroupResponse;
import com.teragrep.cfe_41.captureGroup.PartialCaptureResponse;
import com.teragrep.cfe_41.media.SQLMedia;
import com.teragrep.cfe_41.media.SQLStatementMedia;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class Importsql {

    private final CaptureGroupAllRequest captureGroupAllRequest;
    private final SQLHost sqlHost;
    private final SQLCapture sqlCapture;
    private final ApiConfig apiConfig;

    public Importsql(final ApiConfig apiConfig) {
        this(new CaptureGroupAllRequestImpl(apiConfig), new SQLHost(apiConfig), new SQLCapture(apiConfig), apiConfig);
    }

    public Importsql(
            final CaptureGroupAllRequest captureGroupAllRequest,
            final SQLHost sqlHost,
            final SQLCapture sqlCapture,
            final ApiConfig apiConfig
    ) {
        this.apiConfig = apiConfig;
        this.captureGroupAllRequest = captureGroupAllRequest;
        this.sqlHost = sqlHost;
        this.sqlCapture = sqlCapture;
    }

    @Override
    public String toString() {
        SQLStatementMedia sqlStatementMedia = new SQLMedia();
        final CaptureGroupResponse capRespo;
        try {
            capRespo = captureGroupAllRequest.captureGroupResponse();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Get unique group names from all capture groups
        final Set<String> captureGroupNames = new HashSet<>();
        for (PartialCaptureResponse captureGroup : capRespo.partialCaptureResponses()) {
            captureGroupNames.add(captureGroup.groupName());
        }

        for (String captureGroupName : captureGroupNames) {
            final SQLMediaCaptureGroup SQLMediaCaptureGroup = new SQLMediaCaptureGroup(captureGroupName);

            final List<SQLMediaCapture> captures = sqlCapture.asSQLCaptures(captureGroupName);

            final List<SQLMediaHost> hosts = sqlHost.asSQLHosts(captureGroupName);

            // Group insertion to importsql
            sqlStatementMedia = SQLMediaCaptureGroup.asSql(sqlStatementMedia);
            // Streams insertion to importsql
            for (SQLMediaCapture c : captures) {
                sqlStatementMedia = c.asSql(sqlStatementMedia);
            }
            // Hosts insertion to importsql
            for (SQLMediaHost h : hosts) {
                sqlStatementMedia = h.asSql(sqlStatementMedia);
            }
        }
        return sqlStatementMedia.asSql();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Importsql importsql = (Importsql) o;
        return Objects.equals(captureGroupAllRequest, importsql.captureGroupAllRequest) && Objects
                .equals(sqlHost, importsql.sqlHost) && Objects.equals(sqlCapture, importsql.sqlCapture)
                && Objects.equals(apiConfig, importsql.apiConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(captureGroupAllRequest, sqlHost, sqlCapture, apiConfig);
    }
}
