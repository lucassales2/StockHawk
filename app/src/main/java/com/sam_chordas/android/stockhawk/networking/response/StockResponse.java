package com.sam_chordas.android.stockhawk.networking.response;

import com.sam_chordas.android.stockhawk.networking.dto.QuoteDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 26/12/16.
 */

public class StockResponse {

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

        private List<QuoteDto> quote = new ArrayList<>();

        public List<QuoteDto> getQuotes() {
            return quote;
        }

    }

}
