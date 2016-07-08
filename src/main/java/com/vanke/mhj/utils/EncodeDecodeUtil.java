package com.vanke.mhj.utils;

public class EncodeDecodeUtil {
    /*
     * 所有文本框，都必须先经过htmlEncode再入库（防sql注入） 所有显示，如果是单行文本框无须经过htmlDecode
     * 定义：单行文本框输入的内容都是没有格式的内容 推定：所有在单行文本框输入html标签的都是不良行为
     * 带编辑器的多行文本框的内容应该经过htmlDecode，保持其固有格式
     * 注意到script已经被强力转义不再decode还原，因此给你有格式又怎么样？
     */
    public static String htmlEncode(String html) {
        if (html != null) {
            return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll(" ", "&nbsp;").replaceAll("[+]", "＋")
                    .replaceAll("-", "－").replaceAll("\\\\", "＼").replaceAll("/", "／").replaceAll("[*]", "＊").replaceAll(":", "：").replaceAll("[?]", "？").replaceAll("[|]", "｜")
                    .replaceAll("￠", "&cent;").replaceAll("£", "&pound;").replaceAll("¥", "&yen;").replaceAll("§", "&sect;").replaceAll("©", "&copy;").replaceAll("®", "&reg;")
                    .replaceAll("×", "&times;").replaceAll("÷", "&divide;").replaceAll("·", "&middot;");
        } else {
            return " ";
        }
    }

    public static String htmlDecode(String html) {
        if (html != null) {
            return html.replaceAll("&divide;", "÷").replaceAll("&times;", "×").replaceAll("&reg;", "®").replaceAll("&copy;", "©").replaceAll("&sect;", "§").replaceAll("&yen;", "¥")
                    .replaceAll("&pound;", "£").replaceAll("&cent;", "￠").replaceAll("｜", "|").replaceAll("？", "?").replaceAll("：", ":").replaceAll("＊", "*").replaceAll("／", "/")
                    .replaceAll("＼", "\\").replaceAll("－", "-").replaceAll("＋", "+").replaceAll("&nbsp;", " ").replaceAll("&quot;", "\"").replaceAll("&gt;", ">")
                    .replaceAll("&lt;", "<").replaceAll("&amp;", "&").replaceAll("<script", "&lt;script").replaceAll("&middot;", "·");
        } else {
            return null;
        }
    }

}