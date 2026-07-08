/*
 * Copyright (C) 2025 Consiglio Nazionale delle Ricerche
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
package it.cnr.anac.transparency.mcp_server.tools;

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import it.cnr.anac.transparency.mcp_server.clients.RuleServiceClient;

import it.cnr.anac.transparency.mcp_server.dto.RuleServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpProgressToken;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RuleTool {

    private final RuleServiceClient ruleServiceClient;

    @McpTool(name = "totale-regole",
            description = "Il servizio ritorna il numero totale delle regole definite e da verificare, ha bisogno come parametro obbligatorio il nome della regola padre")
    public Integer ruleCount(
            McpSyncServerExchange exchange,
            @McpToolParam(description = "Nome della regola padre", required = true) String root_rule_name,
            @McpProgressToken String progressToken) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder() // (3)
                .level(McpSchema.LoggingLevel.DEBUG)
                .data(String.format("Call ruleCount with param with root_rule_name: %s",root_rule_name))
                .meta(Map.of())
                .build());

        log.info("Call ruleCount with param with root_rule_name: {}", root_rule_name);
        Integer count = ruleServiceClient.count(root_rule_name);
        log.info("Trovate {} regole", count);
        return count;
    }

    @McpTool(name = "albero-regole",
            description = "Il servizio recupera e restituisce l'intero albero delle regole di trasparenza configurate, " +
                    "organizzato in una struttura gerarchica con nome della regola come chiave. Ogni regola contiene " +
                    "i termini da cercare e le eventuali regole figlie.")
    public Map<String, RuleServiceDto> allRules(
            McpSyncServerExchange exchange,
            @McpProgressToken String progressToken) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder()
                .level(McpSchema.LoggingLevel.DEBUG)
                .data("Call allRules")
                .meta(Map.of())
                .build());

        log.info("Call allRules");
        Map<String, RuleServiceDto> rules = ruleServiceClient.all();
        log.info("Recuperato albero regole con {} regole radice", rules.size());
        return rules;
    }
}
