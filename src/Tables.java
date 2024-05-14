import java.sql.SQLException;

public class Tables {
    private JDBC dbconn;
    private String dbnaam;

    private String databaseurl;

    public Tables(String dbnaam) {
        this.dbnaam = dbnaam;
        databaseurl = "jdbc:mysql://localhost:3306/"+dbnaam;
        createOpmerking();
        createOrder();
        createOrderitem();
        createUser();
    }

    public void createOpmerking(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            try {
                JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `routebepaling`.`opmerkingen` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `bezorger` VARCHAR(45) NULL,\n" +
                        "  `opmerking` VARCHAR(45) NULL,\n" +
                        "  `order_id` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`id`, `order_id`),\n" +
                        "  INDEX `order_id_idx` (`order_id` ASC),\n" +
                        "  CONSTRAINT `order_id`\n" +
                        "    FOREIGN KEY (`order_id`)\n" +
                        "    REFERENCES `routebepaling`.`order` (`order_id`)\n" +
                        "    ON DELETE NO ACTION\n" +
                        "    ON UPDATE NO ACTION);");
                System.out.println("Opmerkingen tabel gemaakt");
            } catch (SQLException e){
                System.out.println("Tabel opmerkingen kon niet gemaakt worden");
                throw new RuntimeException(e);
            }
            dbconn.closeConnection();
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
    }
    public void createOrder(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
        try {
            JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `routebepaling`.`order` (\n" +
                    "  `order_id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `date_received` DATETIME NULL,\n" +
                    "  `date_delivered` DATETIME NULL,\n" +
                    "  `delivered` VARCHAR(45) NOT NULL DEFAULT 'No',\n" +
                    "  PRIMARY KEY (`order_id`),\n" +
                    "  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC)\n" +
                    ");");
            System.out.println("Order tabel");
            dbconn.closeConnection();
        } catch (SQLException e) {
            System.out.println("Tabel order kon niet gemaakt worden");
            throw new RuntimeException(e);
        }
    }
    public void createUser(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            try{
                JDBC.executeSQL(dbconn.getConn(), "CREATE TABLE IF NOT EXISTS `user` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `naam` varchar(45) NOT NULL,\n" +
                        "  `wachtwoord` varchar(45) NOT NULL,\n" +
                        "  `rol` varchar(45) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `ID_UNIQUE` (`id`)\n" +
                        ");");
                System.out.println("User tabel gemaakt");
                dbconn.closeConnection();
            }catch (SQLException e){
                System.out.println("Tabel user kon niet gemaakt worden");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
        }
    }
    public void createOrderitem(){
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            try {
                JDBC.executeSQL(dbconn.getConn(),"CREATE TABLE IF NOT EXISTS `routebepaling`.`order_item` (\n" +
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
                        "    REFERENCES `routebepaling`.`order` (`order_id`)\n" +
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
        }
    }
}
