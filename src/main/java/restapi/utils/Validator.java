package restapi.utils;

import exceptions.NotAllFieldsAreFilledException;
import restapi.pojo.RequestPojo;

/**
 * @author Максим Зеленский
 * @since 03.04.2020
 */
public class Validator {
    public static void requestIsValid(RequestPojo request) throws NotAllFieldsAreFilledException {
        if (request.getFirst_name() == null || request.getLast_name() == null ||
            request.getBirth_date() == null || request.getGender() == null ||
            request.getRequest_date() == null) {
            throw new NotAllFieldsAreFilledException("Some of not null fields in request was not filled");
        }

        if (request.getPassport_series() == null || request.getPassport_issue_date() == null ||
            request.getPassport_number() == null || request.getPassport_issuer() == null) {
            if (request.getPlace_type() == null || request.getSettlement() == null) {
                throw new NotAllFieldsAreFilledException("Some of not null fields like passport or birth place data in request was not filled");
            }
        }
    }
}
