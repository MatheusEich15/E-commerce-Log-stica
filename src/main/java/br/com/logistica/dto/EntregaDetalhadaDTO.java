package br.com.logistica.dto;

import java.sql.Timestamp;

public record EntregaDetalhadaDTO(int id, int pedidoId, String nomeCliente, String nomeMotorista, Timestamp dataSaida, Timestamp dataEntrega, String status) {
    @Override
    public String toString() {
        return "\nID: " + id + "\nPEDIDO ID: " + pedidoId + "\nNOME CLIENTE: " + nomeCliente + "\nNOME MOTORISTA: " + nomeMotorista + "\nDATA SAIDA: " + dataSaida + "\nDATA ENTREGA: " + dataEntrega + "\nSTATUS: " + status + "\n<>---------------------------<>";
    }
}
