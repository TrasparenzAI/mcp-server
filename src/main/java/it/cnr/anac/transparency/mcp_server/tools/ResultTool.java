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
import it.cnr.anac.transparency.mcp_server.dto.PageResponse;
import it.cnr.anac.transparency.mcp_server.dto.ResultShowDto;
import it.cnr.anac.transparency.mcp_server.services.ResultService;
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
public class ResultTool {

    private final ResultService resultService;

    @McpTool(description = "Fornisce le informazioni sui risultati dei controlli effettuati dalla " +
            "piattaforma TrasparenzAI relativamente alla sezione Amministrazione trasparente di una pubblica " +
            "amministrazione, come previsto dal DECRETO LEGISLATIVO 14 marzo 2013, n. 33. " +
            "I Risultati sono paginati, puoi usare il parametro page per ottenere le pagine diverse" +
            "dalla prima e scorrere così tutti i risultati." +
            "Nella risposta puoi trovare il numero di risultati totali e il numero di pagine totali.")
    public PageResponse<ResultShowDto> lastResults(
            McpSyncServerExchange exchange,
            @McpToolParam(description = "codice ipa amministrazione pubblica") String codiceIpa,
            @McpToolParam(description = "page - la pagina di risultati da ottenere", required = false) Integer page,
            @McpProgressToken String progressToken) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder() // (3)
                .level(McpSchema.LoggingLevel.DEBUG)
                .data(String.format("Call lastResult with param with codiceIpa: %s", codiceIpa))
                .meta(Map.of())
                .build());

        log.debug("Call lastResult with codiceIpa: {}", codiceIpa);
        return resultService.getLastResult(codiceIpa, page);
    }
}
