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
import it.cnr.anac.transparency.mcp_server.dto.PageResponse;
import it.cnr.anac.transparency.mcp_server.dto.ResultShowDto;
import it.cnr.anac.transparency.mcp_server.dto.RispostaPaginata;
import it.cnr.anac.transparency.mcp_server.dto.RisultatoValidazioneRegola;
import it.cnr.anac.transparency.mcp_server.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResultService {

    private final ConductorService conductorService;
    private final ResultServiceClient resultServiceClient;
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
        long validResults = 0;
        long notFoundResults = 0;
        long otherResults = 0;
        for (RisultatoValidazioneRegola r : results.getContenuto()) {
            if (r.stato() >= 200 && r.stato() <=299) {
                validResults = validResults++;
            }
            else if (r.stato() >= 400 && r.stato() <=499) {
                notFoundResults = notFoundResults++;
            } else {
                otherResults = otherResults++;
            }
        };
        String resoconto = String.format("In questa sono contenuti %d risultati di validazione. " +
                "Di questi risultati %d hanno uno stato valido (per esempio 200, 201,202) " +
                "e %d risultati con stato non trovato (per esempio 404).",
                results.getDimensioneDellaPagina(), validResults, notFoundResults);
        if (otherResults > 0) {
            resoconto = resoconto + String.format(" Inoltre sono presenti %d risultati con stato maggiore o uguale a 500.", otherResults);
        }
        return resoconto;
    }
}