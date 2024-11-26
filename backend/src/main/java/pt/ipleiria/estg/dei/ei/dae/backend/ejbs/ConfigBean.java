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
    private ProductBean productBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");


    @PostConstruct
    public void populateDB() {
        System.out.println("Creating startup entities...");

        try {
            productBean.create("Leite", "Pingo Doce", 1.30f, 6, "");
            productBean.create("Cereais Chocolate", "Pingo Doce", 2.3f, 6, "Normal");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
