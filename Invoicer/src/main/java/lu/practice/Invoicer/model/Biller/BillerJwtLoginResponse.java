package lu.practice.Invoicer.model.Biller;

import java.io.Serializable;
public class BillerJwtLoginResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    public BillerJwtLoginResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
    public String getToken() {
        return this.jwttoken;
    }
}
