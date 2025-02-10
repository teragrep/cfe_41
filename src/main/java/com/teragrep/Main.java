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
package com.teragrep;

import com.teragrep.capture.*;
import com.teragrep.captureGroup.CaptureGroup;
import com.teragrep.captureGroup.CaptureGroupRequest;
import com.teragrep.captureGroup.CaptureGroupResponse;
import com.teragrep.captureGroup.PartialCaptureResponse;
import com.teragrep.flow.FkFlowRequest;
import com.teragrep.flow.FlowRequest;
import com.teragrep.flow.FlowResponse;
import com.teragrep.flow.PartialFlowResponse;
import com.teragrep.host.HostRequest;
import com.teragrep.host.HostResponse;
import com.teragrep.hostGroup.HostGroupRequest;
import com.teragrep.hostGroup.HostGroupResponse;
import com.teragrep.hostGroup.PartialHostResponse;
import com.teragrep.linkage.LinkageRequest;
import com.teragrep.linkage.LinkageResponse;
import com.teragrep.linkage.PartialLinkageResponse;
import com.teragrep.old.JsonPrettyPrint;
import com.teragrep.relpHost.CompleteRelpHost;
import com.teragrep.relpHost.RelpHost;
import com.teragrep.sink.SinkRequest;
import com.teragrep.sink.SinkResponse;
import jakarta.json.*;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

    }
}