package ceng.ceng351.cengfactorydb;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class CENGFACTORYDB implements ICENGFACTORYDB{
    /**
     * Place your initialization code inside if required.
     *
     * <p>
     * This function will be called before all other operations. If your implementation
     * need initialization , necessary operations should be done inside this function. For
     * example, you can set your connection to the database server inside this function.
     */
    private static String user = "e2521706"; // TODO: Your userName
    private static String password = "7cqUoEVt%3K4"; //  TODO: Your password
    private static String host = "144.122.71.128"; // host name
    private static String database = "db2521706"; // TODO: Your database name
    private static int port = 8080; // port

    private static Connection connection = null;

    public void initialize() {

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(url, user, password);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * Should create the necessary tables when called.
     *
     * @return the number of tables that are created successfully.
     */
    public int createTables() {
        int numberOfTables = 0;

        String queryCreateFactory = "create table if not exists factory (" +
                "factoryID int ," +
                "factoryName varchar(60) ," +
                "factoryType varchar(60) ," +
                "country varchar(60) ," +
                "primary key (factoryID))" ;

        String queryCreateEmployee = "create table if not exists employee (" +
                "employeeID int ," +
                "employeeName varchar(60) ," +
                "department varchar(60) ," +
                "salary int ," +
                "primary key (employeeID))" ;

        String queryCreateWorksIn = "create table if not exists worksIn (" +
                "factoryID int ," +
                "employeeID int ," +
                "startDate date ," +
                "primary key (factoryID, employeeID) ," +
                "foreign key (factoryID) references factory(factoryID) on delete cascade on update cascade ," +
                "foreign key (employeeID) references employee(employeeID) on delete cascade on update cascade )" ;

        String queryCreateProduct = "create table if not exists product (" +
                "productID int ," +
                "productName varchar(60) ," +
                "productType varchar(60) ," +
                "primary key (productID))" ;

        String queryCreateProduce = "create table if not exists produce (" +
                "factoryID int ," +
                "productID int ," +
                "amount int ," +
                "productionCost int ," +
                "primary key (factoryID, productID) ," +
                "foreign key (factoryID) references factory(factoryID) on delete cascade on update cascade ," +
                "foreign key (productID) references product(productID) on delete cascade on update cascade )" ;

        String queryCreateShipment = "create table if not exists shipment (" +
                "factoryID int ," +
                "productID int ," +
                "amount int ," +
                "pricePerUnit int ," +
                "foreign key (factoryID) references factory(factoryID) on delete cascade on update cascade ," +
                "foreign key (productID) references product(productID) on delete cascade on update cascade )" ;


        try {
            Statement statement = this.connection.createStatement();

            statement.executeUpdate(queryCreateFactory);
            numberOfTables++;

            statement.executeUpdate(queryCreateEmployee);
            numberOfTables++;

            statement.executeUpdate(queryCreateWorksIn);
            numberOfTables++;

            statement.executeUpdate(queryCreateProduct);
            numberOfTables++;

            statement.executeUpdate(queryCreateProduce);
            numberOfTables++;

            statement.executeUpdate(queryCreateShipment);
            numberOfTables++;

            //close
            statement.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfTables;
    }

    /**
     * Should drop the tables if exists when called.
     *
     * @return the number of tables are dropped successfully.
     */
    public int dropTables() {
        int numberofTables = 0;

        String queryDropWorksInTable = "DROP TABLE IF EXISTS worksIn";

        String queryDropProduceTable = "DROP TABLE IF EXISTS produce";

        String queryDropShipmentTable = "DROP TABLE IF EXISTS shipment";

        String queryDropProductTable = "DROP TABLE IF EXISTS product";

        String queryFactoryTable = "DROP TABLE IF EXISTS factory";

        String queryDropEmployeeTable = "DROP TABLE IF EXISTS employee";

        try {
            Statement statement = this.connection.createStatement();

            statement.executeUpdate(queryDropWorksInTable);
            numberofTables++;

            statement.executeUpdate(queryDropProduceTable);
            numberofTables++;

            statement.executeUpdate(queryDropShipmentTable);
            numberofTables++;

            statement.executeUpdate(queryDropProductTable);
            numberofTables++;

            statement.executeUpdate(queryFactoryTable);
            numberofTables++;

            statement.executeUpdate(queryDropEmployeeTable);
            numberofTables++;

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberofTables;
    }

    /**
     * Should insert an array of Factory into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertFactory(Factory[] factories) {
        int numberOfRows = 0;

        for (int i = 0; i < factories.length; i++){
            try {
                Factory factory = factories[i];

                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO factory VALUES(?,?,?,?)");
                statement.setInt(1, factory.getFactoryId());
                statement.setString(2, factory.getFactoryName());
                statement.setString(3, factory.getFactoryType());
                statement.setString(4, factory.getCountry());

                statement.executeUpdate();
                statement.close();
                numberOfRows++;
            }

            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfRows;
    }

    /**
     * Should insert an array of Employee into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertEmployee(Employee[] employees) {
        int numberOfRows = 0;

        for (int i = 0; i < employees.length; i++){
            try {
                Employee employee = employees[i];

                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO employee VALUES(?,?,?,?)");
                statement.setInt(1, employee.getEmployeeId());
                statement.setString(2, employee.getEmployeeName());
                statement.setString(3, employee.getDepartment());
                statement.setInt(4, employee.getSalary());

                statement.executeUpdate();
                statement.close();
                numberOfRows++;
            }

            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return numberOfRows;
    }

    /**
     * Should insert an array of WorksIn into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertWorksIn(WorksIn[] worksIns) {
        int numberOfRows = 0;

        for (int i = 0; i < worksIns.length; i++){
            try {
                WorksIn worksIn = worksIns[i];

                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO worksIn VALUES(?,?,?)");
                statement.setInt(1, worksIn.getFactoryId());
                statement.setInt(2, worksIn.getEmployeeId());
                statement.setString(3, worksIn.getStartDate());

                statement.executeUpdate();
                statement.close();
                numberOfRows++;

            }

            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return numberOfRows;
    }

    /**
     * Should insert an array of Product into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduct(Product[] products) {
        int numberOfRows = 0;

        for (int i = 0; i < products.length; i++){
            try {
                Product product = products[i];

                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO product VALUES(?,?,?)");
                statement.setInt(1, product.getProductId());
                statement.setString(2, product.getProductName());
                statement.setString(3, product.getProductType());

                statement.executeUpdate();
                statement.close();
                numberOfRows++;
            }

            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return numberOfRows;
    }


    /**
     * Should insert an array of Produce into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduce(Produce[] produces) {
        int numberOfRows = 0;

        for (int i = 0; i < produces.length; i++){
            try {
                Produce produce = produces[i];

                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO produce VALUES(?,?,?,?)");
                statement.setInt(1, produce.getFactoryId());
                statement.setInt(2, produce.getProductId());
                statement.setInt(3, produce.getAmount());
                statement.setInt(4, produce.getProductionCost());

                statement.executeUpdate();
                statement.close();
                numberOfRows++;
            }

            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return numberOfRows;
    }


    /**
     * Should insert an array of Shipment into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertShipment(Shipment[] shipments) {
        int numberOfRows = 0;

        for (int i = 0; i < shipments.length; i++){
            try {
                Shipment shipment = shipments[i];

                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO shipment VALUES(?,?,?,?)");
                statement.setInt(1, shipment.getFactoryId());
                statement.setInt(2, shipment.getProductId());
                statement.setInt(3, shipment.getAmount());
                statement.setInt(4, shipment.getPricePerUnit());

                statement.executeUpdate();
                statement.close();
                numberOfRows++;
            }

            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return numberOfRows;
    }

    /**
     * Should return all factories that are located in a particular country.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesForGivenCountry(String country) {
        ResultSet set;
        ArrayList<Factory> resultList = new ArrayList<>();

        String query = "SELECT factoryID, factoryName, factoryType, country " +
                        "FROM factory " +
                        "WHERE country = ?" +
                        "ORDER BY factoryID ASC";

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, country);
            set = statement.executeQuery();

            while (set.next()){

                int factoryID = set.getInt("factoryID");
                String factoryName = set.getString("factoryName");
                String factoryType = set.getString(("factoryType"));
                String factoryCountry = set.getString(("country"));

                Factory factory = new Factory(factoryID, factoryName, factoryType, factoryCountry);
                resultList.add(factory);
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return resultList.toArray(new Factory[0]);
    }

    /**
     * Should return all factories without any working employees.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesWithoutAnyEmployee() {
        ResultSet set;
        ArrayList<Factory> resultList = new ArrayList<>();

        String query = "SELECT DISTINCT F.factoryID, F.factoryName, F.factoryType, F.country " +
                        "FROM factory F " +
                        "WHERE F.factoryID NOT IN (SELECT DISTINCT W.factoryID " +
                                                    "FROM worksIn W) " +
                        "ORDER BY F.factoryID ASC";
        try {
            Statement statement = this.connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                int factoryID = set.getInt("factoryID");
                String factoryName = set.getString("factoryName");
                String factoryType = set.getString(("factoryType"));
                String factoryCountry = set.getString(("country"));

                Factory factory = new Factory(factoryID, factoryName, factoryType, factoryCountry);
                resultList.add(factory);
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return resultList.toArray(new Factory[0]);
    }

    /**
     * Should return all factories that produce all products for a particular productType
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesProducingAllForGivenType(String productType) {
        ResultSet set;
        ArrayList<Factory> resultList = new ArrayList<>();

        String query = "SELECT DISTINCT F.factoryID, F.factoryName, F.factoryType, F.country\n" +
                        "FROM factory F\n" +
                        "WHERE NOT EXISTS (SELECT T.productID\n" +
                                                "FROM product T\n" +
                                                "WHERE T.productType = ?\n" +
                                                "EXCEPT\n" +
                                                "SELECT P.productID\n" +
                                                "FROM produce P\n" +
                                                "WHERE P.factoryID = F.factoryID)\n" +
                        "ORDER BY F.factoryID ASC";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, productType);
            set = statement.executeQuery();

            while (set.next()){
                int factoryID = set.getInt("factoryID");
                String factoryName = set.getString("factoryName");
                String factoryType = set.getString(("factoryType"));
                String factoryCountry = set.getString(("country"));

                Factory factory = new Factory(factoryID, factoryName, factoryType, factoryCountry);
                resultList.add(factory);
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return resultList.toArray(new Factory[0]);
    }


    /**
     * Should return the products that are produced in a particular factory but
     * don’t have any shipment from that factory.
     *
     * @return Product[]
     */
    public Product[] getProductsProducedNotShipped() {
        ResultSet set;
        ArrayList<Product> resultList = new ArrayList<>();

        String query = "SELECT DISTINCT T.productID, T.productName, T.productType\n" +
                        "FROM product T\n" +
                        "WHERE T.productID IN (SELECT P.productID\n" +
                                                "FROM produce P\n" +
                                                "WHERE NOT EXISTS (SELECT S.productID\n" +
                                                                    "FROM shipment S\n" +
                                                                    "WHERE S.productID = P.productID\n" +
                                                                            "AND S.factoryID = P.factoryID))" +
                        "ORDER BY T.productID ASC";

        try {
            Statement statement = this.connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                int productID = set.getInt("productID");
                String productName = set.getString("productName");
                String productType = set.getString(("productType"));

                Product product = new Product(productID, productName, productType);
                resultList.add(product);

            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return resultList.toArray(new Product[0]);


    }


    /**
     * For a given factoryId and department, should return the average salary of
     *     the employees working in that factory and that specific department.
     *
     * @return double
     */
    public double getAverageSalaryForFactoryDepartment(int factoryId, String department) {
        double avg = 0.0;
        ResultSet set;

        String query = "SELECT AVG(salary) AS avg\n" +
                        "FROM employee E\n" +
                        "WHERE E.department = ?\n" +
                                "AND E.employeeID IN (SELECT W.employeeID\n" +
                                                        "FROM worksIn W\n" +
                                                        "WHERE W.factoryID = ?)";

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, department);
            statement.setInt(2, factoryId);
            set = statement.executeQuery();

            if (set.next()){
                avg = set.getDouble("avg");
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return avg;
    }


    /**
     * Should return the most profitable products for each factory
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProducts() {
        ResultSet set;
        ArrayList<QueryResult.MostValueableProduct> resultList = new ArrayList<>();

        String query = "SELECT DISTINCT Temp1.factoryID, Temp1.productID, Temp1.productName, Temp1.productType, Temp1.profit1 AS profit\n" +
                        "FROM (SELECT P.factoryID, T.productID, T.productName, T.productType, ((S.amount * S.pricePerUnit) - (P.amount * P.productionCost)) AS profit1\n" +
                                "FROM product T, produce P, shipment S\n" +
                                "WHERE T.productID = P.productID\n" +
                                        "AND P.productID = S.productID\n" +
                                        "AND P.factoryID = S.factoryID\n" +
                                "UNION\n" +
                                "SELECT P.factoryID, T.productID, T.productName, T.productType, -(P.amount * P.productionCost) AS profit1\n" +
                                "FROM product T, produce P\n" +
                                "WHERE T.productID = P.productID\n" +
                                        "AND NOT EXISTS (SELECT S.productID\n" +
                                                        "FROM shipment S\n" +
                                                        "WHERE S.productID = P.productID\n" +
                                                                "AND S.factoryID = P.factoryID)) AS Temp1\n" +

                        "WHERE Temp1.profit1 >= ALL (SELECT ((S2.amount * S2.pricePerUnit) - (P2.amount * P2.productionCost)) AS profit1\n" +
                                                    "FROM product T2, produce P2, shipment S2\n" +
                                                    "WHERE T2.productID = P2.productID\n" +
                                                            "AND P2.productID = S2.productID\n" +
                                                            "AND P2.factoryID = S2.factoryID\n" +
                                                            "AND Temp1.factoryID = P2.factoryID\n" +
                                                    "UNION\n" +
                                                    "SELECT -(P2.amount * P2.productionCost) AS profit1\n" +
                                                    "FROM product T2, produce P2\n" +
                                                    "WHERE T2.productID = P2.productID\n" +
                                                            "AND Temp1.factoryID = P2.factoryID\n" +
                                                            "AND NOT EXISTS (SELECT S2.productID\n" +
                                                                            "FROM shipment S2\n" +
                                                                            "WHERE S2.productID = P2.productID\n" +
                                                                                    "AND S2.factoryID = P2.factoryID\n" +
                                                                                    "AND P2.factoryID = S2.factoryID))\n" +
                        "ORDER BY profit DESC, Temp1.factoryID ASC";

        try {
            Statement statement = this.connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                int factoryID = set.getInt("factoryID");
                int productID = set.getInt("productID");
                String productName = set.getString("productName");
                String productType = set.getString("productType");
                int profit = set.getInt("profit");

                QueryResult.MostValueableProduct object = new QueryResult.MostValueableProduct(factoryID, productID, productName, productType, profit);
                resultList.add(object);
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        QueryResult.MostValueableProduct[] array = new QueryResult.MostValueableProduct[resultList.size()];
        return resultList.toArray(array);
    }


    /**
     * For each product, return the factories that gather the highest profit
     * for that product
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProductsOnFactory() {
        ResultSet set;
        ArrayList<QueryResult.MostValueableProduct> resultList = new ArrayList<>();

        String query = "SELECT DISTINCT Temp1.factoryID, Temp1.productID, Temp1.productName, Temp1.productType, Temp1.profit1 AS profit\n" +
                        "FROM (SELECT P.factoryID, T.productID, T.productName, T.productType, ((S.amount * S.pricePerUnit) - (P.amount * P.productionCost)) AS profit1\n" +
                                "FROM product T, produce P, shipment S\n" +
                                "WHERE T.productID = P.productID\n" +
                                        "AND P.productID = S.productID\n" +
                                        "AND P.factoryID = S.factoryID\n" +
                                "UNION\n" +
                                "SELECT P.factoryID, T.productID, T.productName, T.productType, -(P.amount * P.productionCost) AS profit1\n" +
                                "FROM product T, produce P\n" +
                                "WHERE T.productID = P.productID\n" +
                                        "AND NOT EXISTS (SELECT S.productID\n" +
                                                        "FROM shipment S\n" +
                                                        "WHERE S.productID = P.productID\n" +
                                                                "AND S.factoryID = P.factoryID)) AS Temp1\n" +
                        "WHERE Temp1.profit1 >= ALL (SELECT ((S2.amount * S2.pricePerUnit) - (P2.amount * P2.productionCost)) AS profit1\n" +
                                                    "FROM product T2, produce P2, shipment S2\n" +
                                                    "WHERE T2.productID = P2.productID\n" +
                                                            "AND P2.productID = S2.productID\n" +
                                                            "AND P2.factoryID = S2.factoryID\n" +
                                                            "AND Temp1.productID = P2.productID\n" +
                                                    "UNION\n" +
                                                    "SELECT -(P2.amount * P2.productionCost) AS profit1\n" +
                                                    "FROM product T2, produce P2\n" +
                                                    "WHERE T2.productID = P2.productID\n" +
                                                            "AND Temp1.productID = P2.productID\n" +
                                                            "AND NOT EXISTS (SELECT S2.productID\n" +
                                                                            "FROM shipment S2\n" +
                                                                            "WHERE S2.productID = P2.productID\n" +
                                                                                    "AND S2.factoryID = P2.factoryID\n" +
                                                                                    "AND P2.factoryID = S2.factoryID))\n" +
                        "ORDER BY profit DESC, Temp1.productID ASC";

        try {
            Statement statement = this.connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                int factoryID = set.getInt("factoryID");
                int productID = set.getInt("productID");
                String productName = set.getString("productName");
                String productType = set.getString("productType");
                int profit = set.getInt("profit");

                QueryResult.MostValueableProduct object = new QueryResult.MostValueableProduct(factoryID, productID, productName, productType, profit);
                resultList.add(object);
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        QueryResult.MostValueableProduct[] array = new QueryResult.MostValueableProduct[resultList.size()];
        return resultList.toArray(array);
    }


    /**
     * For each department, should return all employees that are paid under the
     *     average salary for that department. You consider the employees
     *     that do not work earning ”0”.
     *
     * @return QueryResult.LowSalaryEmployees[]
     */
    public QueryResult.LowSalaryEmployees[] getLowSalaryEmployeesForDepartments() {
        ResultSet set;
        ArrayList<QueryResult.LowSalaryEmployees> resultList = new ArrayList<>();

        String query = "SELECT DISTINCT Temp.employeeID, Temp.employeeName, Temp.department, Temp.salary\n" +
                        "FROM (\n" +
                                "SELECT E.employeeID, E.employeeName, E.department, E.salary\n" +
                                        "FROM employee E\n" +
                                        "WHERE E.salary < (SELECT AVG(COALESCE(D.salary,0))\n" +
                                                            "FROM employee D\n" +
                                                            "WHERE D.department = E.department\n" +
                                                            "GROUP BY D.department)\n" +
                                        "UNION\n" +
                                        "SELECT E.employeeID, E.employeeName, E.department, 0\n" +
                                        "FROM employee E\n" +
                                        "WHERE E.employeeID NOT IN (SELECT W.employeeID\n" +
                                                                    "FROM worksIn W)) AS Temp\n" +
                        "ORDER BY Temp.employeeID ASC";


        try {
            Statement statement = this.connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                int employeeID = set.getInt("employeeID");
                String employeeName = set.getString("employeeName");
                String department = set.getString("department");
                int salary = set.getInt("salary");

                QueryResult.LowSalaryEmployees employee = new QueryResult.LowSalaryEmployees(employeeID, employeeName, department, salary);
                resultList.add(employee);
            }

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        QueryResult.LowSalaryEmployees[] array = new QueryResult.LowSalaryEmployees[resultList.size()];
        return resultList.toArray(array);
    }


    /**
     * For the products of given productType, increase the productionCost of every unit by a given percentage.
     *
     * @return number of rows affected
     */
    public int increaseCost(String productType, double percentage) {
        int numberOfRows = 0;

        String query = "UPDATE produce P\n" +
                        "SET P.productionCost = P.productionCost * (1 + ? / 100)\n" +
                        "WHERE P.productID IN (SELECT T.productID\n" +
                                                "FROM product T\n" +
                                                "WHERE T.productType = ?)";

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setDouble(1, percentage);
            statement.setString(2, productType);

            numberOfRows = statement.executeUpdate();

            statement.close();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return numberOfRows;
    }


    /**
     * Deleting all employees that have not worked since the given date.
     *
     * @return number of rows affected
     */
    public int deleteNotWorkingEmployees(String givenDate) {
        int numberOfRows = 0;

        String deleteWorksInQuery = "DELETE FROM worksIn\n" +
                                    "WHERE startDate < ?";

        String deleteEmployeeQuery = "DELETE FROM employee\n" +
                                        "WHERE employeeId NOT IN (SELECT employeeId\n" +
                                        "FROM worksIn)";

        try {
            PreparedStatement deleteWorksInStatement = this.connection.prepareStatement(deleteWorksInQuery);
            deleteWorksInStatement.setString(1, givenDate);
            deleteWorksInStatement.executeUpdate();
            deleteWorksInStatement.close();


            Statement deleteEmployeeStatement = this.connection.createStatement();
            int rowsAffectedInEmployee = deleteEmployeeStatement.executeUpdate(deleteEmployeeQuery);
            deleteEmployeeStatement.close();

            numberOfRows = rowsAffectedInEmployee;
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        return numberOfRows;

    }


    /**
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     *  THE METHODS AFTER THIS LINE WILL NOT BE GRADED.
     *  YOU DON'T HAVE TO SOLVE THEM, LEAVE THEM AS IS IF YOU WANT.
     *  IF YOU HAVE ANY QUESTIONS, REACH ME VIA EMAIL.
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     */

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Peers are considered tied and receive the same rank. After
     * that, there should be a gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Everything is the same but after ties, there should be no
     * gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank2() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each factory, calculate the most profitable 4th product.
     *
     * @return QueryResult.FactoryProfit
     */
    public QueryResult.FactoryProfit calculateFourth() {
        return new QueryResult.FactoryProfit(0,0,0);
    }

    /**
     * Determine the salary variance between an employee and another
     * one who began working immediately after the first employee (by
     * startDate), for all employees.
     *
     * @return QueryResult.SalaryVariant[]
     */
    public QueryResult.SalaryVariant[] calculateVariance() {
        return new QueryResult.SalaryVariant[0];
    }

    /**
     * Create a method that is called once and whenever a Product starts
     * losing money, deletes it from Produce table
     *
     * @return void
     */
    public void deleteLosing() {

    }
}
