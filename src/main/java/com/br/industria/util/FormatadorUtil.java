package com.br.industria.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatadorUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat DECIMAL_FORMATTER;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#,##0.00", symbols);
    }

    private FormatadorUtil() {

    }

    public static String formatarData(LocalDate data) {
        return data.format(DATE_FORMATTER);
    }

    public static String formatarValor(BigDecimal valor) {
        return DECIMAL_FORMATTER.format(valor);
    }
}
