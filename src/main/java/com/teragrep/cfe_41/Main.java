/*
 * Integration Command-line tool for Teragrep
 * Copyright (C) 2021  Suomen Kanuuna Oy
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
package com.teragrep.cfe_41;

import com.teragrep.cfe_41.flow.FlowRequest;
import com.teragrep.cfe_41.flow.FlowResponse;
import com.teragrep.cfe_41.flow.PartialFlowResponse;
import com.teragrep.cfe_41.host.HostRequest;
import com.teragrep.cfe_41.host.HostResponse;
import com.teragrep.cfe_41.hostGroup.HostGroupRequest;
import com.teragrep.cfe_41.hostGroup.HostGroupResponse;
import com.teragrep.cfe_41.hostGroup.PartialHostResponse;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // Creates new ApiConfig from commandline args
        ApiConfig apiConfig = new ApiConfig(new Arguments(args));

        HostGroupRequest hostGroupRequests = new HostGroupRequest("string", apiConfig);
        HostGroupResponse hostGroupResponse = hostGroupRequests.hostGroupResponse();
        // List consisting of all hosts in a host group
        List<PartialHostResponse> partialHostResponses = hostGroupResponse.partialHostResponses();

        // List of all requests towards singular hosts
        List<HostRequest> hostRequests = new ArrayList<>();
        for (PartialHostResponse partialHostResponse : partialHostResponses) {
            // Create single list element of new HostRequest with host_id, host_type and parameters required for creating external requests
            hostRequests.add( new HostRequest(partialHostResponse.hostId(),partialHostResponse.hostGroupType(),apiConfig));
        }

        // List of responds from request
        List<HostResponse> hostResponses = new ArrayList<>();
        for (HostRequest hostRequest : hostRequests) {
            // Add the results from request to list of responds
            hostResponses.add(hostRequest.hostResponse());
        }

        // Loop through the responds and pick what is needed for config creation
        for(HostResponse hostResponse : hostResponses) {
            hostResponse.fqHost();
            hostResponse.md5();
        }
    }
}
