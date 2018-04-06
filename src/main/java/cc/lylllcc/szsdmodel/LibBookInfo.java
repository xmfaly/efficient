package cc.lylllcc.szsdmodel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author xhaiben
 * @date 2017/1/19
 * title 书名
 * ssh   索书号
 * zrz   责任者
 * cbs   出版社
 * gcfb  馆藏副本
 * kjfb  可借副本
 * cbsj  出版时间
 * marcNo 查询馆藏信息的码
 */
@Data
@EqualsAndHashCode
public class LibBookInfo {
    private String title;
    private String ssh;
    private String zrz;
    private String cbs;
    private String gcfb;
    private String kjfb;
    private String cbsj;
    private String marcNo;

}
