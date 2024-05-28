import java.sql.SQLException;

public class Tables {
    private JDBC dbconn;
    private String dbnaam;
    private String databaseurl;

    public Tables(String dbnaam) {
        this.dbnaam = dbnaam;
        databaseurl = "jdbc:mysql://localhost:3306/" + dbnaam;
        createUser();
        registerUser("Amaldi", "123", "Manager");
        registerUser("Bob", "123", "Bezorger");
        createKlant();
        createOrder();
        createOrderitem();
        createOpmerking();
        createRoute();
        createBezorger();
    }
    public void createKlant(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`klanten` (\n" +
                    "  `klant_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `naam` VARCHAR(45) NOT NULL,\n" +
                    "  `postcode` VARCHAR(10) NOT NULL,\n" +
                    "  `huisnummer` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`klant_id`));");
            JDBC.executeSQL(dbconn.getConn(),
                    "ALTER TABLE `" + dbnaam + "`.`klanten` " +
                            "ADD COLUMN IF NOT EXISTS `klant_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                            "ADD COLUMN IF NOT EXISTS `naam` VARCHAR(45) NOT NULL, " +
                            "ADD COLUMN IF NOT EXISTS `postcode` VARCHAR(10) NOT NULL, " +
                            "ADD COLUMN IF NOT EXISTS `huisnummer` INT NOT NULL;"
            );

            System.out.println("klanten tabel gemaakt");

        } catch (SQLException e) {
            System.out.println("Tabel klanten kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }
    public void createOpmerking() {
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`opmerkingen` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `bezorger` VARCHAR(45) NULL,\n" +
                    "  `opmerking` VARCHAR(45) NULL,\n" +
                    "  `order_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`, `order_id`),\n" +
                    "  INDEX `order_id` (`order_id` ASC),\n" +
                    "  CONSTRAINT `order_id`\n" +
                    "    FOREIGN KEY (`order_id`)\n" +
                    "    REFERENCES `"+dbnaam+"`.`order` (`order_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);");
            System.out.println("Opmerking tabel gemaakt");
            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `"+dbnaam+"`.`opmerkingen`\n" +
                    "ADD COLUMN IF NOT EXISTS `bezorger` VARCHAR(45) NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `opmerking` VARCHAR(45) NULL;");
        } catch (SQLException e) {
            System.out.println("Tabel opmerkingen kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }

    public void createOrder() {
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            return;
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`order` (\n" +
                    "  `order_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `date_received` DATETIME NULL,\n" +
                    "  `date_delivered` DATETIME NULL,\n" +
                    "  `delivered` VARCHAR(45) NOT NULL DEFAULT 'No',\n" +
                    "  `klant_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`order_id`),\n" +
                    "  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC),\n" +
                    "  INDEX `klant_id_idx` (`klant_id` ASC),\n" +
                    "  CONSTRAINT `fk_klant_id`\n" +
                    "    FOREIGN KEY (`klant_id`)\n" +
                    "    REFERENCES `"+dbnaam+"`.`klanten` (`klant_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION\n" +
                    ");");
            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `"+dbnaam+"`.`order`\n" +
                    "ADD COLUMN IF NOT EXISTS `date_received` DATETIME NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `date_delivered` DATETIME NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `delivered` VARCHAR(45) NOT NULL DEFAULT 'No',\n" +
                    "ADD COLUMN IF NOT EXISTS `klant_id` INT NOT NULL;\n");
//            JDBC.executeSQL(dbconn.getConn(), "INSERT INTO test.order (order_id, date_received, klant_id)\n" +
//                    "VALUES\n" +
//                    "    (1, '2024-05-05', 1),\n" +
//                    "    (2, '2024-05-05', 2),\n" +
//                    "    (3, '2024-05-05', 3),\n" +
//                    "    (4, '2024-05-05', 4),\n" +
//                    "    (5, '2024-05-05', 5),\n" +
//                    "    (6, '2024-05-05', 6),\n" +
//                    "    (7, '2024-05-05', 7),\n" +
//                    "    (8, '2024-05-05', 8),\n" +
//                    "    (9, '2024-05-05', 9),\n" +
//                    "    (10, '2024-05-05', 10),\n" +
//                    "    (11, '2024-05-05', 11),\n" +
//                    "    (12, '2024-05-05', 12),\n" +
//                    "    (13, '2024-05-05', 13),\n" +
//                    "    (14, '2024-05-05', 14),\n" +
//                    "    (15, '2024-05-05', 15),\n" +
//                    "    (16, '2024-05-05', 16),\n" +
//                    "    (17, '2024-05-05', 17),\n" +
//                    "    (18, '2024-05-05', 18),\n" +
//                    "    (19, '2024-05-05', 19),\n" +
//                    "    (20, '2024-05-05', 20);");
//            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `"+dbnaam+"`.`order`\n" +
//                    "ADD INDEX IF NOT EXISTS `klant_id_idx` (`klant_id` ASC),\n" +
//                    "ADD CONSTRAINT IF NOT EXISTS `fk_klant_id`\n" +
//                    "  FOREIGN KEY (`klant_id`)\n" +
//                    "  REFERENCES `"+dbnaam+"`.`klanten` (`klant_id`)\n" +
//                    "  ON DELETE NO ACTION\n" +
//                    "  ON UPDATE NO ACTION;");
            System.out.println("Order tabel gemaakt");
        } catch (SQLException e) {
            System.out.println("Tabel order kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }


    public void createRoute() {
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`route` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `puntA` POINT NULL,\n" +
                    "  `puntB` POINT NULL,\n" +
                    "  `puntC` POINT NULL,\n" +
                    "  `puntD` POINT NULL,\n" +
                    "  `puntE` POINT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `puntA_UNIQUE` (`puntA` ASC),\n" +
                    "  UNIQUE INDEX `puntB_UNIQUE` (`puntB` ASC),\n" +
                    "  UNIQUE INDEX `puntC_UNIQUE` (`puntC` ASC),\n" +
                    "  UNIQUE INDEX `puntD_UNIQUE` (`puntD` ASC),\n" +
                    "  UNIQUE INDEX `puntE_UNIQUE` (`puntE` ASC)\n" +
                    ");");
            System.out.println("Route tabel gemaakt");
            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `"+dbnaam+"`.`route`\n" +
                    "ADD COLUMN IF NOT EXISTS `puntA` POINT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `puntB` POINT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `puntC` POINT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `puntD` POINT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `puntE` POINT NULL;");
        } catch (SQLException e) {
            System.out.println("Tabel route kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }

    public void createBezorger() {
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `bezorger` (\n" +
                    "  `id` INT NOT NULL,\n" +
                    "  `naam` VARCHAR(45) NOT NULL,\n" +
                    "  `route_id` INT,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  CONSTRAINT `route_fk` FOREIGN KEY (`route_id`) REFERENCES `route`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ");");
            System.out.println("Bezorger tabel gemaakt");
            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `bezorger`\n" +
                    "ADD COLUMN IF NOT EXISTS `naam` VARCHAR(45) NOT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `route_id` INT;");
            dbconn.voegBezorgerToe();
        } catch (SQLException e) {
            System.out.println("Tabel bezorger kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }

    public void createUser() {
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), """
                    CREATE TABLE IF NOT EXISTS `user` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `naam` varchar(45) NOT NULL,
                      `wachtwoord` varchar(45) NOT NULL,
                      `rol` varchar(45) DEFAULT NULL,
                      `ingelogd` varchar(45) DEFAULT NULL,
                      PRIMARY KEY (`id`),
                      UNIQUE KEY `ID_UNIQUE` (`id`)
                    );""");
            System.out.println("User tabel gemaakt");
            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `user`\n" +
                    "ADD COLUMN IF NOT EXISTS `naam` VARCHAR(45) NOT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `wachtwoord` VARCHAR(45) NOT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `rol` VARCHAR(45) DEFAULT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `ingelogd` VARCHAR(45) DEFAULT NULL;");
        } catch (SQLException e) {
            System.out.println("Tabel user kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }

    public void createOrderitem() {
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(),"CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`order_item` (\n" +
                    "  `item_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `order_id` INT NOT NULL,\n" +
                    "  `product_name` VARCHAR(255) NOT NULL,\n" +
                    "  `quantity` INT NOT NULL,\n" +
                    "  `size` INT NOT NULL,\n" +
                    "  `price` DECIMAL(10, 2) NOT NULL,\n" +
                    "  PRIMARY KEY (`item_id`),\n" +
                    "  INDEX `fk_order_id_idx` (`order_id` ASC),\n" +
                    "  CONSTRAINT `fk_order_id`\n" +
                    "    FOREIGN KEY (`order_id`)\n" +
                    "    REFERENCES `"+dbnaam+"`.`order` (`order_id`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE NO ACTION\n" +
                    ");");
            System.out.println("Order_item tabel gemaakt");
            JDBC.executeSQL(dbconn.getConn(), "ALTER TABLE `"+dbnaam+"`.`order_item`\n" +
                    "ADD COLUMN IF NOT EXISTS `product_name` VARCHAR(255) NOT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `quantity` INT NOT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `size` INT NOT NULL,\n" +
                    "ADD COLUMN IF NOT EXISTS `price` DECIMAL(10, 2) NOT NULL;");
        } catch (SQLException e) {
            System.out.println("Tabel order_item kon niet gemaakt of gealtereerd worden: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            dbconn.closeConnection();
        }
    }
    public void registerUser(String naam, String wachtwoord, String rol){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            try {
                String query = "INSERT INTO user (naam, wachtwoord, rol)\n" +
                        "SELECT * FROM (SELECT ?, ?, ?) AS tmp\n" +
                        "WHERE NOT EXISTS (\n" +
                        "    SELECT 1 FROM user WHERE naam = ? AND wachtwoord = ? AND rol = ?\n" +
                        ");";
                JDBC.executeSQL(dbconn.getConn(), query, naam, wachtwoord, rol, naam, wachtwoord, rol);
                System.out.println(naam+": is geregistreerd");
                dbconn.closeConnection();
            } catch (SQLException e) {
                System.out.println(naam+" kon niet geregistreerd worden");
                throw new RuntimeException(e);
            }
            dbconn.closeConnection();
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            dbconn.closeConnection();
        }
    }

}
