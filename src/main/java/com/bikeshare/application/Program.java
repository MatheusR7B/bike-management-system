package com.bikeshare.application;

import com.bikeshare.entities.Station;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int opcao;
        int id;
        String name;
        String endereco;
        int numb;
        Set<Station> station = new HashSet<>();

        do {
            System.out.println("1 - Cadastrar estacao");
            System.out.println("2 - Cadastar bicicleta");
            System.out.println("3 - Cadastrar cliente");

            System.out.println("0 - Sair");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Id: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nome: ");
                    name = sc.nextLine();
                    System.out.print("Endereco: ");
                    endereco = sc.nextLine();
                    System.out.print("Capacidade: ");
                    numb = sc.nextInt();
                    station.add(new Station(id, name, endereco, numb));
                    break;

                case 2:
                    System.out.println("2 - Cadastrar bicicleta");
                    break;

                case 3:
                    for (Station c : station) {
                    System.out.println(c);
                }

                case 0:
                    System.out.println("Finalizando programa...");
                    break;

                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
        sc.close();
        }
    }