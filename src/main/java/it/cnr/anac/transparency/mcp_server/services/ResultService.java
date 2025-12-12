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
        return dtoMapper.toRispostaPaginata(response);
    }
}