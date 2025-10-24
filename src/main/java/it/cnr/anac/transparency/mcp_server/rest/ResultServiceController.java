package it.cnr.anac.transparency.mcp_server.rest;

import it.cnr.anac.transparency.mcp_server.dto.ResultShowDto;
import it.cnr.anac.transparency.mcp_server.services.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/results")
public class ResultServiceController {

    private final ResultService resultService;

    @GetMapping("{codiceIpa}/last")
    public ResponseEntity<List<ResultShowDto>> getLastResults(@PathVariable("codiceIpa") String codiceIpa) {
        return ResponseEntity.ok(resultService.getLastResult(codiceIpa));
    }
}
