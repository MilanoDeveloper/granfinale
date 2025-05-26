
package br.com.fiap.granfinale.factory;

import br.com.fiap.granfinale.dao.*;

import java.sql.Connection;

public class DAOFactory {

    private final Connection connection;

    public DAOFactory(Connection connection) {
        this.connection = connection;
    }

    public UserDAO getUserDAO() {
        return new UserDAO(connection);
    }

    public ParticipanteDAO getParticipanteDAO() {
        return new ParticipanteDAO(connection);
    }

    public GrupoDAO getGrupoDAO() {
        return new GrupoDAO(connection);
    }

    public DespesaDAO getDespesaDAO() {
        return new DespesaDAO(connection);
    }


}
