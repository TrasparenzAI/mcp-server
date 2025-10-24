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
import it.cnr.anac.transparency.mcp_server.dto.ResultShowDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultService {

    private final ConductorService conductorService;
    private final ResultServiceClient resultServiceClient;

    public List<ResultShowDto> getLastResult(String codiceIpa) {
        val lastWorkflow = conductorService.getLastWorkflow(codiceIpa);
        if (lastWorkflow.isEmpty()) {
            return List.of();
        }
        return resultServiceClient.listByCodiceIpa(
                codiceIpa,lastWorkflow.get().workflowId(), false,
                null, null, null).content();
    }
}
