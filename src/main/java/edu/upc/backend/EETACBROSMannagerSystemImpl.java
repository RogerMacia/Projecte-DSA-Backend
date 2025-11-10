package edu.upc.backend;

import edu.upc.backend.classes.*;
import org.apache.log4j.Logger;

// Per a que en faci les traces li he passat al chat i m'ho ha fet autotamaticament ell

public class EETACBROSMannagerSystemImpl implements EETACBROSMannagerSystem {
    private static EETACBROSMannagerSystemImpl instance;

    private UsersList usersList;

    final static Logger logger = Logger.getLogger(EETACBROSMannagerSystemImpl.class);

    private EETACBROSMannagerSystemImpl() {
        this.usersList = new UsersList();
        logger.info("Constructor SistemaGestioBibliotecaImpl inicialitzat");
    }

    public static EETACBROSMannagerSystemImpl getInstance() {
        logger.info("Inici getInstance()");
        if (instance == null) {
            instance = new EETACBROSMannagerSystemImpl();
            logger.warn("Instancia creada");
        }
        logger.info("Fi getInstance() -> " + instance);
        return instance;
    }

    public void addUser(User user) {
        logger.info("Inici addLector(" + user + ")");
        if (user != null) {
            this.usersList.addUser(user);
            logger.info("Fi addLector() -> Lector afegit: " + user);
        } else {
            logger.warn("Intent d’afegir lector nul");
        }
    }

    public UsersList getUsersList() {
        logger.info("Inici getLlistaLectors()");
        logger.info("Fi getLlistaLectors() -> Retorna: " + usersList);
        return this.usersList;
    }

    public User getUserByUsername(String username) {
        logger.info("Inici getLector(username=" + username + ")");
        User user = usersList.getUserByUsername(username);
        logger.info("Fi getLector() -> Retorna: " + user);
        return user;
    }

    public void clear() {
        logger.info("Inici clear()");
        this.usersList = new UsersList();
        logger.info("Fi clear() -> Llista de lectors buidada");
    }
}
