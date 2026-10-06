package com.bikeshare.application;

import com.bikeshare.entities.*;
import com.bikeshare.entities.dao.BikeDao;
import com.bikeshare.entities.dao.CustomerDao;
import com.bikeshare.entities.dao.RideDao;
import com.bikeshare.entities.dao.StationDao;
import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.BikeType;
import com.bikeshare.enums.RideStatus;
import com.bikeshare.persistence.JPAUtil;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //EntityManagerFactory emf = JPAUtil.getEmf();
        StationDao stationDao = new StationDao();
        BikeDao bikeDao = new BikeDao();
        CustomerDao customerDao = new CustomerDao();
        RideDao rideDao = new RideDao();

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
                    System.out.print("Nome: ");
                    name = sc.nextLine();
                    System.out.print("Endereco: ");
                    endereco = sc.nextLine();
                    System.out.print("Capacidade: ");
                    numb = sc.nextInt();
                    stationDao.salvar(new Station(name, endereco, numb));
                    System.out.println("Estação cadastrada!");
                    break;

                case 2:
                    System.out.println();
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
                    Station est =  stationDao.buscaPorId(stationId);

                    if (est == null) {
                        System.out.println("Estação não encontrada!");
                        break;
                    }

                    if (est.getBikes().size() < est.getCapacity()) {
                        Bike newBike = new Bike(tipo, tatus, num);
                        newBike.setStation(est);
                        bikeDao.salvar(newBike);
                        System.out.println("Bike cadastrada com sucesso!");
                    }else {
                        System.out.println("Estação sem capacidade disponível!");
                    }
                    break;

                case 3:
                    System.out.println();
                    System.out.print("Nome: ");
                    sc.nextLine();
                    name = sc.nextLine();
                    System.out.print("Email: ");
                    endereco = sc.nextLine();
                    System.out.print("Data de nascimento: ");
                    String data = sc.nextLine();
                    birth = LocalDate.parse(data, fmt);
                    customerDao.salvar(new Customer(name, endereco, birth));
                    break;

                case 4:
                    System.out.println();
                    System.out.print("Id da estação inicial: ");
                    int startStation = sc.nextInt();
                    est = stationDao.buscaPorId(startStation);
                    if (est == null) {
                        System.out.println("Estação nâo encontrada!");
                        break;
                    }
                    System.out.println(est.getName());
                    List<Bike> bikeDisp = bikeDao.buscarDisponiveisPorEstacao(est);
                    for (Bike bike : bikeDisp) {
                        System.out.println(bike);
                    }

                    System.out.print("Id da Bicicleta: ");
                    int iBike = sc.nextInt();
                    Bike bic = bikeDao.bucasPorId(iBike);
                    if (bic == null || bic.getStatus() != BikeStatus.DISPONIVEL) {
                        System.out.println("Bicicleta indisponível ou não encontrada!");
                        break;
                    }

                    System.out.print("Id do cliente: ");
                    int iClient = sc.nextInt();
                    Customer idCliente = customerDao.buscarPorId(iClient);
                    if (idCliente == null) {
                        System.out.println("Clinte não cadastrado!");
                        break;
                    }
                    rideService.startRide(bic, est, idCliente);
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
                    Ride rideUse = rideDao.buscarPorId(rideId);
                    if (rideUse == null) {
                        System.out.println("Ride não encontrada!");
                        break;
                    }

                    System.out.print("Id da estação final: ");
                    stationId = sc.nextInt();
                    est = stationDao.buscaPorId(stationId);
                    if (est == null) {
                        System.out.println("Estação não encontrada!");
                        break;
                    }
                    double precing = rideService.finishRide(rideUse, est);
                    System.out.println("Preço da corrida: " + precing);

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
        JPAUtil.close();
        }
    }