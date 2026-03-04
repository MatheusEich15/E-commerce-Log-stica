package br.com.logistica.dto;

public record RelatorioEntregaMotoristaDTO(String nomeMotorista, int qtdEntregas) {
    @Override
    public String toString() {
        return "\nNOME: " + nomeMotorista + "\nENTREGAS TOTAIS: " + qtdEntregas + "\n<>---------------------------<>";
    }
}
