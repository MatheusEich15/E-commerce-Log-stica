package br.com.logistica.dto;

public record RelatorioEntregasAtrasadasDTO(String cidade, int qtdAtrasadas) {
    @Override
    public String toString() {
        return "\nCIDADE: " + cidade + "\nENTREGAS ATRASADAS: " + qtdAtrasadas + "\n<>---------------------------<>";
    }
}
