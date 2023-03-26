package ru.clevertec.ilkevich.receipt.service.PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The PdfGenerationService class is a service for generating a PDF file.
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */

@Component
@Log4j2
public class PdfGenerationService {

    private final PdfServiceInfo pdfServiceInfo;

    public PdfGenerationService(PdfServiceInfo pdfServiceInfo) {
        this.pdfServiceInfo = pdfServiceInfo;
    }

    /**
     * Creates a PDF file.
     *
     * @param cashReceipt object and converts it to String values that will be written to the file
     * @throws DocumentException when the document is not open (has been closed, or not yet opened).
     * @throws IOException       when the file is not found, corrupted, etc.
     */
    public void creatingPdfFile(CashReceipt cashReceipt) {
        StringBuilder namePdfFile = new StringBuilder();
        namePdfFile.append("checks/Clevertec_Template_")
                .append(cashReceipt.getCheckNumber())
                .append(".pdf");
        Path path = Paths.get(String.valueOf(namePdfFile));
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(path));
            document.open();
            PdfReader reader = new PdfReader("src/main/resources/pdf/Clevertec_Template.pdf");
            PdfImportedPage page = writer.getImportedPage(reader, 1);
            PdfContentByte contentByte = writer.getDirectContent();
            contentByte.addTemplate(page, 0, 0);

            Paragraph empty = new Paragraph("\n".repeat(13));
            document.add(empty);

            Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            String headerCashReceipt = pdfServiceInfo.checkHeader(cashReceipt);
            Paragraph paragraphHeader = new Paragraph(15, headerCashReceipt, font);
            paragraphHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphHeader);

            PdfPTable table = new PdfPTable(3);
            pdfServiceInfo.addTableHeader(table);
            pdfServiceInfo.addRows(table, cashReceipt.getSetProduct());
            document.add(table);

            Font font1 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            String totalSumCashReceipt = pdfServiceInfo.total(cashReceipt);
            Paragraph paragraphTotalSum = new Paragraph(15, totalSumCashReceipt, font1);
            paragraphTotalSum.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraphTotalSum);

            document.close();
            writer.close();
            log.debug(namePdfFile + " - PDF CREATED");
        } catch (DocumentException | IOException e) {
            log.info(e.getMessage() + " - PDF NOT CREATED");
        }
    }
}

