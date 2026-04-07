package DanceHub.dto;

public class AuthResponse {

    private final String token;
    private final String type;
    private final Long idUtilisateur;
    private final String email;
    private final String role;

    public AuthResponse(String token, Long idUtilisateur, String email, String role) {
        this.token = token;
        this.type = "Bearer";
        this.idUtilisateur = idUtilisateur;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
