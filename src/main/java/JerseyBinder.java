import database.services.RequestService;
import database.services.UsersService;
import database.services.impl.RequestServiceImpl;
import database.services.impl.UsersServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

/**
 * @author Максим Зеленский
 * @since 11.04.2020
 */
public class JerseyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(new RequestServiceImpl()).to(RequestService.class);
        bind(new UsersServiceImpl()).to(UsersService.class);
    }
}
