package cc.lylllcc.util;

import cc.lylllcc.szsdmodel.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author lixiangpu
 */
public class SzsdUtils {
    private SzsdUtils() {
    }

    public static String getSzsdCookie(String username, String password) throws Exception {
        URL url;
        InputStream inputStream;
        BufferedReader reader;
        DataOutputStream dataOutputStream;

        Scanner scanner;

        String casSslStickyCookie;
        String keyDcpCas;
        String lt;
        String castgc;
        String szsdSSIONID;
        String portalSessionId;
        String keyDcpV6;
        String szsdCookie;
        Document doc;
        Elements elements;
        Element element;

        //第一次连接 获取lt
        url = new URL("https://cas.upc.edu.cn/cas/");
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.connect();
        inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
        reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        scanner = new Scanner(reader);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        reader.close();
        inputStream.close();
        doc = Jsoup.parse(stringBuilder.toString());
        elements = doc.getElementsByClass("login_form");
        element = elements.select("input").get(6);

        lt = element.attr("value");

        List<String> temp = (httpsURLConnection.getHeaderFields().get("Set-Cookie"));
        keyDcpCas = temp.get(0);
        keyDcpCas = keyDcpCas.substring(0, keyDcpCas.indexOf(";"));
        casSslStickyCookie = temp.get(1);
        casSslStickyCookie = casSslStickyCookie.substring(0, casSslStickyCookie.indexOf(";"));
        httpsURLConnection.disconnect();
        //第二次连接
        url = new URL("https://cas.upc.edu.cn/cas/login");
        httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setInstanceFollowRedirects(false);
        String cookietemp = casSslStickyCookie + ";" + keyDcpCas;
        httpsURLConnection.setRequestProperty("Cookie", cookietemp);
        httpsURLConnection.connect();
        dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
        String content = "username=" + URLEncoder.encode(username, "UTF-8")
                + "&password=" + URLEncoder.encode(getMD5(password), "UTF-8")
                + "&service=" + "http://i.upc.edu.cn"
                + "&encodedService=" + "http%3a%2f%2fi.upc.edu.cn"
                + "&serviceName=" + "null"
                + "&loginErrCnt=" + "0"
                + "&lt=" + lt;
        dataOutputStream.writeBytes(content);
        dataOutputStream.flush();
        dataOutputStream.close();
        castgc = httpsURLConnection.getHeaderFields().get("Set-Cookie").get(0);
        castgc = castgc.substring(0, castgc.indexOf(";"));
        inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
        reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        scanner = new Scanner(reader);
        stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        reader.close();
        inputStream.close();
        doc = Jsoup.parse(stringBuilder.toString());
        elements = doc.getAllElements();
        element = elements.select("a").first();
        String ticketURL = element.attr("href");
        ticketURL = ticketURL.substring(4, ticketURL.length());
        ticketURL = "https" + ticketURL;

        httpsURLConnection.disconnect();
        //第一次重定向
        //重定向连接1
        try {
            url = new URL(ticketURL);
        } catch (MalformedURLException e) {
            return null;
        }

        httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setInstanceFollowRedirects(false);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.connect();
        httpsURLConnection.getInputStream();
        List<String> tempList = httpsURLConnection.getHeaderFields().get("Set-Cookie");
        szsdSSIONID = tempList.get(0);
        szsdSSIONID = szsdSSIONID.substring(0, szsdSSIONID.indexOf(";"));
        portalSessionId = tempList.get(1);
        portalSessionId = portalSessionId.substring(0, portalSessionId.indexOf(";"));
        httpsURLConnection.disconnect();
        //重定向连接2
        url = new URL("https://i.upc.edu.cn/dcp/forward.action?path=/portal/portal&p=home");
        httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setInstanceFollowRedirects(false);
        cookietemp = szsdSSIONID + ";" + portalSessionId;
        httpsURLConnection.setRequestProperty("Cookie", cookietemp);
        httpsURLConnection.connect();
        keyDcpV6 = httpsURLConnection.getHeaderFields().get("Set-Cookie").get(0);
        keyDcpV6 = keyDcpV6.substring(0, keyDcpV6.indexOf(";"));
        httpsURLConnection.disconnect();

        //第二次重定向
        //重定向第一次
        url = new URL("https://cas.upc.edu.cn/cas/login?service=http%3A%2F%2Fi.upc.edu.cn%2Fdcp%2Fforward.action%3Fpath%3D%2Fportal%2Fportal%26p%3Dhome");
        httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setInstanceFollowRedirects(false);
        httpsURLConnection.setDoInput(true);
        cookietemp = casSslStickyCookie + ";" + castgc + ";" + keyDcpCas;
        httpsURLConnection.setRequestProperty("Cookie", cookietemp);
        httpsURLConnection.connect();
        inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
        reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        scanner = new Scanner(reader);
        stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        reader.close();
        inputStream.close();
        doc = Jsoup.parse(stringBuilder.toString());
        elements = doc.getAllElements();
        element = elements.select("a").first();
        String lastURL = element.attr("href");
        lastURL = "https" + lastURL.substring(4, lastURL.length());
        httpsURLConnection.disconnect();
        //重定向第二次
        url = new URL(lastURL);
        httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setInstanceFollowRedirects(false);
        cookietemp = szsdSSIONID + ";" + keyDcpV6 + ";" + portalSessionId;
        httpsURLConnection.setRequestProperty("Cookie", cookietemp);
        httpsURLConnection.connect();
        inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
        inputStream.close();
        szsdCookie = cookietemp;
        httpsURLConnection.disconnect();
        return szsdCookie;
    }

