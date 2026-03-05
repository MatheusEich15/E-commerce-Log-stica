package br.com.logistica.dto;

import java.sql.Timestamp;

public record BuscarPedidoCPFDTO(String nomeCliente, String CPF, int id, Timestamp dataPedido, double volumeM3, int pesoKg, String status) {
    @Override
    public String toString() {
        return "\nNOME: " + nomeCliente + "\nCPF / CNPJ: " + CPF + "\nID: " + id + "\nDATA PEDIDO: " + dataPedido + "\nVOLUME M³: " + volumeM3 + "\nPESO KG: " + pesoKg + "\nSTATUS: " + status + "\n<>---------------------------<>";
    }
}