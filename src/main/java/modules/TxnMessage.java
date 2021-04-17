package modules;

public class TxnMessage {
    private String msgTypeInd;
    private int bitMap;
    private String panNum;
    private String expirationDate;
    private String txnAmount;
    private String responseCode;
    private String cardholderName;
    private String zipCode;

    public String getMsgTypeInd() {
        return msgTypeInd;
    }

    public void setMsgTypeInd(String msgTypeInd) {
        this.msgTypeInd = msgTypeInd;
    }

    public int getBitMap() {
        return bitMap;
    }

    public void setBitMap(int bitMap) {
        this.bitMap = bitMap;
    }

    public String getPanNum() {
        return panNum;
    }

    public void setPanNum(String panNum) {
        this.panNum = panNum;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.msgTypeInd != null){
            sb.append(this.msgTypeInd);
        }

        if(this.bitMap != 0){
            sb.append(Integer.toHexString(this.bitMap));
        }

        if(this.panNum != null){
            sb.append(this.panNum);
        }

        if(this.expirationDate != null){
            sb.append(this.expirationDate);
        }

        if(this.txnAmount != null){
            sb.append(this.txnAmount);
        }

        if(this.responseCode != null){
            sb.append(this.responseCode);
        }

        if(this.cardholderName != null){
            sb.append(this.cardholderName);
        }

        if(this.zipCode != null){
            sb.append(this.zipCode);
        }

        return sb.toString();
    }
}
