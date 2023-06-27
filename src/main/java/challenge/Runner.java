package challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Runner {
    public static void main(String[] args) {

        Map<String, Map<String, DailyAggregate>> dailyAggregates = new TreeMap<>();
        String[] tickers = new String[] {"ABC", "MEGA", "NGL", "TRX"};
        // helps to get values for the trades of the day before when there were no trades for a ticker in that day
        Map<String, DailyAggregate> lastKnownAggregates = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("files/test-market.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";");

                String dateTime = columns[0];
                String ticker = columns[1];
                double price = Double.parseDouble(columns[2].replace(",", "."));
                int trades = Integer.parseInt(columns[3]);

                // Get or create the list of aggregates for the current date
                String date = dateTime.split(" ")[0];
                // add the date key with an empty HashMap only when absent
                dailyAggregates.putIfAbsent(date, new HashMap<>());
                Map<String, DailyAggregate> tickerData = dailyAggregates.get(date);
                tickerData.putIfAbsent(ticker, new DailyAggregate());

                // If there is not an existing aggregate then aggregate is a new DailyAggregate object
                DailyAggregate aggregate = tickerData.getOrDefault(ticker, new DailyAggregate());

                if (aggregate.getOpenPrice() == 0) {
                    aggregate.setOpenPrice(price);
                }
                // will be the last appearing line for the ticker with the specific date
                aggregate.setClosePrice(price);
                // replace the old price with the current one if the current one is bigger
                aggregate.setHighestPrice(Math.max(aggregate.getHighestPrice(), price));
                aggregate.setLowestPrice(Math.min(aggregate.getLowestPrice(), price));
                // sums up each entry of price * number_traded for the whole day
                aggregate.setDailyTradedVolume(aggregate.getDailyTradedVolume() + (price * trades));

                tickerData.put(ticker, aggregate);
            }

            for (Map.Entry<String, Map<String, DailyAggregate>> entry : dailyAggregates.entrySet()) {
                String date = entry.getKey();
                Map<String, DailyAggregate> tickerData = entry.getValue();

                System.out.println("******" + date + "******");

                for (String ticker : tickers) {
                    boolean tickerPresent = false;

                    if (!tickerData.containsKey(ticker)) {
                        DailyAggregate lastKnownAggregate = lastKnownAggregates.get(ticker);
                        if (lastKnownAggregate != null) {
                            lastKnownAggregate.setOldValue(true);
                            // the lastKnownAggregate is added to the current date and used to calculate the index
                            // but is not printed
                            tickerData.put(ticker, lastKnownAggregate);
                        }
                    } else {
                        tickerPresent = true;
                    }

                    if (!tickerPresent) {
                        System.out.println("---" + ticker + "---");
                        System.out.println("N/A");
                        System.out.println();
                    }
                }

                double indexOpenPrice = 0.0;
                double indexClosePrice = 0.0;
                double indexHighestPrice = 0.0;
                double indexLowestPrice = 0.0;

                for (Map.Entry<String, DailyAggregate> aggregateEntry : tickerData.entrySet()) {
                    String ticker = aggregateEntry.getKey();
                    DailyAggregate aggregate = aggregateEntry.getValue();

                    // Update the last known aggregates with the current aggregate
                    lastKnownAggregates.put(ticker, aggregate);

                    if (!aggregate.isOldValue()) {
                        System.out.println("---" + ticker + "---");
                        System.out.println("Open Price: " + aggregate.getOpenPrice());
                        System.out.println("Close Price: " + aggregate.getClosePrice());
                        System.out.println("Highest Price: " + aggregate.getHighestPrice());
                        System.out.println("Lowest Price: " + aggregate.getLowestPrice());
                        System.out.println("Daily Traded Volume: " + aggregate.getDailyTradedVolume());
                        System.out.println();
                    }

                    double weight = switch (ticker) {
                        case "ABC" -> 0.1;
                        case "MEGA" -> 0.3;
                        case "NGL" -> 0.4;
                        case "TRX" -> 0.2;
                        default -> 0.0;
                    };

                    indexOpenPrice += (aggregate.getOpenPrice() + (aggregate.getOpenPrice() * weight));
                    indexClosePrice += (aggregate.getClosePrice() + (aggregate.getClosePrice() * weight));
                    indexHighestPrice += (aggregate.getHighestPrice() + (aggregate.getHighestPrice() * weight));
                    indexLowestPrice += (aggregate.getLowestPrice() + (aggregate.getLowestPrice() * weight));
                }
                System.out.println("---INDEX---");
                System.out.println("Index Open Price: " + indexOpenPrice);
                System.out.println("Index Close Price: " + indexClosePrice);
                System.out.println("Index Highest Price: " + indexHighestPrice);
                System.out.println("Index Lowest Price: " + indexLowestPrice);
                System.out.println();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
