package it.cnr.anac.transparency.mcp_server.dto;

public record RisultatoValidazioneRegola(
        String urlEffettivo,
        String url,
        String nomeRegola,
        String termine,
        String contenuto,
        Integer stato,
        Double punteggio,
        String workflowId,
        String messaggioDiErrore,
        String dataUltimoAggiornamento,
        String urlDestinazione
) {
}
