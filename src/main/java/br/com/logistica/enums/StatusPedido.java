package br.com.logistica.enums;

public enum StatusPedido {
    PENDENTE("Pendente"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String name;

    StatusPedido(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
