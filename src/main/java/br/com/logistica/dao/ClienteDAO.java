package br.com.logistica.dao;

import br.com.logistica.dto.RelatorioClienteVolumeDTO;
import br.com.logistica.model.Cliente;
import br.com.logistica.model.Motorista;
import br.com.logistica.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void cadastrarCliente (Cliente cliente)throws SQLException {
        String command = """
                INSERT INTO Cliente
                (nome, cpf_cnpj, endereco, cidade, estado)
                VALUES
                (?, ?, ?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEstado());

            stmt.executeUpdate();
        }
    }

    public List<Cliente> listarClientesBasico() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String command = """
                SELECT
                id,
                nome,
                cpf_cnpj
                FROM Cliente
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf_cnpj")
                ));
            }
        }
        return clientes;
    }

    public List<RelatorioClienteVolumeDTO> listarClienteVolume() throws SQLException{
        List<RelatorioClienteVolumeDTO> relatorioClienteVolumeDTOS = new ArrayList<>();
        String command = """
                SELECT Cliente.nome, SUM(Pedido.volume_m3)
                FROM Cliente
                JOIN Pedido ON Cliente.id = Pedido.cliente_id
                WHERE Pedido.status = 'ENTREGUE'
                GROUP BY Cliente.id
                ORDER BY SUM(Pedido.volume_m3) DESC;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                relatorioClienteVolumeDTOS.add(new RelatorioClienteVolumeDTO(
                        rs.getString("nome"),
                        rs.getDouble("SUM(Pedido.volume_m3)")
                ));
            }
        }
        return relatorioClienteVolumeDTOS;
    }
}