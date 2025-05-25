package br.com.fiap.granfinale.exception;

public class EmailJaCadastradoException extends Exception {

    public EmailJaCadastradoException(String email) {
        super("O email '" + email + "' já está cadastrado no sistema.");
    }
}
