TRUNCATE TABLE inscriptions, cours, eleves, professeurs, utilisateurs RESTART IDENTITY CASCADE;

INSERT INTO utilisateurs (nom, prenom, email, password, role, actif) VALUES
('Admin', 'User', 'admin@dancehub.com', '$2a$10$0kyjHdln6fF1RDwN9MEyCOFJa32IzkdGrR9UMKvJ6A9IVOmZJInZi', 'ADMIN', true),
('Lina', 'Martin', 'lina@dancehub.com', '$2a$10$0kyjHdln6fF1RDwN9MEyCOFJa32IzkdGrR9UMKvJ6A9IVOmZJInZi', 'ELEVE', true),
('Sami', 'Benali', 'sami@dancehub.com', '$2a$10$0kyjHdln6fF1RDwN9MEyCOFJa32IzkdGrR9UMKvJ6A9IVOmZJInZi', 'ELEVE', false),
('Claire', 'Dubois', 'claire@dancehub.com', '$2a$10$0kyjHdln6fF1RDwN9MEyCOFJa32IzkdGrR9UMKvJ6A9IVOmZJInZi', 'PROFESSEUR', true),
('Nadia', 'Morel', 'nadia@dancehub.com', '$2a$10$0kyjHdln6fF1RDwN9MEyCOFJa32IzkdGrR9UMKvJ6A9IVOmZJInZi', 'PROFESSEUR', true);

INSERT INTO professeurs (nom, prenom, email, telephone, specialite, utilisateur_id) VALUES
(
    'Dubois',
    'Claire',
    'claire.prof@dancehub.com',
    '0611223344',
    'Classique',
    (SELECT id_utilisateur FROM utilisateurs WHERE email = 'claire@dancehub.com')
),
(
    'Morel',
    'Nadia',
    'nadia.prof@dancehub.com',
    '0622334455',
    'Hip-hop',
    (SELECT id_utilisateur FROM utilisateurs WHERE email = 'nadia@dancehub.com')
);

INSERT INTO eleves (nom, prenom, email, telephone, date_naissance, utilisateur_id) VALUES
(
    'Martin',
    'Lina',
    'lina.eleve@dancehub.com',
    '0633445566',
    '2008-05-14',
    (SELECT id_utilisateur FROM utilisateurs WHERE email = 'lina@dancehub.com')
),
(
    'Benali',
    'Sami',
    'sami.eleve@dancehub.com',
    '0644556677',
    '2006-11-02',
    (SELECT id_utilisateur FROM utilisateurs WHERE email = 'sami@dancehub.com')
);

INSERT INTO cours (nom, nom_cours, description, horaire, duree_minutes, capacite_max, salle, niveau, professeur_id) VALUES
(
    'Classique debutant',
    'Classique debutant',
    'Cours de danse classique pour debutants',
    '2026-04-07 18:00:00',
    90,
    12,
    'Salle A',
    'DEBUTANT',
    (SELECT id_professeur FROM professeurs WHERE email = 'claire.prof@dancehub.com')
),
(
    'Hip-hop intermediaire',
    'Hip-hop intermediaire',
    'Travail du rythme et des enchainements',
    '2026-04-08 19:00:00',
    75,
    15,
    'Salle B',
    'INTERMEDIAIRE',
    (SELECT id_professeur FROM professeurs WHERE email = 'nadia.prof@dancehub.com')
),
(
    'Atelier scene avance',
    'Atelier scene avance',
    'Preparation choregraphique pour spectacle',
    '2026-04-10 20:00:00',
    120,
    10,
    'Studio 2',
    'AVANCE',
    (SELECT id_professeur FROM professeurs WHERE email = 'nadia.prof@dancehub.com')
);

INSERT INTO inscriptions (eleve_id, cours_id, date_inscription, statut) VALUES
(
    (SELECT id_eleves FROM eleves WHERE email = 'lina.eleve@dancehub.com'),
    (SELECT id_cours FROM cours WHERE nom_cours = 'Classique debutant'),
    '2026-04-01',
    'CONFIRMEE'
),
(
    (SELECT id_eleves FROM eleves WHERE email = 'sami.eleve@dancehub.com'),
    (SELECT id_cours FROM cours WHERE nom_cours = 'Hip-hop intermediaire'),
    '2026-04-02',
    'EN_ATTENTE'
);
