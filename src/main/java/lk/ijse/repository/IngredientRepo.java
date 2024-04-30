package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Ingredient;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepo {
    public static boolean save(Ingredient ingredient) throws SQLException {
        String sql = "INSERT INTO ingredient VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, ingredient.getId());
        pstm.setObject(2, ingredient.getName());
        pstm.setObject(3, ingredient.getCategory());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Ingredient ingredient) throws SQLException {
        String sql = "UPDATE ingredient SET iName = ?,  category = ? WHERE  ingredientId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, ingredient.getName());
        pstm.setObject(2, ingredient.getCategory());
        pstm.setObject(3, ingredient.getId());


        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM ingredient WHERE  ingredientId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static Ingredient searchById(String id) throws SQLException {
        String sql = "SELECT * FROM ingredient WHERE  ingredientId  = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String ingre_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String category = resultSet.getString(3);


            Ingredient ingredient = new Ingredient(ingre_id,name,category);

            return ingredient;
        }

        return null;
    }

    public static List<Ingredient> getAll() throws SQLException {
        String sql = "SELECT * FROM ingredient";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Ingredient> ingredList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String category = resultSet.getString(3);


            Ingredient ingredient = new Ingredient(id,name,category);
            ingredList.add(ingredient);
        }
        return ingredList;
    }

    public static List<String> getName() throws SQLException {
        String sql = "SELECT iName FROM ingredient";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> nameList = new ArrayList<>();
        while (resultSet.next()) {
            nameList.add(resultSet.getString(1));
        }
        return nameList;
    }

    public static Ingredient searchByName(String nameValue) throws SQLException {
        String sql = "SELECT * FROM ingredient WHERE iName = ?";
        PreparedStatement pstm = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,nameValue);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Ingredient(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
        }

        return null;
    }

    public static String getName(String ingredientId) throws SQLException {
        String sql = "SELECT iName FROM ingredient WHERE ingredientId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,ingredientId);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String iName = resultSet.getString(1);

            return iName;
        }
        return null;

    }
}
