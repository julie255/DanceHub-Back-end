package DanceHub.Service;

import DanceHub.Repository.UtilisateurRepository;
import DanceHub.dto.AuthRequest;
import DanceHub.dto.AuthResponse;
import DanceHub.entity.Utilisateur;
import DanceHub.Security.JwtService;
import DanceHub.exception.ForbiddenException;
import DanceHub.exception.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            UtilisateurRepository utilisateurRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.utilisateurRepository = utilisateurRepository;
    }

    public AuthResponse connecter(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (DisabledException e) {
            throw new ForbiddenException("Le compte n'est pas encore active");
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Email ou mot de passe incorrect");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token, utilisateur.getIdUtilisateur(), utilisateur.getEmail(), utilisateur.getRole().name());
    }
}
