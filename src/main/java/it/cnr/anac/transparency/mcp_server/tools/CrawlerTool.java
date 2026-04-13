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
package it.cnr.anac.transparency.mcp_server.tools;

import it.cnr.anac.transparency.mcp_server.clients.CrawlerClient;
import it.cnr.anac.transparency.mcp_server.dto.CrawlerRequest;
import it.cnr.anac.transparency.mcp_server.dto.CrawlerResponse;
import it.cnr.anac.transparency.mcp_server.dto.CrawlingMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlerTool {

    private final CrawlerClient crawlerClient;

    @McpTool(
            description = """
            Recupera il contenuto HTML di una pagina web dato il suo URL.
            Supporta due modalità di recupero:
            - 'httpStream': fetch HTTP diretto, veloce, adatto a pagine statiche (senza JavaScript).
            - 'htmlSource': usa un browser Chrome headless (Selenium) per caricare la pagina, \
            esegue JavaScript e restituisce il DOM completamente renderizzato. \
            Adatto a pagine dinamiche o con redirect lato client.
            Se non specificata, la modalità predefinita è 'htmlSource'.
            Restituisce il contenuto testuale/HTML della pagina recuperata.
            """
    )
    public String fetchUrl(
            @McpToolParam(
                    description = """
                    Modalità di recupero della pagina web. Valori ammessi:
                    - 'httpStream': esegue una richiesta HTTP GET diretta senza browser. \
                    Veloce ma non esegue JavaScript. Usare per pagine statiche.
                    - 'htmlSource': carica la pagina tramite browser Chrome headless (Selenium), \
                    eseguendo JavaScript e attendendo il rendering completo del DOM. \
                    Usare per pagine dinamiche, Single Page Application o pagine con redirect lato client.
                    Se omesso o null, viene usata la modalità 'htmlSource'.
                    """
            ) CrawlingMode crawlingMode,

            @McpToolParam(
                    description = """
                    L'URL completo della pagina web da recuperare. \
                    Deve includere lo schema (http:// o https://). \
                    Esempi validi: 'https://www.example.com', 'http://192.168.1.1/pagina'. \
                    Se lo schema è assente, viene aggiunto automaticamente 'http://'.
                    """
            ) String url
    ) {
        log.info("Try to fetch URL: {}", url);
        CrawlerResponse crawlerResponse = crawlerClient.fetchURL(
                CrawlerRequest.of(Optional.ofNullable(crawlingMode).orElse(CrawlingMode.htmlSource), url)
        );
        String content = crawlerResponse.content();
        log.info("The page was successfully retrieved");
        if (log.isTraceEnabled()) log.trace("The page was successfully retrieved and the content is:: {}", content);
        return content;
    }
}