    public static String getJwxtCookie(String szsdCookie) throws Exception {
        String jwxtCookie;
        //跳转教务系统
        URL
                url = new URL("https://i.upc.edu.cn/dcp/forward.action?path=dcp/core/appstore/menu/jsp/redirect&appid=1180&ac=3");
        HttpsURLConnection
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestProperty("Cookie", szsdCookie);
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setInstanceFollowRedirects(false);
        httpsURLConnection.connect();
        //地址规范化
        String jwxtURL = httpsURLConnection.getHeaderField("Location");
        String temp1 = jwxtURL.substring(0, jwxtURL.indexOf(" "));
        temp1 += "%20";
        temp1 += jwxtURL.substring(jwxtURL.indexOf(" ") + 1, jwxtURL.length());
        httpsURLConnection.disconnect();

        url = new URL(temp1);
        HttpURLConnection
                httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.connect();
        jwxtCookie = httpURLConnection.getHeaderField("Set-Cookie");
        jwxtCookie = jwxtCookie.substring(0, jwxtCookie.indexOf(";"));
        InputStream
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        inputStream.close();
        httpURLConnection.disconnect();
        URL loginUrl2 = new URL("http://jwxt.upc.edu.cn/jwxt/Logon.do?method=logonBySSO");
        httpURLConnection = (HttpURLConnection) loginUrl2.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestProperty("Cookie", jwxtCookie);
        httpURLConnection.connect();
        httpURLConnection.disconnect();
        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        inputStream.close();
        httpsURLConnection.disconnect();

        return jwxtCookie;
    }



