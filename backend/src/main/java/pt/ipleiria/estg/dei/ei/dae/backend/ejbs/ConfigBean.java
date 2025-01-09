package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private ClientBean clientBean;

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");


    @PostConstruct
    public void populateDB() {
        System.out.println("Creating startup entities...");

        //productBean.create("Leite", "Pingo Doce", 1.30f, 6, "Normal");
        //productBean.create("Cereais Chocolate", "Pingo Doce", 2.3f, 6, "Normal");

        System.out.println("Creating clients...");
        try {
            clientBean.create("client1", "123", "Client1", "client1@mail.pt");
            clientBean.create("client2", "123", "Client2", "client2@mail.pt");
            clientBean.create("client3", "123", "Client3", "client3@mail.pt");
            clientBean.create("client4", "123", "Client4", "client4@mail.pt");
            clientBean.create("client5", "123", "Client5", "client5@mail.pt");
            clientBean.create("client6", "123", "Client6", "client6@mail.pt");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        try {
            administratorBean.create("admin1", "123", "Admin1", "admin1@mail.pt");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        System.out.println("Creating orders...");
        try {
            System.out.println("Client 1 orders...");
            orderBean.create(1,"client1","Tua mae");
            productBean.create(1, "Leite Meio Gordo", "Pingo Doce",1.3f,"Lacticínios",6,"Normal",1);
            productBean.create(2, "Cereais Chocolate", "Pingo Doce",2.3f,"Mercearia",3,"Normal",1);
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
        }


    }
}
