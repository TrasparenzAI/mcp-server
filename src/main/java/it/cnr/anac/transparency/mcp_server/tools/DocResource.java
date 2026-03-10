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
package it.cnr.anac.transparency.mcp_server.tools;

import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Component;

@Component
public class DocResource {

    @McpResource(uri = "mcp://docs/manuale-integrazione.md", name = "Manuale Tecnico Tool TrasparenzAI")
    public String getManual() {
        return """
            # Linee Guida per l'uso dei Tool di questo MCP Server
            - Utilizza il tool 'Ultimi risultati controllo trasparenza' solo dopo aver prelevato il codiceIpa della pubblica amministrazione.
            - Utilizza il tool 'Elenco delle Pubbliche Amministrazioni' sia cerca il codiceIpa di una singola amministrazione
              che per ottenere un elenco di pubbliche amministrazioni (con i relativi codiciIpa)
            - Utilizza 'Elenco delle Pubbliche Amministrazioni' anche per cerca la lista delle pubbliche amministrazione che appartengono
              a un comune, provincia o regione.
            - Nei risultati ottenuti dal tool 'Ultimi risultati controllo trasparenza' considera come validi sia lo stato 200 che 201 e 202. 
            - Nei risultati ottenuti dal tool 'Ultimi risultati controllo trasparenza' lo stato 202 significa che la sezione della trasparenza
              è stata individuata ma con un testo alternativo, considera comunque lo stato 202 come valido.
            """;
    }
}