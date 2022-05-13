package shtanko.util;

public class TransferUtil {

    public static boolean compareBin(Long withdrawNumberCard, Long putNumberCard) {
        String bin1 = getBin(withdrawNumberCard);
        String bin2 = getBin(putNumberCard);
        return bin1.equals(bin2);
    }

    private static String getBin(Long number) {
        return String.valueOf(number).substring(0, 6);
    }
}
