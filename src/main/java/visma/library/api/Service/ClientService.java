package visma.library.api.Service;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import visma.library.api.Model.Order;
import visma.library.api.Repository.OrderRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    OrderRepo orderRepo;

    public void orderBook(Order order) throws IOException, ParseException {
        List<Order> ordersList =  orderRepo.getTakings();
        ordersList.add(order);
        orderRepo.saveToFile(ordersList);

    }

    public List<Order> getOrderByClient(String client) throws IOException, ParseException {
        List<Order> orders =  orderRepo.getTakings();
        if (!orders.isEmpty()) {
            return orders.stream().filter(x -> x.getClient().equals(client)).collect(Collectors.toList());
        } else {
            return orders;
        }
    }

}
