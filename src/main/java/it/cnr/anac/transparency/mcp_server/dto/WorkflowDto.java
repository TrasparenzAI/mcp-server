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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WorkflowDto(
        @JsonDeserialize(using = TimestampToLocalDateTimeDeserializer.class) // <-- AGGIUNGI QUESTO
        LocalDateTime createTime, // epoch millis, e.g., 1761148800318
        @JsonDeserialize(using = TimestampToLocalDateTimeDeserializer.class) // <-- AGGIUNGI QUESTO
        LocalDateTime updateTime, // epoch millis, e.g., 1761213641329
        String status, /* "COMPLETED" */
        String codiceIpa,
        String rootRule,
        @JsonDeserialize(using = TimestampToLocalDateTimeDeserializer.class) // <-- AGGIUNGI QUESTO
        LocalDateTime endTime, // epoch millis, e.g., 1761252944000
        String workflowId // "328b5403-5fb3-4351-8b6e-692abcb5b3c8"
) {
}
