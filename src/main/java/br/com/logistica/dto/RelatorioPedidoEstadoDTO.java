package br.com.logistica.dto;

public record RelatorioPedidoEstadoDTO(String estado, int qtdPedidos) {
    @Override
    public String toString() {
        return "\nESTADO: " + estado + "\nQUANTIDADE PEDIDOS: " + qtdPedidos + "\n<>---------------------------<>";
    }
}
