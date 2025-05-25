package br.com.fiap.granfinale.exception;

public class UsuarioNaoEncontradoException extends Exception {

    public UsuarioNaoEncontradoException(String email) {
        super("Usuário com o email '" + email + "' não foi encontrado.");
    }

    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.");
    }
}
