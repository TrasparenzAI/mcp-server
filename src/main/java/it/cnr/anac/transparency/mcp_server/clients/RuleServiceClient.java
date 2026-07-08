/*
 * Copyright (C) 2026 Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package it.cnr.anac.transparency.mcp_server.clients;

import it.cnr.anac.transparency.mcp_server.dto.RuleServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        name = "ruleServiceClient",
        url = "${rule.base-url:https://monitorai.ba.cnr.it/rule-service}"
)
public interface RuleServiceClient {

    @GetMapping("/v1/rules/{root_rule_name}/count")
    Integer count(@PathVariable("root_rule_name") String root_rule_name);

    @GetMapping("/v1/rules")
    Map<String, RuleServiceDto> all();
}
