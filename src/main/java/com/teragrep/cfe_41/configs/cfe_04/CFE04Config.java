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
package com.teragrep.cfe_41.configs.cfe_04;

import com.teragrep.cfe_41.Printable;
import com.teragrep.cfe_41.capture.*;
import com.teragrep.cfe_41.captureGroup.CaptureGroupAllRequest;
import com.teragrep.cfe_41.captureGroup.PartialCaptureResponse;
import com.teragrep.cfe_41.configs.cfe_04.global.Global;
import com.teragrep.cfe_41.configs.cfe_04.index.Index;
import com.teragrep.cfe_41.configs.cfe_04.meta.Meta;
import com.teragrep.cfe_41.configs.cfe_04.sourcetype.Sourcetype;
import com.teragrep.cfe_41.media.Media;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

public final class CFE04Config implements Printable {

    private final CaptureGroupAllRequest captureGroupAllRequest;
    private final CaptureRequest captureRequest;
    private final CaptureStorageRequest captureStorageRequest;

    public CFE04Config(
            final CaptureGroupAllRequest captureGroupAllRequest,
            final CaptureRequest captureRequest,
            final CaptureStorageRequest captureStorageRequest
    ) {
        this.captureGroupAllRequest = captureGroupAllRequest;
        this.captureRequest = captureRequest;
        this.captureStorageRequest = captureStorageRequest;
    }

    @Override
    public Media print(final Media media) {
        // All CaptureGroups
        List<PartialCaptureResponse> groups;
        try {
            groups = captureGroupAllRequest.captureGroupResponse().partialCaptureResponses();
        }
        catch (IOException e) {
            throw new UncheckedIOException("Exception occurred whilst sending captureGroupAllRequest", e);
        }

        Set<Jsonable> indexes = new HashSet<>();
        Set<Jsonable> sourcetypes = new HashSet<>();
        Set<Jsonable> volumes = new HashSet<>();
        Set<Jsonable> fields = new HashSet<>();
        Set<Jsonable> transforms = new HashSet<>();
        for (final PartialCaptureResponse group : groups) {
            try {
                final CaptureResponse captureResponse = captureRequest
                        .captureResponse(group.captureDefinitionId(), group.captureGroupType());
                final List<PartialCaptureStorageResponse> partialCaptureStorageResponses = captureStorageRequest
                        .captureStorageResponse(group.captureDefinitionId())
                        .partialCaptureStorageResponses();

                if (partialCaptureStorageResponses.stream().anyMatch(r -> r.storageName().equalsIgnoreCase("cfe_04"))) {
                    indexes.add(new Index(captureResponse.index(), "%s","%s","%s",""));
                    sourcetypes.add(new Sourcetype(captureResponse.source_type(), "","","",List.of(),"","","","",""));
                }
            }
            catch (IOException e) {
                throw new UncheckedIOException("Exception occurred whilst sending CaptureRequests", e);
            }
        }

        return media
                .with("_meta", new Meta("").asJsonStructure())
                .with("volumes", new JsonableArray(volumes).asJsonStructure())
                .with("indexes", new JsonableArray(indexes).asJsonStructure())
                .with("sourcetypes", new JsonableArray(sourcetypes).asJsonStructure())
                .with("fields", new JsonableArray(fields).asJsonStructure())
                .with("transforms", new JsonableArray(transforms).asJsonStructure())
                .with("global", new Global("","","","").asJsonStructure());
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CFE04Config that = (CFE04Config) o;
        return Objects.equals(captureGroupAllRequest, that.captureGroupAllRequest) && Objects.equals(captureRequest, that.captureRequest) && Objects.equals(captureStorageRequest, that.captureStorageRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(captureGroupAllRequest, captureRequest, captureStorageRequest);
    }
}
