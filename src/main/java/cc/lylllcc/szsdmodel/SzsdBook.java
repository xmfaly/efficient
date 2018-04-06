package cc.lylllcc.szsdmodel;

import lombok.Data;

/**
 * @author lixiangpu
 */
@Data
public class SzsdBook {
    private String bookName;
    private String authorName;
    private String borrowDate;
    private String returnDate;
    private String barCode;
    private String checkNum;
}
