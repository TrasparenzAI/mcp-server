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
import it.cnr.anac.transparency.mcp_server.clients.PublicSiteClient;
import it.cnr.anac.transparency.mcp_server.dto.CompanyDto;

import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpProgressToken;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PublicSiteTool {

    @Value("${public-sites.company-search-max-results}")
    Integer companySearchMaxResults;

    private final PublicSiteClient publicSiteClient;

    @McpTool(description = "Ricerca una pubblica amministrazione (PA) attraverso alcune informazioni " +
            "disponibili nell'Indice delle PA (IPA). E' possibile fornire opzionalmente alcuni " +
            "parametri per filtrare le pubbliche amministrazioni fornite.")
    public List<CompanyDto> listCompanies(
            McpSyncServerExchange exchange,
            @McpToolParam(description = "codice categoria IPA dell'ente", required = false) String codiceCategoria,
            @McpToolParam(description = "codice fiscale", required = false) String codiceFiscaleEnte,
            @McpToolParam(description = "codice ipa", required = false) String codiceIpa,
            @McpToolParam(description = "denominazione ente", required = false) String denominazioneEnte,
            @McpToolParam(description = "comune", required = false) String comune,
            @McpToolParam(description = "provincia", required = false) String provincia,
            @McpToolParam(description = "id ipa from", required = false) Long idIpaFrom,
            @McpToolParam(description = "senza indirizzo", required = false) Boolean withoutAddress,
            @McpToolParam(description = "regione", required = false) String regione,
            @McpProgressToken String progressToken) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder() // (3)
                .level(McpSchema.LoggingLevel.DEBUG)
                .data(
                        String.format("Call listCompanies with param with codiceCategoria: %s, codiceFiscaleEnte: %s" +
                                        "codiceIpa: %s, denominazione: %s, comune: %s provincia: %s, regione: %s",
                                        codiceCategoria, codiceFiscaleEnte, codiceIpa, denominazioneEnte,
                                        comune, provincia, regione))
                .meta(Map.of())
                .build());

        return publicSiteClient.listCompanies(
                codiceCategoria, codiceFiscaleEnte, codiceIpa, denominazioneEnte,
                comune, provincia, idIpaFrom, withoutAddress, regione, null, companySearchMaxResults, null
        ).content();
    }
}
