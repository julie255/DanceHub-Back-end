# DanceHub Backend

API REST Spring Boot pour une application de gestion d'ecole de danse.

DanceHub permet de gerer les comptes utilisateurs, les profils eleves et professeurs, le catalogue des cours et les inscriptions. Le backend expose des routes publiques pour consulter les cours et des routes securisees pour les operations de gestion et les parcours connectes.

## Ce que fait l'application

Le backend couvre les besoins metier suivants :

- inscription d'un nouvel utilisateur
- activation ou desactivation d'un compte par un administrateur
- connexion avec JWT
- gestion des roles `ADMIN`, `ELEVE`, `PROFESSEUR`
- creation et gestion des profils eleves
- creation et gestion des profils professeurs
- consultation publique des cours
- creation, modification et suppression des cours
- inscription d'un eleve a un cours
- suivi des inscriptions avec statuts `EN_ATTENTE`, `CONFIRMEE`, `ANNULEE`

En pratique :

- un visiteur peut creer un compte
- ce compte est cree avec le role `ELEVE`
- ce compte est inactif tant qu'un admin ne l'active pas
- une fois connecte, un utilisateur peut acceder aux routes correspondant a son role
- un eleve peut gerer son profil et ses inscriptions
- un professeur peut consulter ses informations et les inscriptions de ses cours
- un admin peut piloter l'ensemble du back-office

## Stack technique

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Data JPA
- Spring Security
- JWT avec `jjwt`
- Bean Validation
- PostgreSQL
- Maven

## Architecture

Le projet suit une structure Spring classique :

- `src/main/java/DanceHub/Controller`
  expose les endpoints HTTP
- `src/main/java/DanceHub/Service`
  contient la logique metier et les controles d'acces
- `src/main/java/DanceHub/Repository`
  gere l'acces aux donnees via JPA
- `src/main/java/DanceHub/entity`
  contient les entites metier
- `src/main/java/DanceHub/dto`
  contient les objets d'entree et de sortie de l'API
- `src/main/java/DanceHub/Security`
  gere JWT et l'authentification Spring Security
- `src/main/java/DanceHub/Config`
  contient la configuration de securite
- `src/main/java/DanceHub/exception`
  centralise les erreurs API

## Modele metier

### Utilisateur

Compte de connexion de l'application.

Champs principaux :

- `idUtilisateur`
- `nom`
- `prenom`
- `email`
- `password`
- `actif`
- `role`

Un utilisateur peut etre lie a un profil `Eleves` ou `Professeurs`.

### Eleves

Profil metier d'un eleve.

Champs principaux :

- `idEleves`
- `nom`
- `prenom`
- `email`
- `telephone`
- `dateNaissance`
- `utilisateur`

### Professeurs

Profil metier d'un professeur.

Champs principaux :

- `idProfesseur`
- `nom`
- `prenom`
- `email`
- `telephone`
- `specialite`
- `utilisateur`

### Cours

Cours de danse propose par l'ecole.

Champs principaux :

- `idCours`
- `nomCours`
- `description`
- `horaire`
- `dureeMinutes`
- `capaciteMax`
- `salle`
- `niveau`
- `professeurs`

### Inscription

Inscription d'un eleve a un cours.

Champs principaux :

- `idInscription`
- `eleves`
- `cours`
- `dateInscription`
- `statut`

## Regles metier principales

### Inscription utilisateur

Quand un visiteur appelle `POST /api/utilisateurs/inscription` :

- l'email doit etre unique
- le mot de passe est hashé avec BCrypt
- le role est force a `ELEVE`
- le compte est cree avec `actif = false`

L'inscription ne cree pas automatiquement le profil eleve. Ce profil se cree ensuite via l'endpoint `POST /api/eleves`.

### Connexion

Quand un utilisateur appelle `POST /api/auth/login` :

- l'email et le mot de passe sont verifies
- le compte doit etre actif
- un token JWT est renvoye si l'authentification reussit

### Gestion des cours

- les cours sont consultables publiquement
- seuls les admins peuvent creer, modifier ou supprimer un cours
- les places disponibles sont calculees a partir des inscriptions `CONFIRMEE`

### Gestion des inscriptions

Avant de creer une inscription :

- l'eleve doit exister
- le cours doit exister
- l'eleve ne doit pas deja etre inscrit a ce cours
- le cours ne doit pas etre complet

Lors de la creation :

- `dateInscription` prend la date du jour si elle n'est pas fournie
- `statut` prend `EN_ATTENTE` si il n'est pas fourni

## Securite

La securite repose sur JWT, Spring Security et des controles d'acces metier.

### Fonctionnement general

1. l'utilisateur se connecte via `POST /api/auth/login`
2. le backend renvoie un token JWT
3. le client envoie ce token dans le header `Authorization`
4. le filtre JWT authentifie l'utilisateur sur les routes protegees

Header attendu :

```text
Authorization: Bearer <token>
```

### Acces par role

- routes publiques :
  - `POST /api/auth/login`
  - `POST /api/utilisateurs/inscription`
  - `GET /api/cours/**`
- routes admin :
  - `/api/utilisateurs/**`
  - creation, modification, suppression des cours
  - creation d'un professeur
  - changement de statut d'une inscription
- routes eleve :
  - gestion de son profil eleve
  - consultation de ses inscriptions
  - creation de ses inscriptions
- routes professeur :
  - consultation et modification de son profil
  - consultation des inscriptions de ses cours

