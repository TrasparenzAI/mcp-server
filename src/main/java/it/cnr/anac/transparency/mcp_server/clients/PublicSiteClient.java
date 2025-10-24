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

import it.cnr.anac.transparency.mcp_server.dto.CompanyDto;
import it.cnr.anac.transparency.mcp_server.dto.PageResponse;
import it.cnr.anac.transparency.mcp_server.dto.CompanyAddressDto;
import it.cnr.anac.transparency.mcp_server.dto.MunicipalityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "publicSitesClient",
        url = "${public-sites.base-url:https://dica33.ba.cnr.it/public-sites-service}"
)
public interface PublicSiteClient {

    // ============= Company Controller (READ methods) =============

    @GetMapping(path = "/v1/companies")
    PageResponse<CompanyDto> listCompanies(
            @RequestParam(value = "codiceCategoria", required = false) String codiceCategoria,
            @RequestParam(value = "codiceFiscaleEnte", required = false) String codiceFiscaleEnte,
            @RequestParam(value = "codiceIpa", required = false) String codiceIpa,
            @RequestParam(value = "denominazioneEnte", required = false) String denominazioneEnte,
            @RequestParam(value = "comune", required = false) String comune,
            @RequestParam(value = "provincia", required = false) String provincia,
            @RequestParam(value = "idIpaFrom", required = false) Long idIpaFrom,
            @RequestParam(value = "withoutAddress", required = false) Boolean withoutAddress,
            @RequestParam(value = "regione", required = false) String regione,
            // Pageable flattened
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) List<String> sort
    );

    @GetMapping(path = "/v1/companies/{id}")
    CompanyDto getCompany(@PathVariable("id") Long id);

    @GetMapping(path = "/v1/companies/{id}/address")
    CompanyAddressDto getCompanyAddress(@PathVariable("id") Long id);

    // ============= Municipalities (READ methods) =============

    @GetMapping(path = "/v1/municipalities")
    PageResponse<MunicipalityDto> listMunicipalities(
            @RequestParam(value = "codiceCatastale", required = false) String codiceCatastale,
            @RequestParam(value = "codiceComune", required = false) String codiceComune,
            @RequestParam(value = "denominazione", required = false) String denominazione,
            @RequestParam(value = "denominazioneAltraLingua", required = false) String denominazioneAltraLingua,
            @RequestParam(value = "denominazioneRegione", required = false) String denominazioneRegione,
            @RequestParam(value = "ripartizioneGeografica", required = false) String ripartizioneGeografica,
            @RequestParam(value = "siglaAutomobilistica", required = false) String siglaAutomobilistica,
            // Pageable flattened
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) List<String> sort
    );

    @GetMapping(path = "/v1/municipalities/{id}")
    MunicipalityDto getMunicipality(@PathVariable("id") Long id);

    // ============= Geo (READ methods) =============

    @GetMapping(path = "/v1/geo/geojson")
    Map<String, Object> getGeoJson();

    @GetMapping(path = "/v1/geo/geoCompanyAddresses/{id}")
    List<Map<String, Object>> getGeoCompanyAddresses(@PathVariable("id") Long id
    );

    @GetMapping(path = "/v1/geo/geoCompanyAddress/{id}")
    Map<String, Object> getGeoCompanyAddressBest(@PathVariable("id") Long id);
}
