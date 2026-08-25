package com.bikeshare.enums;

public enum BikeStatus {

    DISPONIVEL(1),
    EM_USO(2),
    MANUTENCAO(3);

    private final int opcao;

    BikeStatus(int opcao) {
        this.opcao = opcao;
    }

    public int getOpcao() {
        return opcao;
    }

    public static BikeStatus porp(int opcao) {
        for (BikeStatus tipo : values()) {
            if (tipo.opcao == opcao) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Opcao invalida" + opcao);
    }

}