Le backend applique aussi un controle fin sur le proprietaire d'une ressource. Un eleve ne peut pas acceder au profil ou aux inscriptions d'un autre eleve.

### Gestion des erreurs

Les erreurs metier sont centralisees et renvoyees avec des statuts HTTP coherents :

- `400 Bad Request`
- `403 Forbidden`
- `404 Not Found`
- `409 Conflict`

## Configuration

Pour lancer le projet en local :

1. creer une base PostgreSQL nommee `dancehub`
2. copier `application.properties.example` vers `src/main/resources/application.properties`
3. renseigner vos identifiants locaux PostgreSQL
4. definir une cle JWT d'au moins 32 caracteres

Variables d'environnement supportees :

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`

## Donnees de demo

Par defaut, le backend ne charge pas `data.sql`.

Si tu veux des donnees de demonstration, lance l'application avec le profil `dev`. Le fichier `src/main/resources/application-dev.properties` active alors l'initialisation SQL.

Exemple PowerShell :

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
.\mvnw.cmd spring-boot:run
```

Avec ce profil, `data.sql` cree des utilisateurs, eleves, professeurs, cours et inscriptions de test.

Comptes utiles en mode `dev` :

- `admin@dancehub.com` -> `ADMIN`
- `lina@dancehub.com` -> `ELEVE`
- `sami@dancehub.com` -> `ELEVE`
- `claire@dancehub.com` -> `PROFESSEUR`
- `nadia@dancehub.com` -> `PROFESSEUR`



## Lancer le projet

### Lancement standard

```powershell
.\mvnw.cmd spring-boot:run
```

### Lancement avec donnees de demo

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
.\mvnw.cmd spring-boot:run
```

### Executer les tests

```powershell
.\mvnw.cmd test
```

## Endpoints principaux

### Authentification

- `POST /api/auth/login`

Exemple :

```json
{
  "email": "admin@dancehub.com",
  "password": "password"
}
```

### Utilisateurs

- `POST /api/utilisateurs/inscription`
- `GET /api/utilisateurs`
- `GET /api/utilisateurs/{id}`
- `PUT /api/utilisateurs/{id}/activer`
- `PUT /api/utilisateurs/{id}/desactiver`
- `PUT /api/utilisateurs/{id}/role?role=ADMIN`
- `DELETE /api/utilisateurs/{id}`

### Cours

- `GET /api/cours`
- `GET /api/cours/{id}`
- `GET /api/cours/disponibles`
- `GET /api/cours/niveau/{niveau}`
- `GET /api/cours/recherche?nom=...`
- `GET /api/cours/{id}/places`
- `POST /api/cours`
- `PUT /api/cours/{id}`
- `DELETE /api/cours/{id}`

### Eleves

- `POST /api/eleves`
- `GET /api/eleves`
- `GET /api/eleves/{id}`
- `GET /api/eleves/utilisateur/{utilisateurId}`
- `PUT /api/eleves/{id}`
- `DELETE /api/eleves/{id}`

### Professeurs

- `POST /api/professeurs`
- `GET /api/professeurs`
- `GET /api/professeurs/{id}`
- `GET /api/professeurs/utilisateur/{utilisateurId}`
- `PUT /api/professeurs/{id}`
- `DELETE /api/professeurs/{id}`

### Inscriptions

- `POST /api/inscriptions`
- `GET /api/inscriptions`
- `GET /api/inscriptions/{id}`
- `GET /api/inscriptions/eleve/{eleveId}`
- `GET /api/inscriptions/cours/{coursId}`
- `PUT /api/inscriptions/{id}/statut?statut=CONFIRMEE`
- `DELETE /api/inscriptions/{id}`

## Parcours de test typique

### 1. Creer un compte

```http
POST /api/utilisateurs/inscription
```

### 2. Se connecter en admin

```http
POST /api/auth/login
```

### 3. Activer le compte cree

```http
PUT /api/utilisateurs/{id}/activer
```

### 4. Se connecter avec le nouveau compte

```http
POST /api/auth/login
```

### 5. Creer le profil eleve

```http
POST /api/eleves
```

### 6. Consulter les cours

```http
GET /api/cours
```

### 7. Inscrire l'eleve a un cours

```http
POST /api/inscriptions
```

### 8. Confirmer l'inscription en admin

```http
PUT /api/inscriptions/{id}/statut?statut=CONFIRMEE
```

## Fichiers importants

- `src/main/java/DanceHub/DancehubApplication.java`
- `src/main/java/DanceHub/Config/SecurityConfig.java`
- `src/main/java/DanceHub/Security/JwtAuthenticationFilter.java`
- `src/main/java/DanceHub/Security/JwtService.java`
- `src/main/java/DanceHub/Service/AccessControlService.java`
- `src/main/java/DanceHub/exception/GlobalExceptionHandler.java`
- `src/main/resources/application-dev.properties`
- `application.properties.example`

## Etat actuel du backend

Le backend est aujourd'hui utilisable pour :

- tester les flux principaux dans Postman
- brancher un frontend sur une API securisee
- gerer les roles et les acces de base
- manipuler les cours et les inscriptions avec des regles metier coherentes

Les points deja traites dans cette version :

- JWT et routes protegees
- DTOs d'entree et de sortie
- validation des requetes
- gestion centralisee des erreurs
- controle d'acces par role et par proprietaire
- calcul coherent des places disponibles
