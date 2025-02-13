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

/*
Should take any generic JSONObject passed from request and convert the values into map for later parsing.
 */

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.StringReader;

public final class Response {

    private final HttpResponse jsonResponse;

    public Response(HttpResponse jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    // parse response that comes in array
    public JsonArray parseArrayResponse() throws IOException {
        // Convert Http response to JsonReader
        String response = EntityUtils.toString(jsonResponse.getEntity());
        System.out.println(response);
        JsonReader jsonReader = Json.createReader(new StringReader(response));
        // Return the JSONArray back to the object
        return jsonReader.readArray();
    }

    // Different method for single object response
    public JsonObject parseResponse() throws IOException {
        // Convert Http response to JsonReader
        String response = EntityUtils.toString(jsonResponse.getEntity());
        System.out.println(response);
        JsonReader jsonReader = Json.createReader(new StringReader(response));
        // Return the JSONArray back to the object
        return jsonReader.readObject();
    }

    @Override
    public String toString() {
        return "Response{" + "jsonResponse=" + jsonResponse + '}';
    }
}
