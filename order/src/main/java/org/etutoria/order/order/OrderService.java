package org.etutoria.order.order;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.etutoria.order.customer.CustomerClient;
import org.etutoria.order.exception.BusinessException;
import org.etutoria.order.kafka.OrderConfirmation;
import org.etutoria.order.kafka.OrderProducer;
import org.etutoria.order.orderline.OrderLineRequest;
import org.etutoria.order.orderline.OrderLineService;
import org.etutoria.order.payment.PaymentClient;
import org.etutoria.order.payment.PaymentRequest;
import org.etutoria.order.product.ProductClient;
import org.etutoria.order.product.PurchaseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;//pour communiquer avec le microservice customer pour vérifier si le client existe
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        //verifier si le client existe en utilisant OpenFeign
        //recuperer les produits  achetés en utilisant le productmicroservice en utilisant RestTemplate
        //sauvegarder la commande car elle contient les informations de la commande
        //sauvegarder les lignes de commande
        //commencer le processus de paiement
        //envoyer un message de confirmation  au microservice de notification avec kafka
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = productClient.purchaseProducts(request.products());//acheter les produits ici nous allons utiliser RestTemplate pour communiquer avec le microservice product

        var order = this.repository.save(mapper.toOrder(request));//sauvegarder la commande avant de sauvegarder les lignes de commande car les lignes de commande dépendent de la commande

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }


    // Méthode pour trouver les commandes d'un client
    public List<OrderResponse> findOrdersByCustomerId(String customerId) {
        // Récupérer les commandes du client
        List<Order> orders = this.repository.findByCustomerId(customerId);

        // Mapper les entités Order en OrderResponse
        return orders.stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }
}
