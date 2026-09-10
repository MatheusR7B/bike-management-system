package com.bikeshare.application;

import com.bikeshare.entities.*;
import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.BikeType;
import com.bikeshare.enums.RideStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        Map<Integer, Ride> ride = new HashMap<>();
        Map<Integer, Customer> client = new HashMap<>();
        RideService rideService = new RideService();

        int proximoIdBike = 1;
        int proximoIdStation = 1;
        int proximoIdClient = 1;
        int idRine = 1;

        do {
            System.out.println();
            System.out.println("1 - Cadastrar estacao");
            System.out.println("2 - Cadastar bicicleta");
            System.out.println("3 - Cadastrar cliente");
            System.out.println("4 - Iniciar viajem");
            System.out.println("5 - Finalizar viajem");
            System.out.println("6 - Listar estações");
            System.out.println("7 - Relatório");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println();
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
                    System.out.println();
                    //int idBike = proximoIdBike;
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
                    Bike newBike = new Bike(proximoIdBike, tipo, tatus, num);
                    newBike.setStation(est);
                    bikes.put(proximoIdBike, newBike);
                    est.addBike(newBike);
                    proximoIdBike++;
                    break;

                case 3:
                    System.out.println();
                    int idClient = proximoIdClient;
                    System.out.print("Nome: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.print("Email: ");
                    endereco = sc.nextLine();
                    System.out.print("Data de nascimento: ");
                    String data = sc.nextLine();
                    birth = LocalDate.parse(data, fmt);
                    client.put(proximoIdClient, new Customer(proximoIdClient, name, endereco, birth));

                    break;

                case 4:
                    System.out.println();
                    System.out.print("Id da estação inicial: ");
                    int startStation = sc.nextInt();
                    est = station.get(startStation);
                    List<Bike> bikeDisp = bikes.values().stream().filter(b -> b.getStation() == est &&  b.getStatus() == BikeStatus.DISPONIVEL).toList();
                    System.out.println(est.getName());
                    for (Bike bike : bikeDisp) {
                        System.out.println(bike);
                    }

                    System.out.print("Id da Bicicleta: ");
                    int iBike = sc.nextInt();
                    Bike bic = bikes.get(iBike);

                    System.out.print("Id do cliente: ");
                    int iClient = sc.nextInt();
                    Customer idCliente = client.get(iClient);
                    Ride novaRide = rideService.startRide(idRine, bic, est, idCliente);
                    ride.put(idRine, novaRide);
                    idRine++;

                    break;

                case 5:
                    System.out.println();
                    List<Ride> listRide = ride.values().stream().filter(b -> b.getStatus() == RideStatus.EM_ANDAMENTO).toList();
                    for (Ride rid : listRide) {
                        System.out.println(rid);
                    }

                    System.out.println();
                    System.out.print("Id da ride: ");
                    int rideId = sc.nextInt();
                    Ride rideUse = ride.get(rideId);

                    System.out.print("Id da estação final: ");
                    stationId = sc.nextInt();
                    est = station.get(stationId);
                    double precing = rideService.finishRide(rideUse, est);
                    System.out.println(precing);

                    break;

                case 6:
                    System.out.println();
                    for (Station station1 : station.values()) {
                        System.out.println();
                        System.out.println(station1.getName());
                        for (Bike bike : station1.getBikes()) {
                            System.out.println(bike);
                        }
                    }
                    break;

                case 7:

                    Map<BikeStatus, Long> countStatus = bikes.values().stream().collect(Collectors.groupingBy(Bike::getStatus, Collectors.counting()));

                    Map<Bike, Long> countBike = ride.values().stream().filter(b -> b.getStatus() == RideStatus.FINALIZADA).collect(Collectors.groupingBy(Ride::getBike, Collectors.counting()));
                    Optional<Map.Entry<Bike, Long>> maxBikeEntry = countBike.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));

                    Map<Station, Long> countStation = ride.values().stream().collect(Collectors.groupingBy(Ride::getStartStation, Collectors.counting()));
                    Optional<Map.Entry<Station, Long>> maxStationEntry = countStation.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));

                    List<Ride> listRideFinish = ride.values().stream().filter(b -> b.getStatus() == RideStatus.FINALIZADA).toList();
                    Long avgDuration = Math.round(listRideFinish.stream().mapToLong(Ride::getDuration).average().orElse(0.0));

                    System.out.println("========== RELATÓRIO ==========");
                    System.out.println();
                    System.out.println("Total de bicicletas: " + bikes.size());
                    System.out.println("Disponiveis: " + countStatus.get(BikeStatus.DISPONIVEL));
                    System.out.println("Em uso: " + countStatus.get(BikeStatus.EM_USO));
                    System.out.println("Manutenção: " + countStatus.get(BikeStatus.MANUTENCAO));
                    System.out.println();
                    System.out.println("Viagens realizadas: " + ride.values().stream().filter(b -> b.getStatus() == RideStatus.FINALIZADA).count());
                    System.out.println();
                    if (maxBikeEntry.isPresent()) {
                        System.out.println("Bicicleta mais utilizada: ");
                        Map.Entry<Bike, Long> entry = maxBikeEntry.get();
                        System.out.println("#" + entry.getKey().getId() + " - " + entry.getValue() + " viagens");
                    } else {
                        System.out.println("Nenhuma bicicleta utilizada!");
                    }

                    if (maxStationEntry.isPresent()) {
                        System.out.println("Estação mais movimentada: ");
                        Map.Entry<Station, Long> entry = maxStationEntry.get();
                        System.out.println(entry.getKey().getName() + " - " + entry.getValue() + " retiradas");
                    } else {
                        System.out.println("Nenhuma viagem realizada!");
                    }

                    System.out.println("Tempo médio das viagens: ");
                    System.out.println(avgDuration + " minutos");
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