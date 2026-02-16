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

import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import it.cnr.anac.transparency.mcp_server.dto.RispostaPaginata;
import it.cnr.anac.transparency.mcp_server.dto.RisultatoValidazioneRegola;
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

    @McpTool(
            name = "Ultimi risultati controllo trasparenza",
            description =
                    "Recupera i risultati (paginati) dei controlli TrasparenzAI per la sezione 'Amministrazione trasparente' "
                            + "di una pubblica amministrazione (PA) identificata da codice IPA, in attuazione del D.Lgs. 33/2013.\n\n"

                            + "QUANDO INVOCARLO (trigger): invoca SEMPRE questo tool quando l’utente chiede "
                            + "\"ultimi risultati\", \"esiti\", \"risultati controlli\", \"report trasparenza\" o informazioni "
                            + "sullo stato della trasparenza di una PA.\n"
                            + "Non inventare i dati: per rispondere devi ottenere i risultati tramite questo tool.\n"
                            + "Se l’utente non fornisce il codice IPA, chiedilo prima.\n\n"

                            + "PAGINAZIONE (0-based):\n"
                            + "- page omesso/null => 0 (prima pagina)\n"
                            + "- per scorrere: usa page=1,2,... fino a (pagineTotali - 1)\n\n"

                            + "STRUTTURA RISPOSTA (JSON) E COME INTERPRETARLA:\n"
                            + "La risposta è una RispostaPaginata con questi campi principali:\n"
                            + "- contenuto: array di RisultatoValidazioneRegola (gli elementi della pagina)\n"
                            + "- dimensioneDellaPagina: size della pagina\n"
                            + "- elementiTotali: numero totale elementi complessivi\n"
                            + "- numeroDiElementi: quanti elementi in questa pagina\n"
                            + "- numeroDiPagina: pagina corrente (0-based)\n"
                            + "- pagineTotali: numero totale di pagine\n\n"

                            + "COME RISPONDERE (FORMATO RICHIESTO):\n"
                            + "1) Aggiungi una riga di riepilogo paging nel formato:\n"
                            + "   \"Pagina X/Y — elementi in pagina: N — totale elementi: T\"\n"
                            + "   dove X=numeroDiPagina, Y=pagineTotali, N=numeroDiElementi, T=elementiTotali.\n"
                            + "2) Poi rispondi SEMPRE con un elenco puntato (bullet list) dei risultati della pagina corrente.\n"
                            + "3) Se numeroDiElementi è grande, mostra solo i primi 10 bullet e indica che ci sono altri risultati nella pagina.\n\n"

                            + "CONTENUTO DI OGNI BULLET (quando disponibile):\n"
                            + "- titolo: termine (oppure contenuto)\n"
                            + "- nomeRegola\n"
                            + "- stato (es. 200=OK, 202=accettato/da verificare, 404=mancante)\n"
                            + "- dataUltimoAggiornamento\n"
                            + "- link: preferisci urlDestinazione; in alternativa urlEffettivo\n"
                            + "- se messaggioDiErrore non è null, includilo."
    )
    public RispostaPaginata<RisultatoValidazioneRegola> lastResults(
            McpSyncServerExchange exchange,
            @McpToolParam(
                    description = "codiceIpa: codice IPA della PA (stringa non vuota). Se manca, chiedilo all’utente."
            ) String codiceIpa,
            @McpToolParam(
                    description = "page: indice pagina 0-based. Opzionale; se omesso/null viene usata page=0 (prima pagina).",
                    required = false
            ) Integer page,
            @McpProgressToken String progressToken) {

        exchange.loggingNotification(McpSchema.LoggingMessageNotification.builder() // (3)
                .level(McpSchema.LoggingLevel.DEBUG)
                .data(String.format("Call lastResult with param with codiceIpa: %s", codiceIpa))
                .meta(Map.of())
                .build());

        RispostaPaginata<RisultatoValidazioneRegola> results = resultService.getLastResult(codiceIpa, page);
        log.info("Call lastResult with codiceIpa: {}, page: {}, found {} elementi",
                codiceIpa, page, results.numeroDiElementi());
        return results;
    }
}
