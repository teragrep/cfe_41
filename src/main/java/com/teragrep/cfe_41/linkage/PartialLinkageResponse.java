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
package com.teragrep.cfe_41.linkage;

import jakarta.json.JsonObject;

import java.util.Objects;

public final class PartialLinkageResponse {

    private final JsonObject partialLinkage;

    public PartialLinkageResponse(final JsonObject partialLinkage) {
        this.partialLinkage = partialLinkage;
    }

    public int linkageId() {
        return partialLinkage.getInt("id");
    }

    public String captureGroupName() {
        return partialLinkage.getString("capture_group_name");
    }

    public String hostGroupName() {
        return partialLinkage.getString("host_group_name");
    }

    public String hostGroupType() {
        return partialLinkage.getString("host_group_type");
    }

    public String captureGroupType() {
        return partialLinkage.getString("capture_group_type");
    }

    public int hostGroupId() {
        return partialLinkage.getInt("host_group_id");
    }

    public int captureGroupId() {
        return partialLinkage.getInt("capture_group_id");
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartialLinkageResponse that = (PartialLinkageResponse) o;
        return Objects.equals(partialLinkage, that.partialLinkage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(partialLinkage);
    }
}
