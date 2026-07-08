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
package it.cnr.anac.transparency.mcp_server.services;

import it.cnr.anac.transparency.mcp_server.clients.ResultServiceClient;
import it.cnr.anac.transparency.mcp_server.clients.RuleServiceClient;
import it.cnr.anac.transparency.mcp_server.dto.PageResponse;
import it.cnr.anac.transparency.mcp_server.dto.ResultShowDto;
import it.cnr.anac.transparency.mcp_server.dto.RispostaPaginata;
import it.cnr.anac.transparency.mcp_server.dto.RisultatoValidazioneRegola;
import it.cnr.anac.transparency.mcp_server.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ResultService {

    private final ConductorService conductorService;
    private final ResultServiceClient resultServiceClient;
    private final RuleServiceClient ruleServiceClient;
    private final DtoMapper dtoMapper;

    @Value("${result-service.max-results}")
    Integer maxResults;

    public RispostaPaginata<RisultatoValidazioneRegola> getLastResult(String codiceIpa, Integer page) {
        PageResponse<ResultShowDto> response = resultServiceClient.listByCodiceIpa(
                codiceIpa, false,
                page, maxResults, null);
        val result = dtoMapper.toRispostaPaginata(response);
        result.setResoconto(getResoconto(result));
        return result;
    }


    private String getResoconto(RispostaPaginata<RisultatoValidazioneRegola> results) {
        Integer numeroTotaleRegole = results.getContenuto()
                .stream()
                .findAny()
                .map(risultatoValidazioneRegola ->
                        resultServiceClient.listWorkflow(risultatoValidazioneRegola.workflowId(), null, null)
                                .content()
                                .stream()
                                .findAny()
                                .map(workflowDto -> ruleServiceClient.count(workflowDto.rootRule()))
                                .orElse(results.getNumeroDiElementi()))
                .orElse(results.getNumeroDiElementi());

        Map<HttpStatus.Series, Long> conteggioPerSerie = results.getContenuto()
                .stream()
                .collect(Collectors.groupingBy(
                        r -> Optional.ofNullable(HttpStatus.Series.resolve(r.stato()))
                                .orElse(HttpStatus.Series.SERVER_ERROR),
                        Collectors.counting()));

        long validResults = conteggioPerSerie.getOrDefault(HttpStatus.Series.SUCCESSFUL, 0L);
        long notFoundResults = numeroTotaleRegole - validResults;

        return String.format(
                "%d su %d sezioni sono correttamente pubblicate (stato 200 o 202). "
                        + "%d sezioni sono mancanti (stato 400 o 404 o 500)",
                validResults, numeroTotaleRegole, notFoundResults);
    }
}
