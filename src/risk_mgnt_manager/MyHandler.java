package risk_mgnt_manager;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class MyHandler extends DefaultHandler {
    //List to hold trade object
    private List<Portfolio> tradeList = null;
    private Portfolio trd = null;
    List<String> results; 
    //getter method for trade list
    public List<Portfolio> getEmpList() {
        return tradeList;
    }
    boolean bdate = false;
    boolean bfirm = false;
    boolean bacctId = false;
    boolean bUserId = false;
    boolean bseg = false;
    boolean btradedate = false;
    boolean btradetime = false;
    boolean bec = false;
    boolean bexch = false;
    boolean bpfcode = false;
    boolean bpftype = false;
    boolean bpe = false;
    boolean btradeqty = false;
    boolean btradeprice = false;
    boolean bmore = false;
 
    @Override
    /*
    This is the start of the xml reader where the tags in the document are
    confirmed. if they are present, the bit flags are changed to true to 
    represent data present at that tag
    */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        // each tag of the document needed must be present here
        if (qName.equalsIgnoreCase("portfolio")) {
            trd = new Portfolio(); // starts new list in Portfolio class
            //initialize list
            if (tradeList == null)
                tradeList = new ArrayList<>();  
        } else if (qName.equalsIgnoreCase("firm")) {
            bfirm = true;
        } else if (qName.equalsIgnoreCase("acctId")) {
            bacctId = true;
        } else if (qName.equalsIgnoreCase("UserId")) {
            bUserId = true;
        } else if (qName.equalsIgnoreCase("seg")) {
            bseg = true;
        } else if (qName.equalsIgnoreCase("trade")) {
           
        } else if (qName.equalsIgnoreCase("tradedate")) {
            btradedate = true;
        } else if (qName.equalsIgnoreCase("tradetime")) {
            btradetime = true;
        } else if (qName.equalsIgnoreCase("ec")) {
            bec = true;
        } else if (qName.equalsIgnoreCase("exch")) {
            bexch = true;
        } else if (qName.equalsIgnoreCase("pfcode")) {
            bpfcode = true;
        } else if (qName.equalsIgnoreCase("pftype")) {
            bpftype = true;
        } else if (qName.equalsIgnoreCase("pe")) {
            bpe = true;
        } else if (qName.equalsIgnoreCase("tradeqty")) {
            btradeqty = true;
        } else if (qName.equalsIgnoreCase("tradeprice")) {
            btradeprice = true;
        }   
    }

    @Override
    /*
    This is the oposite of startElement and serves as the flag to signal
    the end of a tag and the end of the data to be read in. Bit flags are changed
    back to false
    */
    public void endElement(String uri, String localName, String qName) throws SAXException {   
        if(qName.equalsIgnoreCase("tradeprice")) {
            btradeprice = false;
        } else if (qName.equalsIgnoreCase("tradeqty")) {
            btradeqty = false;
        } else if (qName.equalsIgnoreCase("pe")) {
            bpe = false;
        } else if (qName.equalsIgnoreCase("pftype")) {
            bpftype = false;
        } else if (qName.equalsIgnoreCase("pfcode")) {
            bpfcode = false; 
        } else if(qName.equalsIgnoreCase("exch")) {
            bexch = false; 
        } else if (qName.equalsIgnoreCase("ec")) {
            bec = false;
        } else if (qName.equalsIgnoreCase("tradetime")) {
            btradetime = false;
        } else if (qName.equalsIgnoreCase("tradedate")) {
            btradedate = false;
        } else if (qName.equalsIgnoreCase("trade")) {
            tradeList.add(trd); // data is sent to list for Portfolio class
        } else if (qName.equalsIgnoreCase("seg")) {
            bseg = false;
        } else if (qName.equalsIgnoreCase("UserId")) {
            bUserId = false;
        } else if (qName.equalsIgnoreCase("acctId")) {
            bacctId = false;
        } else if (qName.equalsIgnoreCase("firm")) {
            bfirm = false;
        } else if (qName.equalsIgnoreCase("portfolio")) {
          //  tradeList.add(trd);
        }
    }
    @Override
    /*
    This is where the actual data between the tags is extracted and sent to 
    the variables in class portfolio
    */
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bfirm) {
            trd.setFirm(new String(ch, start, length));
        } else if (bacctId) {
            trd.setAcctId(new String(ch, start, length));
        } else if (bUserId) {
            trd.setUserId(new String(ch, start, length));
        } else if (bseg) {
            trd.setSeg(new String(ch, start, length));
        } else if (btradedate) {
            trd.setTradedate(new String(ch, start, length));
        } else if (btradetime) {
            trd.setTradetime(new String(ch, start, length));
        } else if (bec) {
            trd.setEC(new String(ch, start, length));
        } else if (bexch) {
            trd.setExch(new String(ch, start, length));
        } else if (bpfcode) {
            trd.setPFCode(new String(ch, start, length));
        } else if (bpftype) {
            trd.setPFType(new String(ch, start, length));
        } else if (bpe) {
            trd.setPE(new String(ch, start, length));
        } else if (btradeqty) {
            trd.setTradeQty(new String(ch, start, length));
        } else if (btradeprice) {
            trd.setTradePrice(new String(ch, start, length));
        }  
    }
}