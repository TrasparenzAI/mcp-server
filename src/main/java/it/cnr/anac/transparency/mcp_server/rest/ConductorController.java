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
package it.cnr.anac.transparency.mcp_server.rest;

import it.cnr.anac.transparency.mcp_server.clients.ConductorClient;
import it.cnr.anac.transparency.mcp_server.dto.WorkflowDto;
import it.cnr.anac.transparency.mcp_server.services.ConductorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/conductor")
public class ConductorController {

    private final ConductorClient conductorClient;
    private final ConductorService conductorService;

    @GetMapping("/workflows/{codiceIpa}")
    public ResponseEntity<List<WorkflowDto>> getWorkflows(@NotNull @PathVariable("codiceIpa") String code) {
        return ResponseEntity.ok(conductorClient.getWorkflow(code));
    }

    @GetMapping("/workflows/default")
    public ResponseEntity<List<WorkflowDto>> getWorkflows() {
        return ResponseEntity.ok(conductorClient.getWorkflowCrawlerAmministrazioneTrasparente());
    }

    @GetMapping("/workflows/{codiceIpa}/last")
    public ResponseEntity<Optional<WorkflowDto>> getLastWorkflowId(@NotNull @PathVariable("codiceIpa") String codiceIpa) {
        return ResponseEntity.ok(conductorService.getLastWorkflow(codiceIpa));
    }
}
