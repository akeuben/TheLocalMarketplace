import com.thelocalmarketplace.hardware.BarcodedProduct;
import java.util.ArrayList;
import com.jjjwelectronics.scanner.Barcode;
import static com.thelocalmarketplace.hardware.external.ProductDatabases.BARCODED_PRODUCT_DATABASE;

public class Transaction {

    /**
     * Items contained in an instance of transaction TODO Create constructor
     */
    public static final ArrayList<BarcodedProduct> products = new ArrayList<>();

    /**
     * Adds product to transaction array list
     * @param barcode
     */
    public static void addItem(Barcode barcode) {
        if (barcode != null) {
            BarcodedProduct product = BARCODED_PRODUCT_DATABASE.get(barcode);
            products.add(product);
        }
        else {
            throw new NullPointerException("barcode");
        }

    }
}