# coding_challenge
Deutsche Bank coding challenge

## goal
The goal in this project was to calculate some daily aggegates for a market historical log. The aggregates are open price, close price, highest price and daily traded volume. The index is also calculated

## functionality
When Runner.java is run the program prints the daily aggregates for each ticker. Each daily aggregate is an Object with the specific values as fields. For the part when calculating the index a "oldValue" field was added. The Runner reads the csv file line by line and stores the information in a TreeMap. Inside this TreeMap each day is associated with a HashMap storing all the daily aggregates. If a ticker didn't have any trades for that day then the last known DailyAggregate is stored inside the HashMap with its "oldValue" set to true so that it isn't printed again.
