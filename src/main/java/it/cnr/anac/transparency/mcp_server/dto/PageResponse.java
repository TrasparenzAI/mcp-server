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
package it.cnr.anac.transparency.mcp_server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Rappresenta una pagina di risultati come comunemente restituita dai servizi Spring.
 * Campi aggiuntivi eventuali verranno ignorati.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record PageResponse<T>(
    List<T> content,
    Integer number,
    Integer size,
    Long totalElements,
    Integer totalPages,
    Boolean first,
    Boolean last,
    Integer numberOfElements
) {

    /**
     * Crea una {@code PageResponse} vuota con:
     * - contenuti: lista vuota
     * - numero pagina: 0
     * - size: 0
     * - totalElements: 0
     * - totalPages: 1
     * - first: true (prima pagina)
     * - last: true (ultima pagina)
     * - numberOfElements: 0
     */
    public static <T> PageResponse<T> empty() {
        return new PageResponse<>(
            List.of(),
            0,
            0,
            0L,
            1,
            true,
            true,
            0
        );
    }
}
