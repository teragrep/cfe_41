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
package com.teragrep.cfe_41.fakes;

import com.teragrep.cfe_41.hostGroup.HostGroupRequest;
import com.teragrep.cfe_41.hostGroup.HostGroupResponse;
import jakarta.json.Json;

import java.io.IOException;

public final class HostGroupRequestFake implements HostGroupRequest {

    @Override
    public HostGroupResponse hostGroupResponse(final String hostGroupName) throws IOException {
        return new HostGroupResponse(
                Json.createArrayBuilder().add(Json.createObjectBuilder().add("host_id", 1).add("host_group_name", "hostGroup1").add("host_group_type", "relp").add("md5", "fake-md5").add("id", 1)).add(Json.createObjectBuilder().add("host_id", 2).add("host_group_name", "hostGroup2").add("host_group_type", "relp").add("md5", "fake-md52").add("id", 2)).build()
        );
    }
}
