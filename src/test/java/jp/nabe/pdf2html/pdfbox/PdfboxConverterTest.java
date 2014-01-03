package jp.nabe.pdf2html.pdfbox;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import jp.nabe.pdf2html.Component;
import jp.nabe.pdf2html.Converter;
import jp.nabe.pdf2html.Html;
import jp.nabe.pdf2html.Page;
import jp.nabe.pdf2html.Resource;
import jp.nabe.pdf2html.Resources;
import jp.nabe.pdf2html.Template;

import org.junit.Before;
import org.junit.Test;

public class PdfboxConverterTest {

    private ByteArrayOutputStream data;

    @Before
    public void before() throws Exception {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        BufferedInputStream input = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("test.pdf"));
        byte [] buff = new byte[1024];
        int len = 0;
        while((len =  input.read(buff)) > 0) {
            data.write(buff, 0, len);
        }
        this.data = data;
    }

    @Test
    public void getPage() throws Exception {
        Converter converter = new PdfboxConverter(data.toByteArray());
        Page page = converter.getPage(3);
        Resources resources = page.getResources();
        Html html = page.getHtml();

        for (Resource resource : resources.toList()) {
            System.out.println(resource.getContentType());
        }

        Template template = new Template() {

            public String getEncoding() {
                return "UTF-8";
            }

            public String getHeader(String title, Component... components) {
                StringBuilder head = new StringBuilder("<!DOCTYPE html><html><head>")
                    .append(String.format("<meta http-equiv='Content-Type' content='text/html; charset=%s'>", getEncoding()))
                    .append("<title>").append(title).append("</title>")
                    .append("</head><body>");
                return head.toString();
            }

            public String getFooter(Component... components) {
                return "</body></html>";
            }

            public String getContent(Component... components) {
                StringBuilder content = new StringBuilder();
                for (Component component : components) {
                    content.append("<p>").append(component.getValue()).append("</p>\n");
                }
                return content.toString();
            }

        };
        String text = html.toString(template, resources);
        assertThat(text, is(notNullValue()));

        File file = new File("target/test-classes/test.html");
        System.out.println(file.getAbsolutePath());
        FileWriter writer = new FileWriter(file);
        writer.write(text);
        writer.close();

        converter.close();
    }

}
