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
package it.cnr.anac.transparency.mcp_server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CrawlerResponse (
        String htmlPage,
        String content,
        Long length,
        String realUrl,
        Integer result
){
    public CrawlerResponse {
        if (htmlPage != null) {
            content = base64Decode(htmlPage);
        }
    }

    String base64Decode(String content) {
        if (Base64.isBase64(content)) {
            content = new String(Base64.decodeBase64(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        } else if (content.contains("b'")) {
            content = new String(Base64.decodeBase64(content.replace("b'", "").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }
        return content;
    }

}

