import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Максим Зеленский
 * @since 05.04.2020
 */
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        register(new JerseyBinder());
        packages(true, "restapi");
    }

}
