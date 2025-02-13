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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 Class for handling command line arguments. CNF_01 tool will replace this class completely.
 */
final class Arguments {

    private final Map<String, String> arguments;

    Arguments(final String ... args) {
        this(Arrays.asList(args));
    }

    Arguments(final Iterable<String> args) {
        this.arguments = Arguments.asMap(args);
    }

    // Might not be allowed due to straight up copy pasta from Takes project...?
    private static Map<String, String> asMap(final Iterable<String> args) {
        // Initialize empty map object
        final Map<String, String> map = new HashMap<>(0);
        // Initialize pattern object to check if argument is in valid form
        final Pattern ptn = Pattern.compile("--([a-z\\-]+)(=.+)?");
        // Loops through args
        for (final String arg : args) {
            // Initialize matcher object to check if Pattern object (regex) matches
            final Matcher matcher = ptn.matcher(arg);
            // If does not match, then throw IllegalState
            if (!matcher.matches()) {
                throw new IllegalStateException(String.format("Can't parse this argument: '%s'", arg));
            }
            // Assign args value as variable from matcher that happens after equal sign
            final String value = matcher.group(2);
            // If argument does not have value then empty string is inserted.
            if (value == null) {
                map.put(matcher.group(1), "");
            }
            else {
                // In other cases key is inserted before equal sign and value is substringed by one so the equal sign does not come along.
                map.put(matcher.group(1), value.substring(1));
            }
        }
        return map;
    }

    public String get(String key) {
        return arguments.get(key);
    }
}
