package DanceHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "est obligatoire")
    private String nom;

    @NotBlank(message = "est obligatoire")
    private String prenom;

    @NotBlank(message = "est obligatoire")
    @Email(message = "doit etre un email valide")
    private String email;

    @NotBlank(message = "est obligatoire")
    @Size(min = 8, message = "doit contenir au moins 8 caracteres")
    private String password;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
