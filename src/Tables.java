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
        createOrder();
        createOrderitem();
        createOpmerking();
        createRoute();
        createBezorger();

    }

    public void createOpmerking(){
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
                    "  INDEX `order_id_idx` (`order_id` ASC),\n" +
                    "  CONSTRAINT `order_id`\n" +
                    "    FOREIGN KEY (`order_id`)\n" +
                    "    REFERENCES `"+dbnaam+"`.`order` (`order_id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);");
            System.out.println("Opmerking tabel gemaakt");
            dbconn.closeConnection();
        } catch (SQLException e) {
            System.out.println("Tabel opmerkingen kon niet gemaakt worden");
            dbconn.closeConnection();
            throw new RuntimeException(e);
        }
    }
    public void createOrder(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`order` (\n" +
                    "  `order_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `date_received` DATETIME NULL,\n" +
                    "  `date_delivered` DATETIME NULL,\n" +
                    "  `delivered` VARCHAR(45) NOT NULL DEFAULT 'No',\n" +
                    "  PRIMARY KEY (`order_id`),\n" +
                    "  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC)\n" +
                    ");");
            System.out.println("Order tabel gemaakt");
            dbconn.closeConnection();
        } catch (SQLException e) {
            System.out.println("Tabel order kon niet gemaakt worden");
            dbconn.closeConnection();
            throw new RuntimeException(e);
        }
    }
    public void createRoute(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `"+dbnaam+"`.`route` (\n" +
                    "            `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `puntA` POINT NULL,\n" +
                    "  `puntB` POINT NULL,\n" +
                    "  `puntC` POINT NULL,\n" +
                    "  `puntD` POINT NULL,\n" +
                    "  `puntE` VARCHAR(45) NULL,\n" +
                    "    PRIMARY KEY (`id`),\n" +
                    "    UNIQUE INDEX `puntA_UNIQUE` (`puntA` ASC),\n" +
                    "    UNIQUE INDEX `puntB_UNIQUE` (`puntB` ASC),\n" +
                    "    UNIQUE INDEX `puntC_UNIQUE` (`puntC` ASC),\n" +
                    "    UNIQUE INDEX `puntD_UNIQUE` (`puntD` ASC),\n" +
                    "    UNIQUE INDEX `puntE_UNIQUE` (`puntE` ASC));");
            System.out.println("Route tabel gemaakt");
            dbconn.closeConnection();
        } catch (SQLException e) {
            System.out.println("Tabel route kon niet gemaakt worden");
            dbconn.closeConnection();
            throw new RuntimeException(e);
        }
    }
    public void createBezorger(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `bezorger` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `naam` VARCHAR(45) NOT NULL,\n" +
                    "  `route_id` INT,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  CONSTRAINT `route_fk` FOREIGN KEY (`route_id`) REFERENCES `route`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ");");
            System.out.println("Bezorger tabel gemaakt");
            dbconn.closeConnection();
        } catch (SQLException e) {
            System.out.println("Tabel bezorger kon niet gemaakt worden");
            dbconn.closeConnection();
            throw new RuntimeException(e);
        }
    }

    public void createUser(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            try{
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
                dbconn.closeConnection();
            }catch (SQLException e){
                System.out.println("Tabel user kon niet gemaakt worden");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            dbconn.closeConnection();
        }
    }
    public void createOrderitem(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
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
            } catch (SQLException e) {
                System.out.println("Tabel order_item kon niet gemaakt worden");
                throw new RuntimeException(e);
            }
            dbconn.closeConnection();
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
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
