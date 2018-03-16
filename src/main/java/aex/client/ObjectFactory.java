
package aex.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the aex.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _KoersenResponse_QNAME = new QName("http://server.aex/", "koersenResponse");
    private final static QName _Koersen_QNAME = new QName("http://server.aex/", "koersen");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: aex.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KoersenResponse }
     * 
     */
    public KoersenResponse createKoersenResponse() {
        return new KoersenResponse();
    }

    /**
     * Create an instance of {@link Koersen }
     * 
     */
    public Koersen createKoersen() {
        return new Koersen();
    }

    /**
     * Create an instance of {@link Fonds }
     * 
     */
    public Fonds createFonds() {
        return new Fonds();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KoersenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.aex/", name = "koersenResponse")
    public JAXBElement<KoersenResponse> createKoersenResponse(KoersenResponse value) {
        return new JAXBElement<KoersenResponse>(_KoersenResponse_QNAME, KoersenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Koersen }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.aex/", name = "koersen")
    public JAXBElement<Koersen> createKoersen(Koersen value) {
        return new JAXBElement<Koersen>(_Koersen_QNAME, Koersen.class, null, value);
    }

}
