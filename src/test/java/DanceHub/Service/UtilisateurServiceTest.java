package DanceHub.Service;

import DanceHub.Repository.UtilisateurRepository;
import DanceHub.dto.RegisterRequest;
import DanceHub.entity.Utilisateur;
import DanceHub.exception.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @Test
    void creerCompteHashesPasswordAndSetsDefaultRole() {
        RegisterRequest request = new RegisterRequest();
        request.setNom("Martin");
        request.setPrenom("Lina");
        request.setEmail("lina@example.com");
        request.setPassword("motdepasse123");

        when(utilisateurRepository.existsByEmail("lina@example.com")).thenReturn(false);
        when(passwordEncoder.encode("motdepasse123")).thenReturn("hashed-password");
        when(utilisateurRepository.save(org.mockito.ArgumentMatchers.any(Utilisateur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur created = utilisateurService.creerCompte(request);

        ArgumentCaptor<Utilisateur> captor = ArgumentCaptor.forClass(Utilisateur.class);
        verify(utilisateurRepository).save(captor.capture());

        assertThat(created.getPassword()).isEqualTo("hashed-password");
        assertThat(created.getRole()).isEqualTo(Utilisateur.Role.ELEVE);
        assertThat(created.isActif()).isFalse();
        assertThat(captor.getValue().getPassword()).isEqualTo("hashed-password");
    }

    @Test
    void creerCompteRejectsDuplicateEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("lina@example.com");

        when(utilisateurRepository.existsByEmail("lina@example.com")).thenReturn(true);

        assertThatThrownBy(() -> utilisateurService.creerCompte(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Email deja utilise");
    }
}
