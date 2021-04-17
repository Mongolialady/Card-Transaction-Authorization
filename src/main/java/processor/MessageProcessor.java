package processor;

import modules.TxnMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MessageProcessor {

    public String processMessage(String message) {
        if(message == null && message.length() <= 6){
            return "";
        }

        TxnMessage msg = parser(message);
        if (msg.getPanNum() == null || msg.getExpirationDate() == null || msg.getTxnAmount() == null) {
            msg.setResponseCode("ER");
        } else {

            boolean isValidDate = isValidExpirationDate(msg);
            boolean isValidAmount = isValidAmount(msg);

            if (isValidDate && isValidAmount) {
                msg.setResponseCode("OK");
            } else {
                msg.setResponseCode("DE");
            }
        }

        msg.setBitMap(msg.getBitMap() + 16);

        msg.setMsgTypeInd("0110");

        return msg.toString();
    }

    private boolean isValidExpirationDate(TxnMessage txnMessage) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYMM");
        LocalDate localDate = LocalDate.now();
        String dt = localDate.format(dtf);
        String yy = txnMessage.getExpirationDate().substring(2);
        String mm = txnMessage.getExpirationDate().substring(0, 2);
        if(Integer.parseInt(mm) > 12){
            return false;
        }

        String ed = yy + mm;
        return ed.compareTo(dt) > 0;
    }

    private TxnMessage parser(String message) {
        TxnMessage msg = new TxnMessage();
        int j = 0;
        // message type indicator
        msg.setMsgTypeInd(message.substring(j, j += 4));

        // bitMap
        int bitmap = Integer.parseInt(message.substring(j, j += 2), 16);
        msg.setBitMap(bitmap);
        String bm = Integer.toBinaryString(bitmap);
        while(bm.length() < 8){
            bm = "0" + bm;
        }

        int i = 0;
        // pan number
        if (bm.charAt(i++) == '1' && j < message.length()) {
            String x = message.substring(j, j + 2);
            int y = Character.getNumericValue(x.charAt(0)) * 10 + Character.getNumericValue(x.charAt(1));
            msg.setPanNum(message.substring(j, j += y + 2));
        }

        // expiration date
        if (bm.charAt(i++) == '1' && j < message.length()) {
            msg.setExpirationDate(message.substring(j, j += 4));
        }

        // transaction amount
        String ss = message.substring(j, j + 10);
        if (bm.charAt(i++) == '1' && j < message.length()) {
            msg.setTxnAmount(message.substring(j, j += 10));
        }

        i++;
        if (bm.charAt(i++) == '1' && j < message.length()) {
            String x = message.substring(j, j + 2);
            int y = Character.getNumericValue(x.charAt(0)) * 10 + Character.getNumericValue(x.charAt(1));
            msg.setCardholderName(message.substring(j, j += y + 2));
        }

        if (bm.charAt(i++) == '1' && j < message.length()) {
            msg.setZipCode(message.substring(j));
        }

        return msg;
    }

    private boolean isValidAmount(TxnMessage txnMessage) {
        int amount = Integer.parseInt(txnMessage.getTxnAmount()) / 100;
        if ((txnMessage.getZipCode() != null && amount < 200) || (txnMessage.getZipCode() == null && amount < 100)) {
            return true;
        }

        return false;
    }
}
