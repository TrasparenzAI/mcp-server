package it.cnr.anac.transparency.mcp_server.rest;

import it.cnr.anac.transparency.mcp_server.dto.RispostaPaginata;
import it.cnr.anac.transparency.mcp_server.dto.RisultatoValidazioneRegola;
import it.cnr.anac.transparency.mcp_server.services.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/results")
public class ResultServiceController {

    private final ResultService resultService;

    @GetMapping("{codiceIpa}/last")
    public ResponseEntity<RispostaPaginata<RisultatoValidazioneRegola>> getLastResults(
            @PathVariable("codiceIpa") String codiceIpa,
            @RequestParam(value = "page", required = false) Integer page) {
        return ResponseEntity.ok(resultService.getLastResult(codiceIpa, page));
    }
}
