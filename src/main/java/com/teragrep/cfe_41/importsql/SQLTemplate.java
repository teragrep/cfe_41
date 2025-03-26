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

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.io.StringWriter;
import java.util.Objects;

public final class SQLTemplate {

    private final DSLContext ctx;
    private final StringWriter sw;

    public SQLTemplate() {
        this(
                DSL.using(SQLDialect.MYSQL, new Settings().withRenderKeywordCase(RenderKeywordCase.UPPER)),
                new StringWriter()
        );
    }

    public SQLTemplate(final DSLContext ctx,final StringWriter sw) {
        this.ctx = ctx;
        this.sw = sw;
    }

    public String startTemplate() {
        final Query queryGroup = ctx.deleteFrom(DSL.table("log_group"));
        final Query queryHost = ctx.deleteFrom(DSL.table("host"));
        final Query queryStream = ctx.deleteFrom(DSL.table("stream"));
        sw.write("START TRANSACTION;\n");
        sw.write(queryGroup + ";\n");
        sw.write(queryHost + ";\n");
        sw.write(queryStream + ";\n");
        return sw.toString();
    }

    public String endTemplate() {
        sw.write("COMMIT;");
        return sw.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SQLTemplate that = (SQLTemplate) o;
        return Objects.equals(ctx, that.ctx) && Objects.equals(sw, that.sw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctx, sw);
    }
}
