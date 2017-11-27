package proxyServer;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarPage;

import java.io.File;

public class ArchiveManager {

    public static void main(String[] args) {
        HarReader harReader = new HarReader();
        Har har = null;
        try {
            File file = new File(ArchiveManager.class.getClassLoader()
                    .getResource("file/Archive.har").getFile());
            har = harReader.readFromFile(file);
        } catch (HarReaderException e) {
            e.printStackTrace();
        }
        if (har == null || har.getLog() == null) {
            System.err.println("Invalid Http Archive File");
            System.exit(-1);
        }

        StringBuilder builder = new StringBuilder("HAR Info\n\r---------------------\n\r");
        builder.append("Creator:\t\t\t").append(har.getLog().getCreator().getName()).append("\n\r");
        builder.append("Version:\t\t\t").append(har.getLog().getCreator().getVersion()).append("\n\r");
        builder.append("Number of Pages:\t").append(har.getLog().getPages().size()).append("\n\r");
        for (HarPage page : har.getLog().getPages()) {
            builder.append("\t - Page Id:\t\t").append(page.getId()).append("\n\r");
            builder.append("\t - Date Time:\t").append(page.getStartedDateTime()).append("\n\r");
            builder.append("\t - Page Title:\t").append(page.getTitle()).append("\n\r");
        }
        builder.append("\n\r").append("Number of Entries:\t").append(har.getLog().getEntries().size()).append("\n\r");
        int i = 1;
        for (HarEntry entry : har.getLog().getEntries()) {
            builder.append("\t + Request Url:\t\t").append(entry.getRequest().getUrl()).append("\n\r");
            builder.append("\t + Response Headers Size:\t").append(entry.getResponse().getHeadersSize()).append("\n\r");
            builder.append("\t + Time (ms):\t\t").append(entry.getTime()).append("\n\r");
            builder.append("\t + Server Ip:\t\t").append(entry.getServerIPAddress()).append("\n\r");
            builder.append("\t---> ").append(i).append("\n\r");
            i++;
        }

        System.out.println(builder.toString());
    }
}
