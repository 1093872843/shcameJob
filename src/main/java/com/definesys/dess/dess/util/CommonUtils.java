package com.definesys.dess.dess.util;




import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {
    public static final String XMLORJSON_XML = "XML";
    public static final String XMLORJSON_JSON = "JSON";


    public static final String CONTENT_TYPE_XML = "application/xml;charset=UTF-8";

    public static final Map<String,String> serverMap = new HashMap<String,String>();



    public static String getSpecifiedServerName(String key) {
        return serverMap.get(key);
    }

    public static String splitSpecifyLengthStr(String sour,int length) {

        if (sour != null && sour.length() > 0 && length > 0) {
            try {
                byte[] b = sour.getBytes("UTF-8");
                if (b.length > length) {
                    byte[] subArray = new byte[length];
                    for (int i = 0; i < length; i++) {
                        subArray[i] = b[i];
                    }
                    return new String(subArray,"UTF-8");
                } else {
                    return sour;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }

    /**
     * 从xml中或者json中获取字段值
     *
     * @param sour
     * @param xmlOrJson
     * @param nodeName
     * @return
     */
    public static String getNodeValueBySubString(String sour,String xmlOrJson,String nodeName) {
        if (sour == null) {
            return null;
        }
        String res = null;
        try {
            String startStr = "";
            String endStr = "";
            if (XMLORJSON_XML.equals(xmlOrJson)) {

                String locateStr = "<" + nodeName + ">";
                int locateIdx = sour.indexOf(locateStr,0);
                if (locateIdx != -1) {
                    //优先查找带命名空间的节点干净节点<s></s>
                    int endIdx = sour.indexOf("</" + nodeName + ">",locateIdx);
                    if (locateIdx + locateStr.length() <= endIdx) {
                        res = sour.substring(locateIdx + locateStr.length(),endIdx);
                    }
                } else {
                    locateStr = nodeName; //不太确定节点是否带命名空间，以及节点有没有属性
                    locateIdx = sour.indexOf(locateStr,0);
                    boolean isLocate = false;
                    String nsPreSuffix = "";
                    int sum = 0;
                    while (!isLocate && locateIdx != -1) {
                        sum++;
                        if (sum > 10000 && sum < 10100) {
                            System.out.println("=======>getNodeValueBySubString死循环了:" + sour + "====>" + nodeName);
                        }
                        int sidx = sour.lastIndexOf("<",locateIdx);
                        int eidx = sour.lastIndexOf(">",locateIdx);
                        boolean hafIsLocate = false;
                        if (sidx != -1 && sidx > eidx) {
                            if (sidx + 1 == locateIdx) {
                                hafIsLocate = true;
                            } else {
                                int didx = sour.indexOf(":",sidx);
                                if (didx + 1 == locateIdx) {
                                    hafIsLocate = true;
                                    nsPreSuffix = sour.substring(sidx + 1,didx);
                                }
                            }
                            if (hafIsLocate) {
                                //初步认定谁定位到了
                                int eAfterIdx = sour.indexOf(">",locateIdx);
                                int sAfterIdx = sour.indexOf("<",locateIdx);
                                if (eAfterIdx != -1 && eAfterIdx < sAfterIdx) {
                                    if (locateIdx + locateStr.length() == eAfterIdx) {
                                        isLocate = true;
                                    } else {
                                        int blankIdx = sour.indexOf(" ",locateIdx);
                                        if (locateIdx + locateStr.length() == blankIdx) {
                                            isLocate = true;
                                        }

                                    }

                                }
                            }
                        }
                        if (!isLocate) {
                            locateIdx = sour.indexOf(locateStr,locateIdx + locateStr.length());
                        }
                    }
                    if (isLocate) {
                        int subStartIdx = sour.indexOf(">",locateIdx);
                        int subEndIdx = -1;
                        if (nsPreSuffix != null && nsPreSuffix.length() > 0) {
                            subEndIdx = sour.indexOf("</" + nsPreSuffix + ":" + locateStr + ">",locateIdx);
                        } else {
                            subEndIdx = sour.indexOf("</" + locateStr + ">",locateIdx);
                        }
                        if (subStartIdx != -1 && subStartIdx < subEndIdx) {
                            res = sour.substring(subStartIdx + 1,subEndIdx);
                        }
                    }

                }
            } else if (XMLORJSON_JSON.equals(xmlOrJson)) {
//                startStr = "\"" + nodeName + "\"";
//                endStr = "\"";
//                int startIdx = sour.indexOf(startStr,0);
//                if (startIdx != -1) {
//                    int endIdxTmp = sour.indexOf(endStr,startIdx + nodeName.length() + 2);
//                    if (endIdxTmp != -1) {
//                        int endIdx = sour.indexOf(endStr,endIdxTmp + 1);
//                        res = sour.substring(endIdxTmp + 1,endIdx);
//                    }
//                }
                //最大概率优先尝试，命中率高 根据比较规范的json报文查找 "字段名":"值"
                int startIdx = sour.indexOf("\"" + nodeName + "\":\"",0);
                if (startIdx != -1) {
                    startIdx = startIdx + nodeName.length() + 4;
                    int endIdxTmp = sour.indexOf("\"",startIdx);
                    if (endIdxTmp != -1) {
                        res = sour.substring(startIdx,endIdxTmp);
                    }
                } else {
                    //第二优先级尝试，命中率一般,根据 "字段":  来查找
                    startIdx = sour.indexOf("\"" + nodeName + "\":");
                    if (startIdx != -1) {
                        startIdx = startIdx + nodeName.length() + 3;
                        int endTmp1 = sour.indexOf(",",startIdx);
                        int endTmp2 = sour.indexOf("}",startIdx);
                        String tmpRes = null;
                        if (endTmp1 != -1) {
                            if (endTmp1 < endTmp2) {
                                //按照endTmp1截取
                                tmpRes = sour.substring(startIdx,endTmp1);
                            } else {
                                if (endTmp2 != -1) {
                                    //按照endTmp2截取
                                    tmpRes = sour.substring(startIdx,endTmp2);
                                }
                            }
                        } else {
                            if (endTmp2 != -1) {
                                //按照endTmp2截取
                                tmpRes = sour.substring(startIdx,endTmp2);
                            }
                        }
                        if (tmpRes != null) {
                            res = tmpRes.replaceAll("\n","").replaceAll("\r","").trim().replaceAll("\"","");
                            if ("null".equals(res)) {
                                res = null;
                            }
                        }
                    } else {
                        //第三种尝试，命中率较低 根据 "字段" 来查找
                        startIdx = sour.indexOf("\"" + nodeName + "\"");
                        if (startIdx != -1) {
                            //可能存在
                            startIdx = startIdx + nodeName.length() + 2;
                            int endTmp1 = sour.indexOf(",",startIdx);
                            int endTmp2 = sour.indexOf("}",startIdx);
                            String tmpRes = null;
                            if (endTmp1 != -1) {
                                if (endTmp1 < endTmp2) {
                                    //按照endTmp1截取
                                    tmpRes = sour.substring(startIdx,endTmp1);
                                } else {
                                    if (endTmp2 != -1) {
                                        //按照endTmp2截取
                                        tmpRes = sour.substring(startIdx,endTmp2);
                                    }
                                }
                            } else {
                                if (endTmp2 != -1) {
                                    //按照endTmp2截取
                                    tmpRes = sour.substring(startIdx,endTmp2);
                                }
                            }
                            if (tmpRes != null) {
                                tmpRes = tmpRes.replaceAll("\n","").replaceAll("\r","").trim();
                                if (tmpRes.startsWith(":")) {
                                    tmpRes = tmpRes.substring(1);
                                    if (tmpRes != null) {
                                        res = tmpRes.trim().replaceAll("\"","");
                                        ;
                                    }
                                    if ("null".equals(res)) {
                                        res = null;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("========>sour:" + sour);
            System.out.println("========>nodeName:" + nodeName + ",XMLORJSON:" + xmlOrJson);
            e.printStackTrace();
            res = null;
        }
        return res;
    }

//    public static String getNodeValueBySubString(String sour,String xmlOrJson,String nodeName) {
//        String res = null;
//        try {
//            String startStr = "";
//            String endStr = "";
//            if (XMLORJSON_XML.equals(xmlOrJson)) {
//                startStr = ":" + nodeName + ">";
//                endStr = "</";
//                int startIdx = sour.indexOf(startStr,0);
//                if (startIdx == -1) {
//                    startStr = "<" + nodeName + ">";//没办法要重新找下，这种情况下应该是小概率事件，毕竟大部分xml节点是有命名空间的
//                    startIdx = sour.indexOf(startStr,0);
//                }
//                if (startIdx != -1) {
//                    int endIdx = sour.indexOf(endStr,startIdx + nodeName.length() + 2);
//                    if (endIdx != -1) {
//                        res = sour.substring(startIdx + nodeName.length() + 2,endIdx);
//                    }
//                }
//            } else if (XMLORJSON_JSON.equals(xmlOrJson)) {
//                startStr = "\"" + nodeName + "\"";
//                endStr = "\"";
//                int startIdx = sour.indexOf(startStr,0);
//                if (startIdx != -1) {
//                    int endIdxTmp = sour.indexOf(endStr,startIdx + nodeName.length() + 2);
//                    if (endIdxTmp != -1) {
//                        int endIdx = sour.indexOf(endStr,endIdxTmp + 1);
//                        res = sour.substring(endIdxTmp + 1,endIdx);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("========>sour:"+sour);
//            System.out.println("========>nodeName:"+nodeName+",XMLORJSON:"+xmlOrJson);
//            e.printStackTrace();
//            res = null;
//        }
//        return res;
//    }

    /**
     * 写死的，获取json的数组
     *
     * @param sour
     * @param nodeName
     * @return
     */
    public static List<String> getJsonNodeStringArrayValue(String sour,String nodeName) {
        List<String> res = new ArrayList<String>();
        String strArray = "";
        String startStr = "\"" + nodeName + "\"";
        String endStr1 = "[";
        String endStr2 = "]";
        int startIdx = sour.indexOf(startStr,0);
        if (startIdx >= 0) {
            int firstdhIdx = sour.indexOf(",",startIdx + nodeName.length() + 2);
            int endStr1Idx = sour.indexOf(endStr1,startIdx + nodeName.length() + 2);
            if (firstdhIdx == -1 || endStr1Idx < firstdhIdx) {
                int ednStr2Idx = sour.indexOf(endStr2,endStr1Idx);
                if (endStr1Idx >= 0 && ednStr2Idx >= 0) {
                    strArray = sour.substring(endStr1Idx + 1,ednStr2Idx);
                }
            }
        }
        String[] spltArray = strArray.split(",");
        for (int i = 0; i < spltArray.length; i++) {
            if (spltArray[i] != null && spltArray[i].length() > 0) {
                int firtIdx = spltArray[i].indexOf("\"");
                int lastIdx = spltArray[i].lastIndexOf("\"");
                if (firtIdx >= 0 && lastIdx >= 0) {
                    spltArray[i] = spltArray[i].substring(firtIdx + 1,lastIdx);
                    if (spltArray[i] != null && spltArray[i].length() > 0) {
                        res.add(spltArray[i]);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 判断xml的根节点下的内容是否为xml格式，CDATA除外，目的是为了区分
     * <xml><node1></node1></></xml>和<xml>xxxx</xml>的区别
     *
     * @param sour
     * @return
     */
    public static boolean getRootNodeValueIsXml(String sour) {
        boolean res = false;
        if (sour == null) {
            return res;
        } else {
            //使用这个方法默认可以肯定的是sour是一个xml字符串，否则不能使用改方法，因为在这里去判断是否一个XML，要执行xml格式的验证，耗费的性能很多

            int idx1 = sour.indexOf(">",0);
            if (idx1 != -1 && idx1 + 1 < sour.length()) {
                String nextVar = " ";
                int widx1 = idx1 + 1;
                int sum = 0;
                while (("\n".equals(nextVar) || "\r".equals(nextVar) || " ".equals(nextVar)) && widx1 < sour.length()) {
                    nextVar = sour.substring(widx1,widx1 + 1);
                    widx1++;
                    sum++;
                    if (sum > 10000 && sum < 10100) {
                        System.out.println("================>commonutils rootnodevalueIsxml 183" + sum);
                    }
                }
                if ("<".equals(nextVar)) {
                    String nnextVar = sour.substring(widx1,widx1 + 1);
                    {
                        if (!"/".equals(nnextVar) && !"!".equals(nnextVar)) {
                            res = true;
                        }
                    }

                }
            }
        }
        return res;

    }

    /**
     * 根据contentType的值判断是否为xml的格式
     *
     * @param contentType
     * @return
     */
    public static boolean getIsXMLByContentType(String contentType) {
        if (contentType != null && (contentType.contains("xml") || contentType.contains("XML"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据contentType的值判断是否为json的格式
     *
     * @param contentType
     * @return
     */
    public static boolean getIsJsonByContentType(String contentType) {
        if (contentType != null && (contentType.contains("json") || contentType.contains("JSON"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取xml跟节点下的内容，如<xml><ss></ss></></xml>返回<ss></ss>
     *
     * @param sour
     * @return
     */
    public static String getRootNodeTextValueBySubString(String sour) {
        if (sour == null) {
            return null;
        } else {
            int sd = sour.indexOf(">",0) + 1;
            int ed = sour.lastIndexOf("</",sour.length());
            if (sd <= ed) {
                return sour.substring(sd,ed);
            } else {
                return null;
            }

        }

    }






    public static String addParam1ToUrlParamsStr(String urlParams,String paramName,String paramValue) {
        String res;
        if (urlParams == null || urlParams.trim().length() == 0) {
            return "?" + paramName + "=" + paramValue;
        } else {
            int sd = urlParams.indexOf(paramName + "=");

            if (sd != -1) {
                if (sd == 0) {
                    int spsd = urlParams.indexOf("&",0);
                    if (spsd == -1) {
                        res = paramName + "=" + paramValue;
                    } else {
                        res = paramName + "=" + paramValue + urlParams.substring(spsd);
                    }
                } else {
                    int sd1 = urlParams.indexOf("?" + paramName + "=");
                    if (sd1 != -1) {
                        int spsd = urlParams.indexOf("&",0);
                        if (spsd == -1) {
                            res = "?" + paramName + "=" + paramValue;
                        } else {
                            res = "?" + paramName + "=" + paramValue + urlParams.substring(spsd);
                        }
                    } else {
                        sd1 = urlParams.indexOf("&" + paramName + "=");
                        if (sd1 != -1) {
                            int spsd = urlParams.indexOf("&",sd1 + 1);
                            if (spsd == -1) {
                                res = urlParams.substring(0,sd1) + "&" + paramName + "=" + paramValue;
                            } else {
                                res = urlParams.substring(0,sd1) + "&" + paramName + "=" + paramValue + urlParams.substring(spsd);
                            }
                        } else {
                            if (urlParams.endsWith("?") || urlParams.endsWith("&")) {
                                res = urlParams + paramName + "=" + paramValue;
                            } else {
                                res = urlParams + "&" + paramName + "=" + paramValue;
                            }
                        }
                    }

                }

            } else {
                if (urlParams.endsWith("?") || urlParams.endsWith("&")) {
                    res = urlParams + paramName + "=" + paramValue;
                } else {
                    res = urlParams + "&" + paramName + "=" + paramValue;
                }
            }
        }
        if (res != null && res.length() > 0 && !res.startsWith("?")) {
            res = "?" + res;
        }
        return res;

    }

    /**
     * 打印异常的详细堆栈信息
     *
     * @param e
     * @return
     */
    public static String getExceptionTrackDetailInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            StringBuffer sbf = sw.getBuffer();
            pw.close();
            sw.close();
            return sbf.toString();
        } catch (Exception es) {
            es.printStackTrace();
            return null;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (sw != null) {
                try {
                    sw.close();
                } catch (Exception ess) {
                    ess.printStackTrace();
                }
            }
        }
    }

    /**
     * 将url param格式转化为map，如tt=1&s=3
     * @param urlParams
     * @param target
     * @return
     */
    public static Map<String,String> covertUrlParamsToMap(String urlParams,Map<String,String> target){
        if(target == null){
            target = new HashMap<String,String>();
        }
        if(urlParams != null){
            String[] splitStr = urlParams.split("&");
            if(splitStr != null && splitStr.length>0){
                for(int i = 0;i<splitStr.length;i++){
                    if(splitStr[i] != null){
                        int splIdx = splitStr[i].indexOf("=");
                        if(splIdx != -1){
                            String key = splitStr[i].substring(0,splIdx);
                            if(key != null && key.trim().length()>0){
                                target.put(key,splitStr[i].substring(splIdx+1));
                            }
                        }
                    }
                }
            }

        }
        return target;
    }

    public static String formatDateToStr(Timestamp date,String format){
        if(date !=null) {
            String fm = "yyyy-MM-dd HH:mm:ss";
            if (format != null) {
                fm = format;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(fm);
            return dateFormat.format(new Date(date.getTime()));
        }else{
            return null;
        }
    }




}
