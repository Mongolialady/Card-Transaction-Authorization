package processor;

import org.junit.Assert;
import org.junit.Test;

public class MessageProcessorTest {
    @Test
    public void testApprove_ZipCodeNotNull(){
        MessageProcessor messageProcessor = new MessageProcessor();
        Assert.assertEquals("0110fc16510510510510510012250000011000OK11MASTER YODA90089", messageProcessor.processMessage("0100ec1651051051051051001225000001100011MASTER YODA90089"));
    }

    @Test
    public void testDecline_ZipCodeNotNull(){
        MessageProcessor messageProcessor = new MessageProcessor();
        Assert.assertEquals("0110fc16510510510510510012250000020000DE11MASTER YODA90089", messageProcessor.processMessage("0100ec1651051051051051001225000002000011MASTER YODA90089"));
    }

    @Test
    public void testApprove_ZipCodeNull(){
        MessageProcessor messageProcessor = new MessageProcessor();
        Assert.assertEquals("0110f016411111111111111112250000001000OK", messageProcessor.processMessage("0100e016411111111111111112250000001000"));
    }

    @Test
    public void testDecline_ZipCodeNull(){
        MessageProcessor messageProcessor = new MessageProcessor();
        Assert.assertEquals("0110f016411111111111111112250000011000DE", messageProcessor.processMessage("0100e016411111111111111112250000011000"));
    }

    @Test
    public void testDecline_InvalidExpirationDate(){
        MessageProcessor messageProcessor = new MessageProcessor();
        Assert.assertEquals("0110f016411111111111111112180000001000DE", messageProcessor.processMessage("0100e016411111111111111112180000001000"));
    }

    @Test
    public void testError(){
        MessageProcessor messageProcessor = new MessageProcessor();
        Assert.assertEquals("01107012250000001000ER", messageProcessor.processMessage("01006012250000001000"));
    }
}