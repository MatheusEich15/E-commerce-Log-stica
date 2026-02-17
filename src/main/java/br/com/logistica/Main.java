package br.com.logistica;

import br.com.logistica.dao.*;
import br.com.logistica.model.Cliente;
import br.com.logistica.model.Motorista;
import br.com.logistica.model.Pedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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
                    12 - BuSCar Pedido por CPF/CNPJ do Cliente
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
                    //atribuirPedidoMotorista();
                    break;
                }
                case 5: {
                    //registrarEventoEntrega();
                    break;
                }
                case 6: {
                    //atualizarStatusEntrega();
                    break;
                }
                case 7: {
                    //registrarTodasEntregasClienteMotorista();
                    break;
                }
                case 8: {
                    //relatorioTotalEntregasMotorista();
                    break;
                }
                case 9: {
                    //relatorioClienteMaiorVolumeEntregue();
                    break;
                }
                case 10: {
                    //relatorioPedidosPendentesEstado();
                    break;
                }
                case 11: {
                    //relatorioEntregasAtrasadasCidade();
                    break;
                }
                case 12: {
                    //buSCarPedidoCPFCliente();
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
        System.out.println("Tem certeza que deseja criar um novo pedido?" +
                "\n1 - SIM" +
                "\n2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                System.out.println("Insira o id do cliente:");
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
}