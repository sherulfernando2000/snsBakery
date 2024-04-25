package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);


        try {
        boolean isOrderSaved = OrderRepo.save(po.getOrder());
        if (isOrderSaved ) {
             boolean isQtyUpdated = ProductRepo.update(po.getOdList());
             if (isQtyUpdated ) {
                boolean isOrderDetailSaved = OrderProductDetailRepo.save(po.getOdList());
                 if (isOrderDetailSaved ) {
                     boolean isPaymentSaved = PaymentRepo.save(po.getPayment());
                        if (isPaymentSaved ) {
                            connection.commit();
                            return true;
                     }
                    }
             }
        }
        connection.rollback();
        return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }


        /*Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);


            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            if (isOrderSaved ) {
                boolean isQtyUpdated = ProductRepo.update(po.getOdList());
                if (isQtyUpdated ) {
                    boolean isOrderDetailSaved = OrderProductDetailRepo.save(po.getOdList());
                    if (isOrderDetailSaved ) {
                        boolean isPaymentSaved = PaymentRepo.save(po.getPayment());
                        if (isPaymentSaved ) {
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        */


        /*Connection connection = DbConnection.getInstance().getConnection();
        boolean isQtyUpdated = ProductRepo.update(po.getOdList());
        if (isQtyUpdated ) {
            return true;
        }
        return false;*/

        /*Connection connection = DbConnection.getInstance().getConnection();
        boolean isOrderDetailSaved = OrderProductDetailRepo.save(po.getOdList());
        if (isOrderDetailSaved ) {
            return true;
        }
        return false;*/




        /*Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            if (isOrderSaved) {
                boolean isQtyUpdated = ProductRepo.update(po.getOdList());
                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = OrderProductDetailRepo.save(po.getOdList());
                    if (isOrderDetailSaved) {
                        boolean isPaymentSaved = PaymentRepo.save(po.getPayment());
                        if (isPaymentSaved ) {
                            connection.commit();
                            return true;
                        }

                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }*/
    }
}
