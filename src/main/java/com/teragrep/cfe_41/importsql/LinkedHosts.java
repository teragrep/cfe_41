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
import com.teragrep.cfe_41.host.HostRequest;
import com.teragrep.cfe_41.host.HostRequestImpl;
import com.teragrep.cfe_41.hostGroup.HostGroupRequest;
import com.teragrep.cfe_41.hostGroup.HostGroupRequestImpl;
import com.teragrep.cfe_41.hostGroup.HostGroupResponse;
import com.teragrep.cfe_41.hostGroup.PartialHostResponse;
import com.teragrep.cfe_41.linkage.LinkageRequest;
import com.teragrep.cfe_41.linkage.LinkageRequestImpl;
import com.teragrep.cfe_41.linkage.LinkageResponse;
import com.teragrep.cfe_41.linkage.PartialLinkageResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;

public final class LinkedHosts {

    private final LinkageRequest linkageRequest;
    private final HostGroupRequest hostGroupRequest;
    private final HostRequest hostRequest;

    public LinkedHosts(final ApiConfig apiConfig) {
        this(new LinkageRequestImpl(apiConfig), new HostGroupRequestImpl(apiConfig), new HostRequestImpl(apiConfig));
    }

    public LinkedHosts(
            final LinkageRequest linkageRequest,
            final HostGroupRequest hostGroupRequest,
            final HostRequest hostRequest
    ) {
        this.linkageRequest = linkageRequest;
        this.hostGroupRequest = hostGroupRequest;
        this.hostRequest = hostRequest;
    }

    public List<SQLMediaHost> asSqlMediaHosts(final String captureGroupName) throws IOException {
        final Set<PartialLinkageResponse> partialLinkageResponses;
        final LinkageResponse linkageResponse = linkageRequest.linkageResponse(captureGroupName);
        // Get partials which show in detail which host groups are linked with capture groups
        partialLinkageResponses = new HashSet<>(linkageResponse.partialLinkageResponses());

        // Get a list of host groups linked
        final Set<HostGroupResponse> hostGroupResponses = new HashSet<>();
        for (PartialLinkageResponse partialLinkageResponse : partialLinkageResponses) {
            hostGroupResponses.add(hostGroupRequest.hostGroupResponse(partialLinkageResponse.hostGroupName()));
        }

        // Get partials which show in detail which hosts are linked within host groups
        final Set<PartialHostResponse> partialHostResponses = new HashSet<>();
        for (HostGroupResponse hostGroupResponse : hostGroupResponses) {
            partialHostResponses.addAll(hostGroupResponse.partialHostResponses());
        }
        final List<SQLMediaHost> sqlMediaHosts = new ArrayList<>();
        // Inserts hosts to sqlStatement
        for (PartialHostResponse host : partialHostResponses) {
            final SQLMediaHost sqlMediaHost = new SQLMediaHost(
                    hostRequest.hostResponse(host.hostId(), host.hostGroupType()),
                    captureGroupName
            );
            sqlMediaHosts.add(sqlMediaHost);
        }
        return sqlMediaHosts;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LinkedHosts linkedHosts = (LinkedHosts) o;
        return Objects.equals(linkageRequest, linkedHosts.linkageRequest) && Objects
                .equals(hostGroupRequest, linkedHosts.hostGroupRequest)
                && Objects.equals(hostRequest, linkedHosts.hostRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkageRequest, hostGroupRequest, hostRequest);
    }
}
