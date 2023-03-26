package ru.clevertec.ilkevich.receipt.service.PDF;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.Product;

import java.util.Set;
import java.util.stream.Stream;

/**
 * The PDFServiceInfo class is a service for converting a CashReceipt object into a String value for writing to PDF.
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */

@Component
@Log4j2
public class PdfServiceInfo {

    /**
     * Creation of check header by converting object data to String.
     *
     * @param cashReceipt object and converts it to String values that will be written to the file.
     * @return check header String type values.
     */
    public String checkHeader(CashReceipt cashReceipt) {
        StringBuilder header = new StringBuilder();
        header.append("Cash receipt\n")
                .append(cashReceipt.getShopInfo().getShopName())
                .append("\n")
                .append(cashReceipt.getShopInfo().getAddress())
                .append("\n")
                .append(cashReceipt.getShopInfo().getPhoneNumber())
                .append("\n")
                .append(cashReceipt.getCheckNumber()).append(" ")
                .append(cashReceipt.getDateCreation());
        return String.valueOf(header);
    }

    /**
     * Creating a product table header.
     */
    public void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Name product", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GREEN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_BOTTOM);
                    table.addCell(header);
                });
    }

    /**
     * Populating a table with values.
     */
    public void addRows(PdfPTable table, Set<Product> productSet) {
        for (Product product : productSet) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(String.valueOf(product.getName()));
            table.addCell(String.valueOf(product.getPrice()));
        }
    }

    /**
     * Creating a check total line.to String.
     *
     * @param cashReceipt object and converts it to String values that will be written to the file.
     * @return check total sum String type values.
     */
    public String total(CashReceipt cashReceipt) {
        Double totalSum = 0.0;
        for (Product product : cashReceipt.getSetProduct()) {
            totalSum += product.getPrice();
        }
        byte discount = cashReceipt.getDiscountCard().getDiscount();
        double discountSum = totalSum * discount / 100;
        double totalWithDiscount = totalSum - discountSum;
        return String.format("""
                Total = %.2f
                Discount %d = %.2f
                TOTAL DISCOUNT = %.2f""", totalSum, discount, discountSum, totalWithDiscount);
    }
}
