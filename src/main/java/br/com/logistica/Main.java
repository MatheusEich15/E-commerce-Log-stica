package br.com.logistica;

import br.com.logistica.dto.*;
import br.com.logistica.enums.StatusEntrega;
import br.com.logistica.dao.*;
import br.com.logistica.enums.StatusPedido;
import br.com.logistica.model.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static final ClienteDAO CLIENTEDAO = new ClienteDAO();
    static final EntregaDAO ENTREGADAO = new EntregaDAO();
    static final HistoricoEntregaDAO HISTORICOENTREGADAO = new HistoricoEntregaDAO();
    static final MotoristaDAO MOTORISTADAO = new MotoristaDAO();
    static final PedidoDAO PEDIDODAO = new PedidoDAO();
    static final Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {inicio();}
    public static void inicio() {
        while (true) {
            System.out.println("""
                    
                    1 - Cadastrar Cliente
                    2 - Cadastrar Motorista
                    3 - Criar Pedido
                    4 - Atribuir Pedido a Motorista
                    5 - Registrar Evento de Entrega
                    6 - Atualizar Status da Entrega
                    7 - Listar Todas as Entregas com Cliente e Motorista
                    8 - Relatório: Total de Entregas por Motorista
                    9 - Relatório: Clientes com Maior Volume Entregue
                    10 - Relatório: Pedidos Pendentes por Estado
                    11 - Relatório: Entregas Atrasadas por Cidade
                    12 - Buscar Pedido por CPF/CNPJ do Cliente
                    13 - Cancelar Pedido
                    14 - Excluir Entrega
                    15 - Excluir Cliente
                    16 - Excluir Motorista
                    0 - Sair
                    
                    Insira a opção desejada:
                    """);
            int opcao = SC.nextInt();
            switch (opcao) {
                case 1: {
                    cadastrarCliente();
                    break;
                }
                case 2: {
                    cadastrarMotorista();
                    break;
                }
                case 3: {
                    criarPedido();
                    break;
                }
                case 4: {
                    atribuirPedidoMotorista();
                    break;
                }
                case 5: {
                    registrarEventoEntrega();
                    break;
                }
                case 6: {
                    atualizarStatusEntrega();
                    break;
                }
                case 7: {
                    listarTodasEntregasClienteMotorista();
                    break;
                }
                case 8: {
                    relatorioTotalEntregasMotorista();
                    break;
                }
                case 9: {
                    relatorioClienteMaiorVolumeEntregue();
                    break;
                }
                case 10: {
                    relatorioPedidosPendentesEstado();
                    break;
                }
                case 11: {
                    relatorioEntregasAtrasadasCidade();
                    break;
                }
                case 12: {
                    buscarPedidoCPFCliente();
                    break;
                }
                case 13: {
                    //cancelarPedido();
                    break;
                }
                case 14: {
                    //excluirEntrega();
                    break;
                }
                case 15: {
                    //excluirCliente();
                    break;
                }
                case 16: {
                    //excluirMotorista();
                    break;
                }
                case 0: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("ERRO!!!");
                }
            }
        }
    }

    public static void cadastrarCliente() {
        System.out.println("Tem certeza que deseja cadastrar um novo cliente?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                System.out.println("Insira o nome do cliente:");
                String nome = SC.nextLine();
                System.out.println("Insira o CPF ou CNPJ do cliente:");
                String cpfCnpj = SC.nextLine();
                System.out.println("Insira o endereco do cliente:");
                String endereco = SC.nextLine();
                System.out.println("Insira a cidade do cliente:");
                String cidade = SC.nextLine();
                System.out.println("Insira o estado do cliente:");
                String estado = SC.nextLine();
                Cliente cliente = new Cliente(nome, cpfCnpj, endereco, cidade, estado);
                try {
                    CLIENTEDAO.cadastrarCliente(cliente);
                    System.out.println("Cliente cadastrado!");
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void cadastrarMotorista() {
        System.out.println("Tem certeza que deseja cadastrar um novo motorista?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                System.out.println("Insira o nome do motorista:");
                String nome = SC.nextLine();
                System.out.println("Insira a CNH do motorista:");
                String cnh = SC.nextLine();
                System.out.println("Insira o veículo do motorista:");
                String veiculo = SC.nextLine();
                System.out.println("Insira a cidade base do motorista:");
                String cidadeBase = SC.nextLine();
                Motorista motorista = new Motorista(nome, cnh, veiculo, cidadeBase);
                try {
                    MOTORISTADAO.cadastrarMotorista(motorista);
                    System.out.println("Motorista cadastrado!");
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void criarPedido() {
        List<Cliente> clientes = new ArrayList<>();
        System.out.println("Tem certeza que deseja criar um novo pedido?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                try {
                    clientes = CLIENTEDAO.listarClientesBasico();
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                for (Cliente cliente : clientes) {
                    System.out.println(cliente.toStringBasico());
                }
                System.out.println("\nInsira o ID do cliente:");
                int clienteId = SC.nextInt();
                System.out.println("Insira o volume do pedido (m³):");
                double volumeM3 = SC.nextDouble();
                System.out.println("Insira o peso do pedido (Kg):");
                int pesoKg = SC.nextInt();
                Timestamp dataPedido = new Timestamp(System.currentTimeMillis());
                String status = "PENDENTE";
                Pedido pedido = new Pedido(clienteId, dataPedido, volumeM3, pesoKg, status);
                try {
                    PEDIDODAO.criarPedido(pedido);
                    System.out.println("Pedido criado!");
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void atribuirPedidoMotorista() {
        List<Pedido> pedidos = new ArrayList<>();
        List<Motorista> motoristas = new ArrayList<>();
        System.out.println("Tem certeza que deseja atribuir um pedido a um motorista?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                try {
                    pedidos = PEDIDODAO.listarPedidos();
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                for (Pedido pedido : pedidos) {
                    System.out.println(pedido.toString());
                }
                System.out.println("Insira o ID do pedido:");
                int pedidoId = SC.nextInt();
                try {
                    motoristas = MOTORISTADAO.listarMotoristas();
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                for (Motorista motorista : motoristas) {
                    System.out.println(motorista.toString());
                }
                System.out.println("Insira o ID do motorista:");
                int motoristaId = SC.nextInt();
                Timestamp dataSaida = new Timestamp(System.currentTimeMillis());
                String status = "EM_ROTA";
                System.out.println("Tem certeza que deseja atribuir este pedido ao motorista?" +
                        "\n1 - SIM" +
                        "\n2 - NÃO");
                int confirma = SC.nextInt();
                switch (confirma) {
                    case 1: {
                        Entrega entrega = new Entrega(pedidoId, motoristaId, dataSaida, status);
                        try {
                            ENTREGADAO.criarEntrega(entrega);
                            System.out.println("Pedido atribuido ao motorista e foi enviado!");
                        } catch (Exception e) {
                            System.out.println("Erro ao conectar com o banco de dados!");
                            e.printStackTrace();
                        }
                    }
                    case 2: {
                        System.out.println("Retornando ao menu...");
                        return;
                    }
                    default: {
                        System.out.println("ERRO!!!");
                        break;
                    }
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void registrarEventoEntrega() {
        List<Entrega> entregas = new ArrayList<>();
        System.out.println("Tem certeza que deseja registrar um evento?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                try {
                    entregas = ENTREGADAO.listarEntregas();
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                for (Entrega entrega : entregas) {
                    System.out.println(entrega.toString());
                }
                System.out.println("Insira o ID da entrega:");
                int entregaId = SC.nextInt();
                Timestamp dataEvento = new Timestamp(System.currentTimeMillis());
                SC.nextLine();
                System.out.println("Insira uma descrição do acontecido:");
                String descricao = SC.nextLine();
                System.out.println("Tem certeza de que deseja registrar este evento?\n" +
                        "1 - SIM\n" +
                        "2 - NÃO");
                int confirma = SC.nextInt();
                switch (confirma) {
                    case 1: {
                        HistoricoEntrega historicoEntrega = new HistoricoEntrega(entregaId, dataEvento, descricao);
                        try {
                            HISTORICOENTREGADAO.criarHistoricoEntrega(historicoEntrega);
                            System.out.println("evento registrado!");
                        } catch (Exception e) {
                            System.out.println("Erro ao conectar com o banco de dados!");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Retornando ao menu...");
                        return;
                    }
                    default: {
                        System.out.println("ERRO!!!");
                        break;
                    }
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void atualizarStatusEntrega() {
        List<Entrega> entregas = new ArrayList<>();
        StatusEntrega statusEntregaSelecionado = StatusEntrega.EMROTA;
        System.out.println("Tem certeza que deseja atualizar o status de uma entrega?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                try {
                    entregas = ENTREGADAO.listarEntregas();
                } catch (Exception e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                for (Entrega entrega : entregas) {
                    System.out.println(entrega.toString());
                }
                System.out.println("Insira o ID da entrega:");
                int entregaId = SC.nextInt();
                int pedidoId = 0;
                for (Entrega entrega : entregas) {
                    if (entregaId == entrega.getId()) {
                        pedidoId = entrega.getPedidoId();
                    }
                }
                Timestamp data_entrega = new Timestamp(System.currentTimeMillis());
                int index;
                int status;
                do {
                    index = 1;
                    for (StatusEntrega statusEntrega : StatusEntrega.values()){
                        System.out.println(index + " - " + statusEntrega.getName());
                        index++;
                    }
                    System.out.println("Insira o status da entrega:");
                    status = SC.nextInt();
                    switch (status) {
                        case 1: {
                            statusEntregaSelecionado = StatusEntrega.EMROTA;
                            break;
                        }
                        case 2: {
                            statusEntregaSelecionado = StatusEntrega.ENTREGUE;
                            break;
                        }
                        case 3: {
                            statusEntregaSelecionado = StatusEntrega.ATRASADA;
                            break;
                        }
                        default: {
                            System.out.println("ERRO!!!");
                            break;
                        }
                    }
                } while (status < 1 || status > 3);
                System.out.println("Tem certeza de que deseja atualizar esta estrega?\n" +
                        "1 - SIM\n" +
                        "2 - NÃO");
                int confirma = SC.nextInt();
                switch (confirma) {
                    case 1: {
                        Entrega entrega = new Entrega(data_entrega, statusEntregaSelecionado.getName(), entregaId);
                        Pedido pedido = new Pedido(StatusPedido.ENTREGUE.getName(), pedidoId);
                        try {
                            ENTREGADAO.atualizarEntrega(entrega);
                            PEDIDODAO.atualizarPedido(pedido);
                            System.out.println("Entrega atualizada!");
                        } catch (Exception e) {
                            System.out.println("Erro ao conectar com o banco de dados!");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Retornando ao menu...");
                        return;
                    }
                    default: {
                        System.out.println("ERRO!!!");
                        break;
                    }
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void listarTodasEntregasClienteMotorista() {
        List<EntregaDetalhadaDTO> entregaDetalhadaDTOS = new ArrayList<>();
        try {
            entregaDetalhadaDTOS = ENTREGADAO.listarEntregaClienteMotorista();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (EntregaDetalhadaDTO entregaDetalhadaDTO : entregaDetalhadaDTOS) {
            System.out.println(entregaDetalhadaDTO);
        }
    }

    public static void relatorioTotalEntregasMotorista() {
        List<RelatorioEntregaMotoristaDTO> relatorioEntregaMotoristaDTOS = new ArrayList<>();
        try {
            relatorioEntregaMotoristaDTOS = MOTORISTADAO.listarEntregaMotorista();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (RelatorioEntregaMotoristaDTO relatorioEntregaMotoristaDTO : relatorioEntregaMotoristaDTOS) {
            System.out.println(relatorioEntregaMotoristaDTO);
        }
    }

    public static void relatorioClienteMaiorVolumeEntregue() {
        List<RelatorioClienteVolumeDTO> relatorioClienteVolumeDTOS = new ArrayList<>();
        try {
            relatorioClienteVolumeDTOS = CLIENTEDAO.listarClienteVolume();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (RelatorioClienteVolumeDTO relatorioClienteVolumeDTO : relatorioClienteVolumeDTOS) {
            System.out.println(relatorioClienteVolumeDTO);
        }
    }

    public static void relatorioPedidosPendentesEstado() {
        List<RelatorioPedidoEstadoDTO> relatorioPedidoEstadoDTOS = new ArrayList<>();
        try {
            relatorioPedidoEstadoDTOS = PEDIDODAO.listarPedidoEstado();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (RelatorioPedidoEstadoDTO relatorioPedidoEstadoDTO : relatorioPedidoEstadoDTOS) {
            System.out.println(relatorioPedidoEstadoDTO);
        }
    }

    public static void relatorioEntregasAtrasadasCidade() {
        List<RelatorioEntregasAtrasadasDTO> relatorioEntregasAtrasadasDTOS = new ArrayList<>();
        try {
            relatorioEntregasAtrasadasDTOS = ENTREGADAO.listarEntregasAtrasadas();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (RelatorioEntregasAtrasadasDTO relatorioEntregasAtrasadasDTO : relatorioEntregasAtrasadasDTOS) {
            System.out.println(relatorioEntregasAtrasadasDTO);
        }
    }

    public static void buscarPedidoCPFCliente() {
        List<BuscarPedidoCPFDTO> buscarPedidoCPFDTOS = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        try {
            clientes = CLIENTEDAO.listarClientesBasico();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente.toStringBasico());
        }
        System.out.println("Insira o CPF do cliente: ");
        SC.nextLine();
        String CPF = SC.nextLine();
        try {
            buscarPedidoCPFDTOS = PEDIDODAO.buscarPedidoCPF(CPF);
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
        for (BuscarPedidoCPFDTO buscarPedidoCPFDTO : buscarPedidoCPFDTOS) {
            System.out.println(buscarPedidoCPFDTO);
        }
    }
}