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
package com.teragrep.cfe_41.media;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.using;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.select;

// Builds DSL Query builder and forms queries according to the method call
public final class SQLMedia implements SQLStatementMedia {

    private final Settings settings = new Settings().withRenderKeywordCase(RenderKeywordCase.UPPER);
    private final DSLContext create = using(SQLDialect.MYSQL, settings);
    private final Table<?> logGroupTable = table("log_group");
    private final Table<?> streamTable = table("stream");
    private final Table<?> hostTable = table("host");
    private final Field<Integer> id = field("id", Integer.class);
    private final Field<Integer> gidField = field("gid", Integer.class);
    private final Field<String> name = field("name", String.class);
    private final Field<String> directoryField = field("directory", String.class);
    private final Field<String> streamField = field("stream", String.class);
    private final Field<String> tagField = field("tag", String.class);
    private final List<Query> queries;

    public SQLMedia() {
        this(new ArrayList<>());
    }


    public SQLMedia(List<Query> queries) {
        this.queries = List.copyOf(queries);
    }

    // Build stream here
    @Override
    public SQLStatementMedia withStream(
            final String logGroupName,
            final String directory,
            final String stream,
            final String tag
    ) {
        final Query queryResult = create
                .insertInto(streamTable)
                .columns(gidField, directoryField, streamField, tagField)
                .values(field(select(id).from(logGroupTable).where(name.eq(name.as(logGroupName)))), directoryField.as(directory), streamField.as(stream), tagField.as(tag));
        return withQuery(queryResult);
    }



    @Override
    public SQLStatementMedia withLogGroup(final String logGroupName) {
        final Query queryResult = create.insertInto(logGroupTable).columns(name).values(name.as(logGroupName));
        return withQuery(queryResult);
    }



    @Override
    public SQLStatementMedia withHost(final String hostName, final String logGroupName) {
        final Query queryResult = create
                .insertInto(hostTable)
                .columns(name, gidField)
                .values(name.as(hostName), field(select(id).from(logGroupTable).where(name.eq(name.as(logGroupName)))));
        return withQuery(queryResult);
    }

    @Override
    public Batch asSql() {
        List<String> sqlStrings = queries.stream().map(q -> q.getSQL(ParamType.INLINED)).collect(Collectors.toList());
        for (String sqlString : sqlStrings) {
            System.out.println(sqlString);
        }
        return create.batch(queries);
    }

    public SQLMedia withQuery(final Query query) {
        List<Query> newQueries = new ArrayList<>(queries);
        newQueries.add(query);
        return new SQLMedia(newQueries);
    }



}
