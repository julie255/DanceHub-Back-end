DELETE FROM utilisateurs;
INSERT INTO utilisateurs (nom, prenom, email, password, role, actif)
VALUES ('Admin','user', 'admin@dancehub.com', 'password', 'ADMIN', true);
