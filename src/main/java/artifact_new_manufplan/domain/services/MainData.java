package artifact_new_manufplan.domain.services;

import artifact_new_manufplan.domain.model.Description;
import artifact_new_manufplan.domain.model.Invoice;
import artifact_new_manufplan.domain.model.Order;
import artifact_new_manufplan.domain.model.Tmc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import artifact_new_manufplan.domain.tools.DateConverter;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static artifact_new_manufplan.log.ClassNameUtil.getCurrentClassName;

public class MainData {
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public List<Description> getAllDescription() {

        List<Description> descriptions = new DescriptionReader().getAll();
        log.info("Was return {} Description from Postgres.", descriptions.size());

        Map<String, Tmc> technoProducts = new TmcReader().getAllAsMap();
        log.info("Was return {} technoProducts from Postgres.", technoProducts.size());

        descriptions = filterOnlyTechno(descriptions, technoProducts);
        log.info("Was return {} Description after filtering by TechnoProducts.", descriptions.size());

        Map<String, Order> orders = new OrderReader().getAllAsMap();
        log.info("Was return {} orders from Postgres.", orders.size());
        Map<String, List<Invoice>> invoiceMap = new InvoiceReader().getAllAsMapOfList();
        log.info("Was return {} List<Invoice> from Postgres.", invoiceMap.size());

        for (Description descr : descriptions) {
            String idTmc = descr.getTmc().getId();
            Tmc tmc = technoProducts.get(idTmc);
            if (tmc != null) {
                descr.setTmc(tmc);
            }
            String idOrder = descr.getIdOrder();
            Order order = orders.get(idOrder);
            if (order != null) {
                descr.setOrder(order);
            }

            List<Invoice> newInvoices = invoiceMap.get(idOrder);
            if (newInvoices != null) {
                for (Invoice invoice : newInvoices) {
                    if (idTmc.equals(invoice.getIdTmc())) {
                        descr.getInvoices().add(invoice);
                    }
                }
            }
        }
        return descriptions;
    }

    private List<Description> filterOnlyTechno(List<Description> descriptions, Map<String, Tmc> tmcMap) {
        List<Description> descriptionsAfterFilter = new ArrayList<>();
        for (Description description : descriptions) {
            String idTmcFromDescription = description.getTmc().getId();

            if (tmcMap.containsKey(idTmcFromDescription)) {
                descriptionsAfterFilter.add(description);
            }
        }
        return descriptionsAfterFilter;
    }

    public List<Description> filterOpenWithDate (List<Description> descriptions, String minDayMonthYearDividedDash,
                                             String maxDayMonthYearDividedDash) {
        Timestamp dateStart = DateConverter.getTimestampFromString(minDayMonthYearDividedDash);
        Timestamp dateEnd = DateConverter.getTimestampFromString(maxDayMonthYearDividedDash);
        return descriptions.stream()
                .filter(d -> d.getQuantity() > d.getQuantityShipped())
                .filter(d -> d.getOrder().getDateToFactory().after(dateStart))
                .filter(d -> d.getOrder().getDateToFactory().before(dateEnd))
                .collect(Collectors.toList());
    }

    public List<Description> sortByIdTmc(List<Description> oldDescriptions) {
        Comparator<Description> comparatorIdTmc = (o1, o2) -> o1.getTmc().getId().compareTo(o2.getTmc().getId());
        Collections.sort(oldDescriptions, comparatorIdTmc);
        return oldDescriptions;
    }

    public List<Description> sortByDescriptionTmc(List<Description> oldDescriptions) {
        Comparator<Description> comparatorIdTmc = (o1, o2) ->
                o1.getTmc().getTmcDescription().compareTo(o2.getTmc().getTmcDescription());
        Collections.sort(oldDescriptions, comparatorIdTmc);
        return oldDescriptions;
    }

    public List<List<Description>> sortByListDescriptionTmc(List<List<Description>> oldList) {
        Comparator<List<Description>> comparatorIdTmc = (o1, o2) ->
                o1.get(0).getTmc().getTmcDescription().compareTo(o2.get(0).getTmc().getTmcDescription());
        Collections.sort(oldList, comparatorIdTmc);
        return oldList;
    }

    public Map<String, List<Description>> getTmcAsMapOfList(List<Description> descriptionList) {
        Map<String, List<Description>> tmcMap = new HashMap<>();
        int key = 0;
        for (Description descr : descriptionList) {
            String idTmcFromDescription = descr.getTmc().getId();

            if (! tmcMap.containsKey(idTmcFromDescription)) {
                List<Description> tempDescriptionList = new ArrayList<>();
                tempDescriptionList.add(descr);
                tmcMap.put(idTmcFromDescription, tempDescriptionList);
            }
            else {
                List<Description> tempDescriptionList = tmcMap.get(idTmcFromDescription);
                tempDescriptionList.add(descr);
            }
        }
        return tmcMap;
    }

    public List<List<Description>> getTmcAsListOfList(List<Description> descriptionList) {
        Map<String, List<Description>> tmcMap = getTmcAsMapOfList(descriptionList);
        List<List<Description>> result =  new ArrayList<>();
        result.addAll(tmcMap.values());
        return result;
    }

    public int getnumberOfDescription(List<List<Description>> descriptionListofList) {
        int result = 0;
        for (List<Description> descriptionList : descriptionListofList){
            result += descriptionList.size();
        }
        return result;
    }

    public List<Description> sortByDateToFactory(List<Description> oldDescriptions) {
        Comparator<Description> comparatorIdTmc = (o1, o2) ->
                o1.getOrder().getDateToFactory().compareTo(o2.getOrder().getDateToFactory());
        Collections.sort(oldDescriptions, comparatorIdTmc);
        return oldDescriptions;
    }
}
