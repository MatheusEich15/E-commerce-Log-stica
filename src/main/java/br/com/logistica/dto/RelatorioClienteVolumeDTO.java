package br.com.logistica.dto;

public record RelatorioClienteVolumeDTO(String nomeCliente, double volumeM3) {
    @Override
    public String toString() {
        return "\nNOME: " + nomeCliente + "\nVOLUME M³: " + volumeM3 + "\n<>---------------------------<>";
    }
}
