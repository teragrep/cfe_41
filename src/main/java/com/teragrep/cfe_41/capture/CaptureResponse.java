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
package com.teragrep.cfe_41.capture;

import jakarta.json.JsonObject;

import java.util.NoSuchElementException;
import java.util.Objects;

/*
Takes Capture JsonObject and ṕarses it into Capture
 */
public final class CaptureResponse implements Capture {

    private final JsonObject jsonObject;

    public CaptureResponse(final JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public int id() {
        if (jsonObject.containsKey("id")) {
            return jsonObject.getInt("id");
        }
        throw new NoSuchElementException("No JSON key 'id' found");
    }

    @Override
    public String tag() {
        if (jsonObject.containsKey("tag")) {
            return jsonObject.getString("tag");
        }
        throw new NoSuchElementException("No JSON key 'tag' found");
    }

    @Override
    public String retention_time() {
        if (jsonObject.containsKey("retention_time")) {
            return jsonObject.getString("retention_time");
        }
        throw new NoSuchElementException("No JSON key 'retention_time' found");
    }

    @Override
    public String category() {
        if (jsonObject.containsKey("category")) {
            return jsonObject.getString("category");
        }
        throw new NoSuchElementException("No JSON key 'category' found");
    }

    @Override
    public String application() {
        if (jsonObject.containsKey("application")) {
            return jsonObject.getString("application");
        }
        throw new NoSuchElementException("No JSON key 'application' found");
    }

    @Override
    public String index() {
        if (jsonObject.containsKey("index")) {
            return jsonObject.getString("index");
        }
        throw new NoSuchElementException("No JSON key 'index' found");
    }

    @Override
    public String source_type() {
        if (jsonObject.containsKey("source_type")) {
            return jsonObject.getString("source_type");
        }
        throw new NoSuchElementException("No JSON key 'source_type' found");
    }

    @Override
    public String protocol() {
        if (jsonObject.containsKey("protocol")) {
            return jsonObject.getString("protocol");
        }
        throw new NoSuchElementException("No JSON key 'protocol' found");
    }

    @Override
    public String flow() {
        if (jsonObject.containsKey("flow")) {
            return jsonObject.getString("flow");
        }
        throw new NoSuchElementException("No JSON key 'flow' found");
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CaptureResponse that = (CaptureResponse) o;
        return Objects.equals(jsonObject, that.jsonObject);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jsonObject);
    }
}
