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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQLHost {

    private final LinkageRequest linkageRequest;
    private final HostGroupRequest hostGroupRequest;
    private final HostRequest hostRequest;

    public SQLHost(final ApiConfig apiConfig) {
        this(new LinkageRequestImpl(apiConfig), new HostGroupRequestImpl(apiConfig), new HostRequestImpl(apiConfig));

    }

    public SQLHost(LinkageRequest linkageRequest, HostGroupRequest hostGroupRequest, HostRequest hostRequest) {
        this.linkageRequest = linkageRequest;
        this.hostGroupRequest = hostGroupRequest;
        this.hostRequest = hostRequest;
    }

    public List<SQLMediaHost> asSQLHosts(final String captureGroupName) {
        final Set<PartialLinkageResponse> partialLinkageResponses;
        try {
            LinkageResponse linkageResponse = linkageRequest.linkageResponse(captureGroupName);
            // Get partials which show in detail which host groups are linked with capture groups
            partialLinkageResponses = new HashSet<>(linkageResponse.partialLinkageResponses());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get a list of host groups linked
        final Set<HostGroupResponse> hostGroupResponses = new HashSet<>();
        for (PartialLinkageResponse partialLinkageResponse : partialLinkageResponses) {
            try {
                hostGroupResponses.add(hostGroupRequest.hostGroupResponse(partialLinkageResponse.hostGroupName()));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Get partials which show in detail which hosts are linked within host groups
        final Set<PartialHostResponse> partialHostResponses = new HashSet<>();
        for (HostGroupResponse hostGroupResponse : hostGroupResponses) {
            partialHostResponses.addAll(hostGroupResponse.partialHostResponses());
        }
        List<SQLMediaHost> sqlMediaHosts = new ArrayList<>();
        // Inserts hosts to sqlStatement
        try {
            for (PartialHostResponse host : partialHostResponses) {
                SQLMediaHost sqlMediaHost = new SQLMediaHost(
                        hostRequest.hostResponse(host.hostId(), host.hostGroupType()),
                        captureGroupName
                );
                sqlMediaHosts.add(sqlMediaHost);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlMediaHosts;
    }
}
