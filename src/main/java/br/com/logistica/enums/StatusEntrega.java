package br.com.logistica.enums;

public enum StatusEntrega {
    EMROTA("Em rota"),
    ENTREGUE("Entregue"),
    ATRASADA("Atrasada");

    private final String name;

    StatusEntrega(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
