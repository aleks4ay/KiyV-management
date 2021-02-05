package artifact_new_manufplan.domain.view;

import artifact_new_manufplan.domain.model.Description;
import artifact_new_manufplan.domain.model.Invoice;
import artifact_new_manufplan.domain.model.Tmc;
import artifact_new_manufplan.domain.services.MainData;
import artifact_new_manufplan.domain.tools.DateConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

public class ViewData {
    private final String fileName;
    private final String dateStart;
    private final String dateEnd;

    public ViewData(String fileName, String dateStart, String dateEnd) {
        this.fileName = fileName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    MainData mainData = new MainData();

    public void printTmcMapToHtml(List<List<Description>> tmcListOfList) {
        int numberOfReports = mainData.getnumberOfDescription(tmcListOfList);
        Writer writer = null;
        try {
            writer = new FileWriter(new File(fileName));
            writer.write("<!DOCTYPE html>" + "<html lang=\"en\">\n");
//            writer.write("<head>" + "<meta charset=\"UTF-8\">\n");
            writer.write("<head>" + "<meta charset=\"ISO-1251\">\n");
            writer.write("<title>КИЙ-В</title>\n");
            writer.write("<link href=\"style1.css\" rel=\"stylesheet\" type=\"text/css\">\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("<H1>Данные по Технологичке, записей " + numberOfReports + " <i>("
                     + dateStart.replace("-", ".") + " - " + dateEnd.replace("-", ".") + ")</i> </H1>\n");
//            writer.write("<H3>С " + dateStart + " по " + dateEnd + "</H3>\n");
            writer.write("<div><table>\n");

            writer.write("<tr>\n");
//            writer.write("<th style=\"width: 50px;\">IdTmc</th>\n");
            writer.write("<th style=\"width: 280px;\">Т М С</th>\n");
            writer.write("<th style=\"width: 200px;\">Дополнительное описание</th>\n");
            writer.write("<th style=\"width: 60px;\">Длина</th>\n");
            writer.write("<th style=\"width: 65px;\">Ширина</th>\n");
            writer.write("<th style=\"width: 60px;\">Высота</th>\n");
            writer.write("<th style=\"width: 220px;\">Заказ (дата)</th>\n");
            writer.write("<th style=\"width: 200px;\">Клиент</th>\n");
            writer.write("<th style=\"width: 135px;\">Менеджер</th>\n");
            writer.write("<th style=\"width: 90px;\">Дата (в&nbsp;произв.)</th>\n");
            writer.write("<th style=\"width: 35px;\">Кол-во</th>\n");
            writer.write("<th style=\"width: 55px;\">Отгру- жено</th>\n");
            writer.write("<th >Инвойс (дата)</th>\n");
            writer.write("<th style=\"width: 55px;\">Нужно</th>\n");
            writer.write("<th style=\"width: 70px;\">Не хватает</th>\n");
            writer.write("</tr>\n");

            for (List<Description> descriptionList : tmcListOfList) {

                mainData.sortByDateToFactory(descriptionList);

                Description firstDescription = descriptionList.get(0);
                Tmc tmc = descriptionList.get(0).getTmc();
                int rowSpanTmc = descriptionList.size();
                int rowSpanInvoise = 1;

                int endQuantity = 0;
                for (Description d : descriptionList) {
                    int quantity = d.getQuantity() - d.getQuantityShipped();
                    if (quantity > 0) {
                        endQuantity += quantity;
                    }
                }

                boolean itIsFirstRow = true;
                for (Description d : descriptionList) {
                    writer.write("<tr>\n");
                    if (itIsFirstRow) {
/*02*/                  writer.write("<td rowspan='" + rowSpanTmc + "' style=\"text-align: left;\">" + tmc.getTmcDescription() +
                                "<b> (на&nbsp;складе&nbsp;" + tmc.getBalance() + "&nbsp;шт&nbsp;)</b>" + "</td>\n");
/*03*/                  writer.write("<td rowspan='" + rowSpanTmc + "' style=\"text-align: left;\">" + firstDescription.getDescrSecond() + "</td>\n");
/*04*/                  writer.write("<td rowspan='" + rowSpanTmc + "'>" + firstDescription.getSizeA() + "</td>\n");
/*05*/                  writer.write("<td rowspan='" + rowSpanTmc + "'>" + firstDescription.getSizeB() + "</td>\n");
/*06*/                  writer.write("<td rowspan='" + rowSpanTmc + "'>" + firstDescription.getSizeC() + "</td>\n");
                    }

                    int needToShipmentForAll = endQuantity-tmc.getBalance();
                    int needToShipment = d.getQuantity() - d.getQuantityShipped();

/*08*/              writer.write("<td style=\"text-align: left;\">" + d.getOrder().getDocNumber() + ", поз." +
                            d.getPosition() + " (" + DateConverter.dateToString(d.getOrder().getDateCreate().getTime()) + ") </td>\n");
/*09*/              writer.write("<td style=\"text-align: left;\">" + d.getOrder().getClientName() + "</td>\n");
/*10*/              writer.write("<td style=\"text-align: left;\">" + d.getOrder().getManagerName() + "</td>\n");
/*11*/              writer.write("<td>" + DateConverter.dateToString(d.getOrder().getDateToFactory().getTime()) + "</td>\n");
/*12*/              writer.write("<td>" + d.getQuantity() + "</td>\n");
/*13*/              writer.write("<td>" + d.getQuantityShipped() + "</td>\n");
/*14*/              writer.write("<td>\n");
                    Set<Invoice> invoices = d.getInvoices();
                    for (Invoice invoice : invoices) {
                        writer.write(invoice.getDocNomberInvoice() + " (" +
                                DateConverter.dateToString(invoice.getTimeInvoice().getTime()) + ") ");
                    }
                    writer.write("</td>\n");
/*15*/              writer.write("<td>" + needToShipment + "</td>\n");
                    if (itIsFirstRow) {
                        writer.write("<td style=\"background-color: rgba(140, 176, 255, 0.25);\" rowspan='" + rowSpanTmc + "'><b>");
                        writer.write(needToShipmentForAll > 0 ? String.valueOf(needToShipmentForAll) : "&nbsp;");
                        writer.write("&nbsp;</b></td>\n");
                    }
                    writer.write("</tr>\n");
                    itIsFirstRow = false;
                }
            }

            writer.write("</table>\n</div>\n");
            writer.write("");
            writer.write("");
            writer.write("</body>\n");
            writer.write("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public void printHtml(List<Description> descriptions) {
//        Writer writer = null;
//        try {
//            writer = new FileWriter(new File("print/file1.html"));
//            writer.write("<!DOCTYPE html>" + "<html lang=\"en\">");
//            writer.write("<head>" + "<meta charset=\"UTF-8\">");
//            writer.write("<title>КИЙ-В</title>");
//            writer.write("<link href=\"style1.css\" rel=\"stylesheet\" type=\"text/css\">");
//            writer.write("</head>");
//            writer.write("<body>");
//            writer.write("<H1>Данные по Технологичке</H1>");
//            writer.write("<div><table>");
//
//            writer.write("<tr>");
//            writer.write("<th style=\"width: 50px;\">IdTmc</th>");
//            writer.write("<th style=\"width: 280px;\">Т М С</th>");
//            writer.write("<th style=\"width: 280px;\">Описание</th>");
//            writer.write("<th style=\"width: 60px;\">Длина</th>");
//            writer.write("<th style=\"width: 60px;\">Ширина</th>");
//            writer.write("<th style=\"width: 60px;\">Высота</th>");
//            writer.write("<th style=\"width: 30px;\">поз.</th>");
//            writer.write("<th style=\"width: 90px;\">Заказ (дата)</th>");
//            writer.write("<th style=\"width: 130px;\">Клиент</th>");
//            writer.write("<th style=\"width: 120px;\">Менеджер</th>");
//            writer.write("<th style=\"width: 90px;\">Дата (в произв.)</th>");
//            writer.write("<th style=\"width: 30px;\">Кол-во</th>");
//            writer.write("<th style=\"width: 40px;\">Отгру- жено</th>");
//            writer.write("<th style=\"width: 150px;\">Инвойс\n(дата)</th>");
////            writer.write("<th style=\"width: 40px;\">На складе</th>");
//            writer.write("</tr>");
//
//            for (Description d : descriptions) {
//                writer.write("<tr>");
//                writer.write("<td>" + d.getTmc().getId() + "</td>");
//                writer.write("<td>" + d.getTmc().getTmcDescription() + " (на&nbsp;складе&nbsp;=&nbsp;" +
//                        d.getTmc().getBalance() + "&nbsp;шт&nbsp;)" + "</td>");
//                writer.write("<td>" + d.getDescrSecond() + "</td>");
//                writer.write("<td>" + d.getSizeA() + "</td>");
//                writer.write("<td>" + d.getSizeB() + "</td>");
//                writer.write("<td>" + d.getSizeC() + "</td>");
//                writer.write("<td>" + d.getPosition() + "</td>");
//                writer.write("<td>" + d.getOrder().getDocNumber() + " (" + dateToString(d.getOrder().getDateCreate().getTime()) +  ") </td>");
//                writer.write("<td>" + d.getOrder().getClientName() + "</td>");
//                writer.write("<td>" + d.getOrder().getManagerName() + "</td>");
//                writer.write("<td>" + dateToString(d.getOrder().getDateToFactory().getTime()) + "</td>");
//                writer.write("<td>" + d.getQuantity() + "</td>");
//                writer.write("<td>" + d.getQuantityShipped() + "</td>");
//                writer.write("<td>");
//                Set<Invoice> invoices = d.getInvoices();
//                for (Invoice invoice : invoices) {
//                    writer.write(invoice.getDocNomberInvoice() + "(" + dateToString(invoice.getTimeInvoice().getTime()) + ") ");
//                }
//                writer.write("</td>");
//                writer.write("</tr>");
//
//            }
//
//            writer.write("</table></div>");
//            writer.write("");
//            writer.write("");
//            writer.write("</body>");
//            writer.write("</html>");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
