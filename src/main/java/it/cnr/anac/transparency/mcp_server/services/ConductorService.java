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

import it.cnr.anac.transparency.mcp_server.clients.ConductorClient;
import it.cnr.anac.transparency.mcp_server.dto.WorkflowDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConductorService {

    private final ConductorClient conductorClient;

    public Optional<WorkflowDto> getLastWorkflow(String codiceIpa) {
        val specificWorkflow = conductorClient.getWorkflow(codiceIpa);
        if (specificWorkflow != null && !specificWorkflow.isEmpty()) {
            return specificWorkflow.stream()
                    .max(Comparator.comparing(WorkflowDto::endTime));
        }
        else {
            val fullWorkflow = conductorClient.getWorkflowCrawlerAmministrazioneTrasparente();
            return fullWorkflow.stream()
                    .max(Comparator.comparing(WorkflowDto::endTime));
        }
    }
}