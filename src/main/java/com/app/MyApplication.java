package com.app;

import com.app.database.services.impl.RequestServiceImpl;
import com.app.database.services.impl.UsersServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Максим Зеленский
 * @since 05.04.2020
 */
//@Provider
public class MyApplication extends ResourceConfig /*implements Feature*/ {

    public MyApplication() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new RequestServiceImpl()).to(RequestServiceImpl.class);
                bind(new UsersServiceImpl()).to(UsersServiceImpl.class);

            }
        });
        packages(true, "com.app.restapi");
    }

    /*@Inject
    public MyApplication(ServiceLocator locator) {
        super();
        packages("com.app.restapi");

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(locator);
        // add your Guice modules.
        Injector injector = Guice.createInjector(binder -> {
            binder.bind(RequestService.class).toInstance(new RequestServiceImpl());
            binder.bind(UsersService.class).toInstance(new UsersServiceImpl());
        });
        //Injector injector = Guice.createInjector();
        GuiceIntoHK2Bridge guiceBridge = locator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(injector);
    }*/

}
