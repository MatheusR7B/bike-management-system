package com.bikeshare.application;

import com.bikeshare.entities.*;
import com.bikeshare.entities.dao.BikeDao;
import com.bikeshare.entities.dao.CustomerDao;
import com.bikeshare.entities.dao.RideDao;
import com.bikeshare.entities.dao.StationDao;
import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.BikeType;
import com.bikeshare.persistence.JPAUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StationDao stationDao = new StationDao();
        BikeDao bikeDao = new BikeDao();
        CustomerDao customerDao = new CustomerDao();
        RideDao rideDao = new RideDao();
        RideService rideService = new RideService();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao;
        String name;
        String endereco;

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
                    int numb = sc.nextInt();
                    stationDao.salvar(new Station(name, endereco, numb));
                    System.out.println("Estação cadastrada!");
                    break;

                case 2:
                    System.out.println();
                    System.out.print("Modelo: ");
                    int model = sc.nextInt();
                    sc.nextLine();
                    BikeType tipo = BikeType.type(model);
                    System.out.print("Quilometragem: ");
                    Double num = sc.nextDouble();

                    System.out.print("Id da estação: ");
                    int stationId = sc.nextInt();
                    Station est =  stationDao.buscaPorId(stationId);

                    if (est == null) {
                        System.out.println("Estação não encontrada!");
                        break;
                    }

                    if (bikeDao.countForStation(est) < est.getCapacity()) {
                        Bike newBike = new Bike(tipo, BikeStatus.porp(1), num);
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
                    LocalDate birth = LocalDate.parse(data, fmt);
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
                    if (bic == null || bic.getStatus() != BikeStatus.DISPONIVEL || bic.getStation() != est) {
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
                    List<Ride> listRide = rideDao.rideEmAndamento();
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
                    if (bikeDao.countForStation(est) < est.getCapacity()){
                        System.out.println("Estação lotada!");
                        break;
                    }
                    double precing = rideService.finishRide(rideUse, est);
                    System.out.println("Preço da corrida: " + precing);

                    break;

                case 6:
                    System.out.println();
                    for (Station station1 : stationDao.todasStation()) {
                        System.out.println();
                        System.out.println(station1.getName());
                        for (Bike bike : bikeDao.buscarPorEstacao(station1)) {
                            System.out.println(bike);
                        }
                    }
                    break;

                case 7:

                    System.out.println("========== RELATÓRIO ==========");
                    System.out.println();
                    System.out.println("Total de bicicletas: " + bikeDao.totalBike());
                    System.out.println("Disponiveis: " + bikeDao.countStatus(BikeStatus.DISPONIVEL));
                    System.out.println("Em uso: " + bikeDao.countStatus(BikeStatus.EM_USO));
                    System.out.println();
                    System.out.println("Viagens realizadas: " + rideDao.finishRide());
                    System.out.println();

                    List<Object[]> result = rideDao.bikeMaisUsada();
                    if (!result.isEmpty()) {
                        Object[] primeira = result.get(0);
                        Bike bikeMaisUsada = (Bike) primeira[0];
                        Long vezesUsada = (Long) primeira[1];

                        System.out.println("Bicicleta mais utilizada: ");
                        System.out.println("#" + bikeMaisUsada.getId() + " - " + vezesUsada + " viagens");
                    } else {
                        System.out.println("Nenhuma viagem realizada!");
                    }

                    result = rideDao.estacaoMaisUsada();
                    if(!result.isEmpty()) {
                        Object[] primeira = result.get(0);
                        Station stationMaisUsada = (Station) primeira[0];
                        Long vezesUsada = (Long) primeira[1];

                        System.out.println("Estação mais movimentada: ");
                        System.out.println(stationMaisUsada.getName() + " - " + vezesUsada + " retiradas");
                    }

                    System.out.println("Tempo médio das viagens: ");
                    System.out.println(rideDao.avgDuration() + " minutos");
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