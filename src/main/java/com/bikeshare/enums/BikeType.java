package com.bikeshare.enums;

public enum BikeType {

    CLASSICBIKE(1),
    ELETRONICBIKE(2);

    private final int opcao;

    BikeType(int opcao) {
        this.opcao = opcao;
    }

    public int getOpcao() {
        return opcao;
    }

    public static BikeType type(int opcao) {
        for (BikeType tipo : values()) {
            if (tipo.opcao == opcao) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Opcao invalida" + opcao);
    }
}
