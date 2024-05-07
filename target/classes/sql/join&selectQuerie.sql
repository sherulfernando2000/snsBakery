SELECT
    o.orderId,
    opd.productId,
    p.pName,
    opd.qty,
    opd.unitPrice,
    opd.total,
    o.totalAmount AS order_grossAmount,
    pmt.paymentId,
    pmt.totalAmount AS payment_netAmount,
    pmt.discountAmount AS payment_discountAmount,
    pmt.discountPrecentage,
    c.customerId,
    c.cName,
    c.phoneNo
FROM
    bakery.orders  o
        JOIN
    bakery.orderproductdetail opd ON o.orderId = opd.orderId
        JOIN
    bakery.payment pmt ON o.orderId = pmt.orderId
        JOIN
    bakery.customer c ON o.customerId = c.customerId
        JOIN
    bakery.product p ON opd.productId = p.productId
WHERE
        o.orderId = $P{orderId}