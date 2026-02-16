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
package it.cnr.anac.transparency.mcp_server.mapper;

import it.cnr.anac.transparency.mcp_server.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    // ResultShowDto -> RisultatoValidazioneRegola
    @Mapping(target = "urlEffettivo", source = "realUrl")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "nomeRegola", source = "ruleName")
    @Mapping(target = "termine", source = "term")
    @Mapping(target = "contenuto", source = "content")
    @Mapping(target = "stato", source = "status")
    @Mapping(target = "workflowId", source = "workflowId")
    @Mapping(target = "messaggioDiErrore", source = "errorMessage")
    @Mapping(target = "dataUltimoAggiornamento", source = "updatedAt")
    @Mapping(target = "urlDestinazione", source = "destinationUrl")
    RisultatoValidazioneRegola toRisultatoValidazioneRegola(ResultShowDto source);

    // PageResponse<ResultShowDto> -> RispostaPaginata<RisultatoValidazioneRegola>
    @Mapping(target = "contenuto", source = "content")
    @Mapping(target = "numeroDiPagina", source = "number")
    @Mapping(target = "dimensioneDellaPagina", source = "size")
    @Mapping(target = "elementiTotali", source = "totalElements")
    @Mapping(target = "pagineTotali", source = "totalPages")
    @Mapping(target = "numeroDiElementi", source = "numberOfElements")
    RispostaPaginata<RisultatoValidazioneRegola> toRispostaPaginata(PageResponse<ResultShowDto> source);
}
