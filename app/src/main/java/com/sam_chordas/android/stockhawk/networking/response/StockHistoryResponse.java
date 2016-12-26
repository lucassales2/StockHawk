package com.sam_chordas.android.stockhawk.networking.response;


import com.sam_chordas.android.stockhawk.networking.dto.QuoteHistoryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 19/12/16.
 */

public class StockHistoryResponse {

    private Result query;

    public Result getQuery() {
        return query;
    }

    public class Result {

        private Quotes results;
        private int count;

        public int getCount() {
            return count;
        }

        public Quotes getQuote() {
            return results;
        }
    }

    public class Quotes {

        private List<QuoteHistoryDto> quote = new ArrayList<>();

        public List<QuoteHistoryDto> getStockQuotes() {
            return quote;
        }
    }


}
