package es.atrujillo.mjml.util;

public class TestUtils {

    public static final String HELLO_WORLD_MJML = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-text>Hello World</mj-text></mj-column></mj-section></mj-container></mj-body></mjml>";
    public static final String MALFORMED_TEMPLATE = "<mjml><mj-body><mj-container><mj-section><mj-column><mj-invalid>Hello World</mj-text></mj-column></mj-section></mj-container></mj-body></mjml>";
    public static final String MJML_VERSION4_TEMPLATE = "<mjml> <mj-body> <mj-section> <mj-column> <mj-image width=\"100\" src=\"/assets/img/logo-small.png\"></mj-image> <mj-divider border-color=\"#F45E43\"></mj-divider> <mj-text font-size=\"20px\" color=\"#F45E43\" font-family=\"helvetica\">Hello World</mj-text> </mj-column> </mj-section> </mj-body> </mjml>";
    public static final String INVALID_TEMPLATE = "<template><invalid><must>Return Errors</must></invalid></template>";
    public static final String MJML_APP_ID = "MJML_APP_ID";
    public static final String MJML_SECRET_KEY = "MJML_SECRET_KEY";

}
