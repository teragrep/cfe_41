package com.teragrep.cfe_41.fakes;

import java.util.HashMap;
import java.util.Map;

public final class ConfigFake {
    public Map<String, String> asMap() {
        final Map<String, String> map = new HashMap<>();
                map.put("cfe_04.global.truncate","12345");
                map.put("cfe_04.global.last_chance_index","lci");
                map.put("cfe_04.global.last_chance_index_malformed","lci_malformed");
                map.put("cfe_04.global.max_days_ago","25");

                map.put("cfe_04.config.index.home.path.template","fake:home0/%s/fake");
                map.put("cfe_04.config.index.cold.path.template","fake:cold0/%s/fake");
                map.put("cfe_04.config.index.thawed.path.template","fake:thaw0/%s/fake");
                map.put("cfe_04.config.index.summary.home.path","fake:summary/summary/fake/fake");

                map.put("cfe_04.config.sourcetype.max_days_ago","25");
                map.put("cfe_04.config.sourcetype.category","Fake category");
                map.put("cfe_04.config.sourcetype.description","Fake description");
                map.put("cfe_04.config.sourcetype.truncate","1");
                map.put("cfe_04.config.sourcetype.freeform_indexer_enabled","false");
                map.put("cfe_04.config.sourcetype.freeform_indexer_text","");
                map.put("cfe_04.config.sourcetype.freeform_lb_enabled","false");
                map.put("cfe_04.config.sourcetype.freeform_lb_text","");

                map.put("cfe_04.meta.name","meta-name");
                return map;
    }
}
