package processor;

import modules.TxnMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MessageProcessor {

    public String processMessage(String message) {
        TxnMessage msg = parser(message);
        if (msg.getMsgTypeInd() == null || msg.getBitMap() == null || msg.getPanNum() == null
                || msg.getExpirationDate() == null || msg.getTxnAmount() == null) {
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

        int bm = Integer.parseInt(msg.getBitMap().substring(0, 1), 16);
        bm += 1;
        msg.setBitMap(Integer.toHexString(bm) + msg.getBitMap().substring(1));

        msg.setMsgTypeInd("0110");

        return msg.toString();
    }

    private boolean isValidExpirationDate(TxnMessage txnMessage) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYMM");
        LocalDate localDate = LocalDate.now();
        String dt = localDate.format(dtf);
        String ed = txnMessage.getExpirationDate().substring(2) + txnMessage.getExpirationDate().substring(0, 2);

        return ed.compareTo(dt) > 0;
    }

    private TxnMessage parser(String message) {
        TxnMessage msg = new TxnMessage();
        int j = 0;
        // message type indicator
        if (j < message.length()) {
            msg.setMsgTypeInd(message.substring(j, j += 4));
        }

        // bitMap
        if (j < message.length()) {
            msg.setBitMap(message.substring(j, j += 2));
        }

        // pan number
        if (j < message.length()) {
            String x = message.substring(j, j + 2);
            int y = Character.getNumericValue(x.charAt(0)) * 10 + Character.getNumericValue(x.charAt(1));
            msg.setPanNum(message.substring(j, j += y + 2));
        }

        // expiration date
        if (j < message.length()) {
            msg.setExpirationDate(message.substring(j, j += 4));
        }

        // transaction amount
        if (j < message.length()) {
            msg.setTxnAmount(message.substring(j, j += 10));
        }

        if (j < message.length()) {
            String x = message.substring(j, j + 2);
            int y = Character.getNumericValue(x.charAt(0)) * 10 + Character.getNumericValue(x.charAt(1));
            msg.setCardholderName(message.substring(j, j += y + 2));
        }

        if (j < message.length()) {
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