    public static List<JwxtScore> getScore(String kksj, String jwxtCookie) {
        try {
            URL url = new URL("http://jwxt.upc.edu.cn/jwxt/xszqcjglAction.do?method=queryxscj");
            HttpURLConnection
                    httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Cookie", jwxtCookie);
            DataOutputStream outputStream1 = new DataOutputStream(httpURLConnection.getOutputStream());
            String content2 = "kksj=" + URLEncoder.encode(kksj, "UTF-8")
                    + "&xsfs=" + URLEncoder.encode("qbcj", "UTF-8")
                    + "&PageNum=" + URLEncoder.encode("1", "UTF-8");
            outputStream1.writeBytes(content2);
            outputStream1.flush();
            outputStream1.close();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuffer stringBuffer = new StringBuffer();
            Scanner scann = new Scanner(in);
            while (scann.hasNextLine()) {
                stringBuffer.append(scann.nextLine()).append("\n");
            }
            scann.close();
            in.close();
            inputStream.close();
            httpURLConnection.disconnect();
            String result = stringBuffer.toString();
            List<JwxtScore> scores = new ArrayList<>();
            if (result.contains("评教未完成")) {
                JwxtScore score = new JwxtScore();
                score.setCourseName("评教未完成");
            } else {
                Document doc = Jsoup.parse(result);
                /*
                 *添加学分绩信息
                 * */
                Elements mEs = doc.getElementsByAttributeValueContaining("color", "red");
//            Elements mEs2 = mEs.get(0).getElementsByTag("span");
//            mItem.put("zxfq", mEs2.get(0).text().replace("，", ""));
//            mItem.put("bxxfq", mEs2.get(1).text().replace("，", ""));
//            mItem.put("xxxfq", mEs2.get(2).text().replace("，", ""));
//            mItem.put("rxxfq", mEs2.get(3).text().replace("，", ""));
                Elements mEs2 = mEs.get(1).getElementsByTag("span");

//            mItem.put("zxf", mEs2.get(0).text().replace("，", ""));
//            mItem.put("bxxf", mEs2.get(1).text().replace("，", ""));
//            mItem.put("xxxf", mEs2.get(2).text().replace("，", ""));
//            mItem.put("rxxf", mEs2.get(3).text().replace("，", ""));
                JwxtScore jwxtScore = new JwxtScore();
                jwxtScore.setCourseName("学分绩");
                jwxtScore.setScore(mEs2.get(4).text().replace("，", ""));
                scores.add(jwxtScore);
                Element e = doc.getElementById("PageNavigation");
                e = e.select("font").first();

                Integer itemNum = new Integer(e.text());

                Integer num;
                Integer pageNum = 2;
                //一页显示超过10个适配
                Element mE = doc.getElementById("mxh");
                if (itemNum < 10 || mE.getElementById(String.valueOf(itemNum)) != null) {
                    num = itemNum;
                    for (Integer i = 1; i <= num; i++) {
                        JwxtScore score = new JwxtScore();
                        Element elem;
                        if ((elem = doc.getElementById(i.toString())) != null) {
                            Element temp;
                            temp = elem.select("td").get(3);
                            score.setCourseTime(temp.text());
                            temp = elem.select("td").get(4);
                            score.setCourseName(temp.text());
                            temp = elem.select("td").get(5);
                            score.setScore(temp.text());
                            //添加成绩详细查询链接
                            String herf = temp.childNode(0).attr("onclick");
                            herf = herf.substring(herf.indexOf("'") + 1, herf.indexOf(",") - 1);
                            //score.setDetailUrl(herf);
                            temp = elem.select("td").get(8);
                            score.setCourseKind(temp.text());
                            temp = elem.select("td").get(10);
                            score.setCredit(temp.text());
                        } else {
                            break;
                        }
                        scores.add(score);
                    }
                } else {
                    do {
                        if (itemNum >= 10) {
                            num = 10;
                        } else {
                            num = itemNum;
                        }
                        for (Integer i = 1; i <= num; i++) {
                            JwxtScore score = new JwxtScore();
                            Element elem;
                            if ((elem = doc.getElementById(i.toString())) != null) {
                                Element temp;
                                temp = elem.select("td").get(3);
                                score.setCourseTime(temp.text());
                                temp = elem.select("td").get(4);
                                score.setCourseName(temp.text());
                                temp = elem.select("td").get(5);
                                score.setScore(temp.text());
                                //添加成绩详细查询链接
                                String herf = temp.childNode(0).attr("onclick");
                                if (herf.length() > 0) {
                                    herf = herf.substring(herf.indexOf("'") + 1, herf.indexOf(",") - 1);
                                    if (!herf.contains("null")) {
                                        //score.setDetailUrl(herf);
                                    }
                                }
                                temp = elem.select("td").get(8);
                                score.setCourseKind(temp.text());
                                temp = elem.select("td").get(10);
                                score.setCredit(temp.text());
                            } else {
                                break;
                            }
                            scores.add(score);
                        }
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Cookie", jwxtCookie);

                        outputStream1 = new DataOutputStream(httpURLConnection.getOutputStream());
                        content2 = "kksj=" + URLEncoder.encode(kksj, "UTF-8")
                                + "&xsfs=" + URLEncoder.encode("qbcj", "UTF-8")
                                + "&PageNum=" + URLEncoder.encode(pageNum.toString(), "UTF-8");
                        outputStream1.writeBytes(content2);
                        outputStream1.flush();
                        outputStream1.close();

                        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                        in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        stringBuffer = new StringBuffer();
                        scann = new Scanner(in);
                        while (scann.hasNextLine()) {
                            stringBuffer.append(scann.nextLine()).append("\n");
                        }
                        result = stringBuffer.toString();
                        scann.close();
                        in.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        doc = Jsoup.parse(result);
                        itemNum -= num;
                        pageNum += 1;
                    } while (itemNum > 0);
                }
            }
            return scores;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    public static List<JwxtCourse> getCourseInfo(String xq, String zc, String jwxtCookie) {
        try {
            List<JwxtCourse> courseList = new ArrayList<>();
            String courseUrl = "http://jwxt.upc.edu.cn/jwxt/tkglAction.do?method=goListKbByXs&istsxx=no"
                    + "&xnxqh=" + xq
                    + "&zc=" + zc
                    + "&xs0101id=";
            URL url1 = new URL(courseUrl);
            HttpURLConnection
                    httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Cookie", jwxtCookie);
            httpURLConnection.connect();
            InputStream
                    instream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
//
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            reader.close();
            instream.close();
            httpURLConnection.disconnect();
            if (stringBuilder.toString().contains("评教未完成")) {
                JwxtCourse course = new JwxtCourse("评教未完成");
                courseList.add(course);
            } else {
                Document doc = Jsoup.parse(stringBuilder.toString());
                Elements elements = doc.getAllElements();
                Element element = elements.select("form").first();

                for (int i = 1; i <= 6; i++) {
                    for (int j = 1; j <= 7; j++) {
                        String id = String.valueOf(i) + "-" + String.valueOf(j) + "-" + "1";
                        String id2 = String.valueOf(i) + "-" + String.valueOf(j) + "-" + "2";
                        Element tempE = element.getElementById(id);
                        if (tempE.text().length() < 3) {

                            continue;
                        } else {
                            tempE = element.getElementById(id2);
                        }
                        String info = tempE.text();
                        String[] strings = info.split("\\s+");
                        if (strings.length > 6 && strings.length % 6 != 0) {
                            String[] strT = new String[6];
                            strT[0] = "";
                            for (int t = 0; t <= strings.length - strT.length; t++) {
                                strT[0] += strings[t];
                                if (t < strings.length - strT.length) {
                                    strT[0] += " ";
                                }
                            }
                            System.arraycopy(strings, 1 + strings.length - strT.length, strT, 1, strT.length - 1);
                            strings = strT;
                        }


                        for (int k = 0; k < strings.length / 6; k++) {
                            JwxtCourse item = new JwxtCourse();
                            if (strings[k * 6].contains("英语")) {
                                item.setName(strings[k * 6] + " " + strings[k * 6 + 1]);
                            } else {
                                item.setName(strings[k * 6]);
                            }
                            item.setTeacher(strings[k * 6 + 2]);
                            item.setClassroom(strings[k * 6 + 4]);
                            item.setTimeStart(i * 2 - 1);
                            item.setTimeEnd(i * 2);
                            if (j == 7) {
                                item.setDayOfWeek(0);
                            } else {
                                item.setDayOfWeek(j);
                            }

                            String lessonInfo = strings[k * 6 + 3];
                            String week = lessonInfo.substring(0, lessonInfo.indexOf("周"));
                            if (week.contains(",") && week.contains("-")) {
                                String[] weeks1 = week.split("[,]");
                                for (String str : weeks1) {
                                    if (str.contains("-")) {
                                        String[] weeks = str.split("[-]");
                                        item.setWeekStart(Integer.parseInt(weeks[0]));
                                        item.setWeekEnd(Integer.parseInt(weeks[1]));
                                        courseList.add(item);

                                    } else {
                                        item.setWeekStart(Integer.parseInt(str));
                                        item.setWeekEnd(Integer.parseInt(str));
                                        courseList.add(item);

                                    }

                                }
                            } else if (week.contains(",")) {
                                String[] weeks = week.split("[,]");
                                for (String str : weeks) {
                                    item.setWeekStart(Integer.parseInt(str));
                                    item.setWeekEnd(Integer.parseInt(str));
                                    courseList.add(item);
                                }

                            } else if (week.contains("-")) {
                                if (week.contains("单")) {
                                    week = week.substring(0, week.indexOf("单"));
                                    String[] weeks = week.split("[-]");
                                    for (int x = Integer.parseInt(weeks[0]); x <= Integer.parseInt(weeks[1]); x++) {
                                        if (x % 2 == 1) {
                                            item.setWeekStart(x);
                                            item.setWeekEnd(x);
                                            courseList.add(item);
                                        }
                                    }

                                } else if (week.contains("双")) {
                                    week = week.substring(0, week.indexOf("双"));
                                    String[] weeks = week.split("[-]");
                                    for (int x = Integer.parseInt(weeks[0]); x <= Integer.parseInt(weeks[1]); x++) {
                                        if (x % 2 == 0) {
                                            item.setWeekStart(x);
                                            item.setWeekEnd(x);
                                            courseList.add(item);
                                        }
                                    }
                                } else {
                                    String[] weeks = week.split("[-]");
                                    item.setWeekStart( Integer.parseInt(weeks[0]));
                                    item.setWeekEnd(Integer.parseInt(weeks[1]));
                                    courseList.add(item);
                                }

                            } else if (week.contains("双")) {
                                item.setWeekStart(Integer.parseInt(week.substring(0, 1)));
                                item.setWeekEnd(Integer.parseInt(week.substring(0, 1)));
                                courseList.add(item);
                            } else {
                                item.setWeekStart(Integer.parseInt(week));
                                item.setWeekEnd(Integer.parseInt(week));
                                courseList.add(item);
                            }
                        }
                    }
                }
            }
            return courseList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getMD5(String target) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(target.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
