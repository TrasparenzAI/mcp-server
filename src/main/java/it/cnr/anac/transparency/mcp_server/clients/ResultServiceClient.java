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

import it.cnr.anac.transparency.mcp_server.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "resultServiceClient",
        url = "${result-service.base-url:https://dica33.ba.cnr.it/result-service}"
)
public interface ResultServiceClient {

    // ============= Result Controller (GET methods) =============

    @GetMapping(path = "/v1/results")
    PageResponse<ResultShowDto> listResults(
            @RequestParam(value = "idIpa", required = false) Long idIpa,
            @RequestParam(value = "codiceCategoria", required = false) String codiceCategoria,
            @RequestParam(value = "codiceFiscaleEnte", required = false) String codiceFiscaleEnte,
            @RequestParam(value = "codiceIpa", required = false) String codiceIpa,
            @RequestParam(value = "denominazioneEnte", required = false) String denominazioneEnte,
            @RequestParam(value = "ruleName", required = false) String ruleName,
            @RequestParam(value = "isLeaf", required = false) Boolean isLeaf,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            @RequestParam(value = "createdAfter", required = false) String createdAfter,
            @RequestParam(value = "noCache", required = false) Boolean noCache,
            // Pageable flattened
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) List<String> sort
    );

    @GetMapping(path = "/v1/results/{id}")
    ResultShowDto getResult(
            @PathVariable("id") Long id
    );

    @GetMapping(path = "/v1/results/storageData")
    List<StorageDataShowDto> listStorageDataByWorkflowId(
            @RequestParam("workflowId") String workflowId
    );

    @GetMapping(path = "/v1/results/lastRunAsCsv")
    String listLastRunAsCsv(
            @RequestParam(value = "terse", required = false) Boolean terse,
            @RequestParam(value = "sort") List<String> sort
    );

    @GetMapping(path = "/v1/results/lastResult")
    ResultShowDto lastResult();

    @GetMapping(path = "/v1/results/csv")
    String listAsCsv(
            @RequestParam(value = "idIpa", required = false) Long idIpa,
            @RequestParam(value = "codiceCategoria", required = false) String codiceCategoria,
            @RequestParam(value = "codiceFiscaleEnte", required = false) String codiceFiscaleEnte,
            @RequestParam(value = "codiceIpa", required = false) String codiceIpa,
            @RequestParam(value = "denominazioneEnte", required = false) String denominazioneEnte,
            @RequestParam(value = "ruleName", required = false) String ruleName,
            @RequestParam(value = "isLeaf", required = false) Boolean isLeaf,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "workflowId", required = false) String workflowId,
            @RequestParam(value = "createdAfter", required = false) String createdAfter,
            @RequestParam(value = "terse", required = false) Boolean terse,
            @RequestParam(value = "sort") List<String> sort
    );

    @GetMapping(path = "/v1/results/countResultsAndGroupByCategoriesWidthWorkflowIdAndStatus")
    List<CategoryValueDto> countResultsAndGroupByCategoriesWidthWorkflowIdAndStatus(
            @RequestParam("workflowId") String workflowId,
            @RequestParam("status") List<Integer> status
    );

    @GetMapping(path = "/v1/results/countAndGroupByWorkflowIdAndStatus")
    Map<String, Map<String, Long>> countAndGroupByWorkflowIdAndStatus(
            @RequestParam(value = "ruleName", required = false) String ruleName,
            @RequestParam(value = "workflowIds", required = false) List<String> workflowIds,
            @RequestParam(value = "noCache", required = false) Boolean noCache
    );

    @GetMapping(path = "/v1/results/companiesByWorkflowAndStatus")
    PageResponse<ResultShowDto> companiesByWorkflowAndStatusWithOccurencesBetween(
            @RequestParam("workflowId") String workflowId,
            @RequestParam("status") List<Integer> status,
            @RequestParam("minNumberOfRules") Integer minNumberOfRules,
            @RequestParam("maxNumberOfRules") Integer maxNumberOfRules,
            @RequestParam(value = "denominazioneEnte", required = false) String denominazioneEnte,
            @RequestParam(value = "codiceFiscaleEnte", required = false) String codiceFiscaleEnte,
            @RequestParam(value = "codiceIpa", required = false) String codiceIpa,
            @RequestParam(value = "codiceCategoria", required = false) String codiceCategoria,
            // Pageable flattened
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "sort") List<String> sort
    );

    @GetMapping(path = "/v1/results/codiceipa")
    PageResponse<ResultShowDto> listByCodiceIpa(
            @RequestParam("codiceIpa") String codiceIpa,
            @RequestParam(value = "noCache", required = false) Boolean noCache,
            // Pageable flattened
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "sort") List<String> sort
    );

    @GetMapping(path = "/v1/results/all")
    List<ResultShowDto> listAll(
            @RequestParam(value = "idIpa", required = false) Long idIpa,
            @RequestParam(value = "codiceCategoria", required = false) String codiceCategoria,
            @RequestParam(value = "codiceFiscaleEnte", required = false) String codiceFiscaleEnte,
            @RequestParam(value = "codiceIpa", required = false) String codiceIpa,
            @RequestParam(value = "denominazioneEnte", required = false) String denominazioneEnte,
            @RequestParam(value = "ruleName", required = false) String ruleName,
            @RequestParam(value = "isLeaf", required = false) Boolean isLeaf,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "workflowId") String workflowId,
            @RequestParam(value = "createdAfter", required = false) String createdAfter,
            @RequestParam(value = "sort") List<String> sort
    );
}