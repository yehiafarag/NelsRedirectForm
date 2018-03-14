package com.uib.nelsgalaxyredirectform;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("nelsredirect")
public class NelsGalaxyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        final TextField SimpleSAMLAuthToken = new TextField();
        SimpleSAMLAuthToken.setCaption("SimpleSAMLAuthToken:");
        final TextField PHPSESSID = new TextField();
        PHPSESSID.setCaption("PHPSESSID:");
        final TextField AuthMemCookie = new TextField();
        AuthMemCookie.setCaption("AuthMemCookie:");
        final TextField jSessionId = new TextField();
        jSessionId.setCaption("NelsJSESSIONID:");

        final TextField galaxyURL = new TextField();
        galaxyURL.setCaption("Galaxy URL");
        Button button = new Button("Log in");
        button.addClickListener(e -> {
            initCookie("SimpleSAMLAuthToken", SimpleSAMLAuthToken.getValue());
            initCookie("PHPSESSID", PHPSESSID.getValue());
            initCookie("AuthMemCookie", AuthMemCookie.getValue());
            initCookie("galaxyUrl", galaxyURL.getValue());
            initCookie("NelsJSESSIONID", jSessionId.getValue());
            Page.getCurrent().open("http://localhost:8084/web-peptide-shaker/", "_self");

        });

        layout.addComponents(SimpleSAMLAuthToken, PHPSESSID, AuthMemCookie, jSessionId, galaxyURL, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        AuthMemCookie.setValue("_9ac470e8418e8d67023228c0fd082d8be11202a38a");
        jSessionId.setValue("6152FA65579137E45DB141C231FA842E");
        PHPSESSID.setValue("d2dd76d2dda319a15a3a5e9ad768cd2d");
        SimpleSAMLAuthToken.setValue("_e0a0018260dd3664e64687161751a30f7103067d7f");
        galaxyURL.setValue("https://test-fe.cbu.uib.no");
        setContent(layout);
//        initNelsCookies();

    }

    private String initNelsCookies() {

        try {
            String cookiesRequestProperty = "SimpleSAMLAuthToken=_c0d6b87c6d743e1a5a59a461b0e4538f1d6ec9d9e0;PHPSESSID=d2dd76d2dda319a15a3a5e9ad768cd2d;AuthMemCookie=_1394b2b7bfa55398f5dd90701562333f15aff230af";
            String url = "https://test-fe.cbu.uib.no/nels/welcome.xhtml?GALAXY_URL=https%3A//test-fe.cbu.uib.no/galaxy/tool_runner%3Ftool_id%3Dnels_file_browser&appCallbackUrl=https%3A//test-fe.cbu.uib.no/galaxy/tool_runner%3Ftool_id%3Dnels_file_browser%26runtool_btn%3DExecute&appName=Galaxy";
            URL obj = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

            conn.addRequestProperty("Cookie", "PHPSESSID=0869017484c4c822285ac53eddf107e7; SimpleSAMLAuthToken=_9f017ff6a4e822b92493f9d4edb02c8616404ce4b3; AuthMemCookie=_f378f85ab493cc351c00fbf8849ece085e5cf3db9a; galaxysession=c6ca0ddb55be603a57c17226caf3120ba215643251c7c2df78717325f185dfe3db04e4cf15e7f723");
            conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
            conn.addRequestProperty("Accept-Language", "ar,en-US;q=0.8,en;q=0.6,en-GB;q=0.4");
            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.addRequestProperty("Cache-Control", "no-cache");
            conn.addRequestProperty("Connection", "keep-alive");
            conn.addRequestProperty("Host", "test-fe.cbu.uib.no");
            conn.addRequestProperty("Referer", "https://test-fe.cbu.uib.no/galaxy");
            conn.connect();
            String sessionIdCookie = conn.getHeaderFields() + ";";
            System.out.println("------------------------ JSEESIONID------------------------" + sessionIdCookie);
            return sessionIdCookie;
        } catch (MalformedURLException ex) {
            Logger.getLogger(NelsGalaxyUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(NelsGalaxyUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NelsGalaxyUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void initCookie(String name, String value) {
        // Create a new cookie
        Cookie myCookie = new Cookie(name, value);
// Make cookie expire in 2 minutes
//        myCookie.setMaxAge(60 * 60 * 24 * 360);
        myCookie.setMaxAge(20);
// Set the cookie path.
        myCookie.setPath("/web-peptide-shaker/");
        VaadinService.getCurrentResponse().addCookie(myCookie);

    }

    @WebServlet(urlPatterns = "/*", name = "NelsGalaxyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NelsGalaxyUI.class, productionMode = false)
    public static class NelsGalaxyUIServlet extends VaadinServlet {
    }
}
