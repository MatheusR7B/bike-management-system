package com.bikeshare.application;

import com.bikeshare.entities.Bike;
import com.bikeshare.entities.Station;
import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.BikeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao;
        int id;
        String name;
        String endereco;
        int numb;
        LocalDate birth;
        Map<Integer, Station> station = new HashMap<>();
        Map<Integer, Bike> bikes = new HashMap<>();

        int proximoIdBike = 1;
        int proximoIdStation = 1;
        int proximoIdClient = 1;

        do {
            System.out.println("1 - Cadastrar estacao");
            System.out.println("2 - Cadastar bicicleta");
            System.out.println("3 - Cadastrar cliente");
            System.out.println("4 - ");
            System.out.println("5 - ");
            System.out.println("6 - Listar bicicletas");
            System.out.println("7 - Listar estações");

            System.out.println("0 - Sair");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    sc.nextLine();
                    int idStation = proximoIdStation;
                    System.out.print("Nome: ");
                    name = sc.nextLine();
                    System.out.print("Endereco: ");
                    endereco = sc.nextLine();
                    System.out.print("Capacidade: ");
                    numb = sc.nextInt();
                    station.put(idStation, new Station(idStation, name, endereco, numb));
                    proximoIdStation++;
                    break;

                case 2:
                    int idBike = proximoIdBike;
                    System.out.print("Modelo: ");
                    int model = sc.nextInt();
                    sc.nextLine();
                    BikeType tipo = BikeType.type(model);
                    System.out.print("Status: ");
                    int status = sc.nextInt();
                    BikeStatus tatus = BikeStatus.porp(status);
                    System.out.print("Quilometragem: ");
                    Double num = sc.nextDouble();

                    System.out.print("Id da estação: ");
                    int stationId = sc.nextInt();
                    Station est = station.get(stationId);

                    if (est == null) {
                        System.out.println("Estação não encontrada!");
                        break;
                    }
                    Bike newBike = new Bike(idBike, tipo, tatus, num);
                    bikes.put(idBike, newBike);
                    est.addBike(newBike);
                    proximoIdBike++;
                    break;

                case 3:
                    int idClient = proximoIdClient;
                    System.out.print("Nome: ");
                    name = sc.nextLine();
                    System.out.print("Email: ");
                    endereco = sc.nextLine();
                    System.out.print("Data de nascimento: ");
                    String data = sc.nextLine();
                    birth = LocalDate.parse(data, fmt);
                    break;

                case 7:
                    for (Station station1 : station.values()) {
                        System.out.println(station1.toString());
                    }
                    break;

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