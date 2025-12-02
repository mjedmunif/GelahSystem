package org.example.gelahsystem.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateInvoicePdf(String clientName, String orderName, double price) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("فاتورة الطلب"));
            document.add(new Paragraph("-----------Gelah------------"));
            document.add(new Paragraph("name: " + clientName));
            document.add(new Paragraph("order: " + orderName));
            document.add(new Paragraph("price: " + price + " ريال"));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("PDF error", e);
        }

        return out.toByteArray();
    }
}