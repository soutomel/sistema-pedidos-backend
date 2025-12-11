package br.com.pedidos.sistema_pedidos.infrastructure.dto;

public class RegisterDTO {
    private String name;
    private String email;
    private String cpf;
    private String password;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setPassword(String password) { this.password = password; }
}
