package br.com.logistica.dao;

import br.com.logistica.model.Motorista;
import br.com.logistica.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDAO {
    public void cadastrarMotorista (Motorista motorista) throws SQLException {
        String command = """
                INSERT INTO Motorista
                (nome, cnh, veiculo, cidade_base)
                VALUES
                (?, ?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCnh());
            stmt.setString(3, motorista.getVeiculo());
            stmt.setString(4, motorista.getCidadeBase());

            stmt.executeUpdate();
        }
    }

    //    private int id;
    //
    //    private String nome;
    //
    //    private String cnh;
    //
    //    private String veiculo;
    //
    //    private String cidadeBase;

    public List<Motorista> listarMotoristas() throws SQLException {
        List<Motorista> motoristas = new ArrayList<>();
        String command = """
                SELECT
                id,
                nome,
                cnh,
                veiculo,
                cidade_base
                FROM Motorista
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                motoristas.add(new Motorista(
                        rs.getInt("id"),
                        rs.getInt("id"),
                        rs.getInt("id"),
                        rs.getInt("id"),
                        rs.getInt("id")
                ))
            }
        }
    }
}
