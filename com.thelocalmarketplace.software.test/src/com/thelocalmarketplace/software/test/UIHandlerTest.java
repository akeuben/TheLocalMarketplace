package com.thelocalmarketplace.software.test;

import com.thelocalmarketplace.software.session.UIHandler;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.Numeral;
import com.thelocalmarketplace.hardware.BarcodedProduct;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UIHandlerTest {
    private UIHandler my_ui_handler;
    private UserSession my_user_session;
    private Barcode my_barcode;
    private BarcodedProduct product;

    @Before
    public void setup() {
        my_user_session = new UserSession();
        my_ui_handler = new UIHandler(my_user_session);
    }

    @Test
    public void TestAddBagSelected() {
        my_user_session.setState(UserSessionState.READY_FOR_ITEM);
        my_ui_handler.addBagSelected();
        assertEquals(UserSessionState.WAITING_FOR_BAGGING, my_user_session.getState());
    }

    @Test
    public void TestRemoveItemSelected() {
        my_user_session.setState(UserSessionState.READY_FOR_ITEM);
      
        my_barcode = new Barcode(new Numeral []{Numeral.one, Numeral.one, Numeral.one});
        product = new BarcodedProduct(my_barcode, "aaa", 12.2, 50);

        my_ui_handler.removeItemSelected(product);
        assertEquals(UserSessionState.WAITING_FOR_BAGGING, my_user_session.getState());
    }

    @Test
    public void TestSkipBaggingSelected() {
        my_user_session.setState(UserSessionState.READY_FOR_ITEM);

        my_barcode = new Barcode(new Numeral []{Numeral.one, Numeral.one, Numeral.one});
        product = new BarcodedProduct(my_barcode, "aaa", 12.2, 50);

        my_ui_handler.skipBaggingSelected(product);
        assertEquals(UserSessionState.WAITING_FOR_ATTENDANT, my_user_session.getState());
    }
}
