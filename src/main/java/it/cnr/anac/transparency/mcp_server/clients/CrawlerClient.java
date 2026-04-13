/*
 * Copyright (C) 2026 Consiglio Nazionale delle Ricerche
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

import it.cnr.anac.transparency.mcp_server.dto.CrawlerRequest;
import it.cnr.anac.transparency.mcp_server.dto.CrawlerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "crawlerClient",
        url = "${crawler.base-url:http://dica33crawler-test.ba.cnr.it:8080}"
)
public interface CrawlerClient {

    @PostMapping("/crawl")
    CrawlerResponse fetchURL(@RequestBody CrawlerRequest request);
}
