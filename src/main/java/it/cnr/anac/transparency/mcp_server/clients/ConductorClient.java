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
package it.cnr.anac.transparency.mcp_server.clients;

import it.cnr.anac.transparency.mcp_server.dto.WorkflowDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "conductorClient",
        url = "${conductor.base-url:https://monitorai.ba.cnr.it/conductor-server}"
)
public interface ConductorClient {

    @GetMapping("/api/workflow/crawler_amministrazione_trasparente/correlated/crawler_amministrazione_trasparente?includeClosed=true&includeTasks=false")
    public List<WorkflowDto> getWorkflowCrawlerAmministrazioneTrasparente();

    @GetMapping("/api/workflow/crawler_amministrazione_trasparente/correlated/{code}?includeClosed=true&includeTasks=false")
    public List<WorkflowDto> getWorkflow(@PathVariable("code") String code);
}
